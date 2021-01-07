package com.pqd.application.usecase.claim;

import com.pqd.application.domain.claim.UserProductClaim;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetUserProductClaimsTest {

    private GetUserProductClaims getUserProductClaims;
    private UserProductClaimGateway gateway;

    @BeforeEach
    void setup() {
        gateway = mock(UserProductClaimGateway.class);
        getUserProductClaims = new GetUserProductClaims(gateway);
    }

    @Test
    void GIVEN_claims_exists_WHEN_claims_requested_THEN_claims_returned() {
        List<UserProductClaim> claims = TestDataGenerator.generateUserProductClaimList();

        when(gateway.getUserProductClaimsByUsername("user")).thenReturn(claims);

        List<UserProductClaim> actualClaims =
                getUserProductClaims.execute(GetUserProductClaims.Request.of("user")).getUserProductClaims();

        assertThat(actualClaims).isEqualTo(claims);
    }

    @Test
    void GIVEN_claims__dont_exist_WHEN_claims_requested_THEN_empty_list_returned() {
        when(gateway.getUserProductClaimsByUsername("user")).thenReturn(List.of());

        List<UserProductClaim> actualClaims =
                getUserProductClaims.execute(GetUserProductClaims.Request.of("user")).getUserProductClaims();

        assertThat(actualClaims).isEqualTo(List.of());
    }
}
