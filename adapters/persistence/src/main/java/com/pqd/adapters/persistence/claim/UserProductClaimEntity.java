package com.pqd.adapters.persistence.claim;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_product_claim", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProductClaimEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_product_claim_seq")
    @SequenceGenerator(name = "user_product_claims_generator", sequenceName = "user_product_claim_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "claim_level")
    private String claimLevel;
}
