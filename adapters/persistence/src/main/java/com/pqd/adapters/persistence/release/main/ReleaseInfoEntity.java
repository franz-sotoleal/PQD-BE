package com.pqd.adapters.persistence.release.main;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "release_info", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "release_info_seq")
    @SequenceGenerator(name = "release_info_generator", sequenceName = "release_info_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "quality_level")
    private Double qualityLevel;

    @Column(name = "release_info_sq_id")
    private Long releaseInfoSonarqubeId;
}
