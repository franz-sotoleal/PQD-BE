package com.pqd.application.usecase.claim;

import com.pqd.application.domain.claim.UserProductClaim;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SaveClaimTest {

    private SaveClaim saveClaim;
    private UserProductClaimGateway gateway;

    @Captor
    private ArgumentCaptor<UserProductClaim> captor;

    @BeforeEach
    void setup() {
        gateway = mock(UserProductClaimGateway.class);
        saveClaim = new SaveClaim(gateway);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void GIVEN_correct_request_WHEN_saving_claim_THEN_claim_saved() {
        UserProductClaim userProductClaim = TestDataGenerator.generateUserProductClaim();
        when(gateway.save(any())).thenReturn(userProductClaim);


        saveClaim.execute(SaveClaim.Request.of(userProductClaim.getUserId(),
                                               userProductClaim.getProductId(),
                                               userProductClaim.getClaimLevel()));

        verify(gateway).save(captor.capture());
        assertThat(userProductClaim).isEqualTo(captor.getValue());
    }
}
