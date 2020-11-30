package com.pqd.application.usecase.dummy;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetDummyResult {

    public Response execute() {
        return Response.of("Application layer responds for dummy request");
    }

    @Value(staticConstructor = "of")
    public static class Response {
        String message;
    }

}
