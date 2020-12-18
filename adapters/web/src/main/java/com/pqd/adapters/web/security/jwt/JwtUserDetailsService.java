package com.pqd.adapters.web.security.jwt;

import java.util.ArrayList;

import com.pqd.application.usecase.user.GetUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    GetUser getUser;

    // AuthenticationManager calls this method
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.pqd.application.domain.user.User user = getUser.execute(GetUser.Request.of(username))
                                                           .getUser();

        if (user.getUsername().equals(username)) {
            // User for jwt token (not the pqd domain user)
            return new User(user.getUsername(), user.getPassword(),
                            new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

}
