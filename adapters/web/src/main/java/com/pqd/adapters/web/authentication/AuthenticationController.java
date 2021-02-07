package com.pqd.adapters.web.authentication;

import com.pqd.adapters.web.common.ExceptionResponseJson;
import com.pqd.adapters.web.security.jwt.JwtRequest;
import com.pqd.adapters.web.security.jwt.JwtTokenUtil;
import com.pqd.adapters.web.security.jwt.JwtUserDetailsService;
import com.pqd.application.domain.user.User;
import com.pqd.application.usecase.claim.GetUserProductClaims;
import com.pqd.application.usecase.user.GetUser;
import com.pqd.application.usecase.user.RegisterUser;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/authentication")
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final JwtUserDetailsService jwtInMemoryUserDetailsService;

    private final RegisterUser registerUser;

    private final GetUser getUser;

    private final GetUserProductClaims getUserProductClaims;

    private final PasswordEncoder bcryptEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseJson> loginAndGenerateAuthenticationToken(
            @RequestBody JwtRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();

        authenticate(username, password);
        return ResponseEntity.ok(getLoginResponse(authenticationRequest.getUsername()));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterUserRequestJson input) {
        if (input.getPassword().length() < 4) { throw new RegisterUser.InvalidFieldException("Password too short"); }
        RegisterUser.Request encryptedRequest =
                RegisterUser.Request.builder()
                                    .firstName(input.getFirstName())
                                    .lastName(input.getLastName())
                                    .email(input.getEmail())
                                    .username(input.getUsername())
                                    .password(
                                            bcryptEncoder.encode(input.getPassword())) // Important password encryption
                                    .build();
        registerUser.execute(encryptedRequest);

        return ResponseEntity.ok().build();
    }

    private LoginResponseJson getLoginResponse(String username) {
        List<UserProductClaimResponseJson> claimsResponse =
                getUserProductClaims.execute(GetUserProductClaims.Request.of(username))
                                    .getUserProductClaims()
                                    .stream()
                                    .map(UserProductClaimResponseJson::buildResponseJson)
                                    .collect(Collectors.toList());

        final UserDetails userDetails = jwtInMemoryUserDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails, claimsResponse);
        User user = getUser.execute(GetUser.Request.of(username)).getUser();

        return LoginResponseJson.buildResopnseJson(user, token);
    }

    private void authenticate(@NonNull String username, @NonNull String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @ExceptionHandler({RegisterUser.InvalidFieldException.class})
    public ResponseEntity<ExceptionResponseJson> handleInvalidFieldException(Exception e) {
        return ResponseEntity.badRequest()
                             .body(ExceptionResponseJson.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ExceptionResponseJson> handleBadCredentialsException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(ExceptionResponseJson.builder().message("INVALID_CREDENTIALS").build());
    }

}
