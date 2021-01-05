package com.pqd.application.usecase.product;

import com.pqd.application.usecase.AbstractResponse;
import com.pqd.application.usecase.UseCase;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
@UseCase
public class GenerateToken {

    public Response execute() {
        int tokenLength = 40;
        String characters = "0123456789abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < tokenLength; i++) {
            int index = (int) (characters.length() * Math.random());
            sb.append(characters.charAt(index));
        }

        return Response.of(sb.toString());
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Response extends AbstractResponse {
        String token;
    }

}
