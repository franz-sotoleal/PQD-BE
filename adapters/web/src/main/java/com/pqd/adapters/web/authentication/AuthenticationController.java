package com.pqd.adapters.web.authentication;

import com.pqd.adapters.web.security.jwt.JwtRequest;
import com.pqd.adapters.web.security.jwt.JwtResponse;
import com.pqd.adapters.web.security.jwt.JwtTokenUtil;
import com.pqd.adapters.web.security.jwt.JwtUserDetailsService;
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

@RestController
@RequestMapping("api/authentication")
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final JwtUserDetailsService jwtInMemoryUserDetailsService;

    private final RegisterUser registerUser;

    private final PasswordEncoder bcryptEncoder;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginAndGenerateAuthenticationToken(
            @RequestBody JwtRequest authenticationRequest) {
        return generateAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterUserInput input) {
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

    private ResponseEntity<JwtResponse> generateAuthenticationToken(String username, String password) {
        authenticate(username, password);

        final UserDetails userDetails = jwtInMemoryUserDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(@NonNull String username, @NonNull String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @ExceptionHandler({RegisterUser.InvalidFieldException.class})
    public ResponseEntity<?> handleInvalidFieldException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<?> handleBadCredentialsException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("INVALID_CREDENTIALS");
    }

}
