package com.pqd.adapters.persistence.product.sonarqube;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "sq_info", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SonarqubeInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_info_seq")
    @SequenceGenerator(name = "sq_info_generator", sequenceName = "sq_info_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "base_url")
    private String baseUrl;

    @Column(name = "component_name")
    private String componentName;

    @Column(name = "token")
    private String token;
}
