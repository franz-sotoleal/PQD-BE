package com.pqd.application.usecase.user;

import com.pqd.application.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Component
@Transactional
public class GetUser {

    private final UserGateway userGateway;

    public Response execute(Request request) {
        User user = userGateway.findByUsername(request.getUsername())
                               .orElseThrow(UserNotFoundException::new);

        return Response.of(user);
    }

    @Value(staticConstructor = "of")
    public static class Response {
        User user;
    }

    @Value(staticConstructor = "of")
    public static class Request {
        String username;
    }

    public static class UserNotFoundException extends NoSuchElementException {

    }
}
