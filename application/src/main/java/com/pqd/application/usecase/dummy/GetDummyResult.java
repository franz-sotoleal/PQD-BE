package com.pqd.application.usecase.dummy;

import com.pqd.application.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
@UseCase
public class GetDummyResult {

    public Response execute() {
        return Response.of("Application layer responds for dummy request");
    }

    @Value(staticConstructor = "of")
    public static class Response {
        String message;
    }

}
