package com.pqd.application.usecase.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class GenerateTokenTest {

    private GenerateToken generateToken;

    @BeforeEach
    void setup() {
        generateToken = new GenerateToken();
    }

    @Test
    void GIVEN_multiple_iterations_WHEN_token_generated_THEN_unique_tokens_generated_with_length_40() {
        int iterations = 100;
        HashSet<String> tokenSet = new HashSet<>();
        for (int i = 0; i < iterations; i++) {
            String token = generateToken.execute().getToken();
            tokenSet.add(token);
            assertThat(token.length()).isEqualTo(40);
        }
        assertThat(tokenSet.size()).isEqualTo(iterations);
    }
}
