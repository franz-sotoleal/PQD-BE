package com.pqd.adapters.persistence.claim;

import com.pqd.application.domain.claim.UserProductClaim;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserProductClaimAdapterTest {

    private UserProductClaimRepository repository;
    private UserProductClaimAdapter adapter;

    @Captor
    private ArgumentCaptor<UserProductClaimEntity> captor;

    @BeforeEach
    void setup() {
        repository = mock(UserProductClaimRepository.class);
        adapter = new UserProductClaimAdapter(repository);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void GIVEN_user_product_claim_WHEN_saving_entity_THEN_entity_passed_and_saved() {
        UserProductClaim userProductClaim = TestDataGenerator.generateUserProductClaim();
        UserProductClaimEntity userProductClaimEntity = TestDataGenerator.generateUserProductClaimEntity();
        when(repository.save(any())).thenReturn(userProductClaimEntity);

        UserProductClaim actual = adapter.save(userProductClaim);

        verify(repository).save(captor.capture());
        assertThat(captor.getValue()).hasSameClassAs(userProductClaimEntity);
        assertThat(actual).isEqualTo(userProductClaim);
    }

    @Test
    void GIVEN_claims_exist_WHEN_searching_claims_by_username_THEN_claims_returned() {
        UserProductClaim userProductClaim = TestDataGenerator.generateUserProductClaim();
        UserProductClaimEntity userProductClaimEntity = TestDataGenerator.generateUserProductClaimEntity();
        when(repository.getUserProductClaimsByUsername(any())).thenReturn(List.of(userProductClaimEntity));

        List<UserProductClaim> actual = adapter.getUserProductClaimsByUsername("username");

        assertThat(actual).hasSameClassAs(new ArrayList<UserProductClaim>());
        assertThat(actual.get(0)).isEqualTo(userProductClaim);
    }

    @Test
    void GIVEN_claims_dont_exist_WHEN_searching_claims_by_username_THEN_nothing_returned() {
        when(repository.getUserProductClaimsByUsername(any())).thenReturn(List.of());

        List<UserProductClaim> actual = adapter.getUserProductClaimsByUsername("username");

        assertThat(actual).hasSameClassAs(new ArrayList<UserProductClaim>());
        assertThat(actual.size()).isEqualTo(0);
    }
}
