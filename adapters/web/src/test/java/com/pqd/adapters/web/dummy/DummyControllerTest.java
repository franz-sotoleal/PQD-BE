package com.pqd.adapters.web.dummy;

import com.pqd.application.usecase.dummy.GetDummyResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DummyControllerTest {

    private GetDummyResult getDummyResult;
    private DummyController controller;

    @BeforeEach
    void setup() {
        getDummyResult = mock(GetDummyResult.class);
        controller = new DummyController(getDummyResult);
    }

    @Test
    void GIVEN_nothing_WHEN_dummy_request_made_THEN_response_returned_and_mapped() {
        GetDummyResult.Response dummyResponse = GetDummyResult.Response.of("dummy response");
        when(getDummyResult.execute()).thenReturn(dummyResponse);

        ResponseEntity<DummyResponseResultJson> response = controller.getDummyResponse();

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
