package com.pqd.application.usecase.user;

import com.pqd.application.domain.user.User;
import com.pqd.application.usecase.AbstractResponse;
import com.pqd.application.usecase.UseCase;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@UseCase
@Transactional
public class GetUser {

    private final UserGateway userGateway;

    public Response execute(Request request) {
        User user = userGateway.findByUsername(request.getUsername())
                               .orElseThrow(UserNotFoundException::new);

        return Response.of(user);
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Response extends AbstractResponse {
        User user;
    }

    @Value(staticConstructor = "of")
    public static class Request {
        String username;
    }

    public static class UserNotFoundException extends NoSuchElementException {

    }
}
