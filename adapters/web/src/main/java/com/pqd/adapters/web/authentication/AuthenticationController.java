package com.pqd.adapters.web.authentication;

import com.pqd.adapters.web.security.jwt.JwtRequest;
import com.pqd.adapters.web.security.jwt.JwtResponse;
import com.pqd.adapters.web.security.jwt.JwtTokenUtil;
import com.pqd.adapters.web.security.jwt.JwtUserDetailsService;
import com.pqd.application.usecase.user.RegisterUser;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/authentication")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtInMemoryUserDetailsService;

    @Autowired
    RegisterUser registerUser;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> loginAndGenerateAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
            throws Exception {
        return generateAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterUserInput input) {
        if (input.getPassword().length() < 4) { throw new RegisterUser.InvalidFieldException("Password too short"); }
        RegisterUser.Request encryptedRequest = RegisterUser.Request.builder()
                                                         .firstName(input.getFirstName())
                                                         .lastName(input.getLastName())
                                                         .email(input.getEmail())
                                                         .username(input.getUsername())
                                                         .password(bcryptEncoder
                                                                           .encode(input.getPassword())) // Important password encryption
                                                         .build();
        registerUser.execute(encryptedRequest);

        return ResponseEntity.ok().build();
    }

    private ResponseEntity<?> generateAuthenticationToken(String username, String password) throws Exception {
        authenticate(username, password);

        final UserDetails userDetails = jwtInMemoryUserDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(@NonNull String username, @NonNull String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @ExceptionHandler({RegisterUser.InvalidFieldException.class})
    public ResponseEntity<?> handleInvalidFieldException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
