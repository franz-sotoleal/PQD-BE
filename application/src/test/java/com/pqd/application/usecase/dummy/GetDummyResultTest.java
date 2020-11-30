package com.pqd.application.usecase.dummy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class GetDummyResultTest {

    private GetDummyResult getDummyResult;

    @BeforeEach
    void setup() {
        getDummyResult = new GetDummyResult();
    }

    @Test
    void GIVEN_nothing_WHEN_dummy_request_made_THEN_dummy_response_returned() {
        GetDummyResult.Response expected = GetDummyResult.Response.of("Application layer responds for dummy request");
        GetDummyResult.Response actual = getDummyResult.execute();
        assertThat(expected).isEqualTo(actual);
    }
}
