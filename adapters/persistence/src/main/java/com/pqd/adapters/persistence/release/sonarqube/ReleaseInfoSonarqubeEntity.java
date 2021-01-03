package com.pqd.adapters.persistence.release.sonarqube;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "release_info_sq", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseInfoSonarqubeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "release_info_sq_seq")
    @SequenceGenerator(name = "release_info_sq_generator", sequenceName = "release_info_sq_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "sec_rating")
    private Double securityRating;

    @Column(name = "rel_rating")
    private Double reliabilityRating;

    @Column(name = "maint_rating")
    private Double maintainabilityRating;

    @Column(name = "sec_vulns")
    private Double securityVulnerabilities;

    @Column(name = "rel_bugs")
    private Double reliabilityBugs;

    @Column(name = "maint_debt")
    private Double maintainabilityDebt;

    @Column(name = "maint_smells")
    private Double maintainabilitySmells;
}
