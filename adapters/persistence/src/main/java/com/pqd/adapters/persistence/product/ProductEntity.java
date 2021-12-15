package com.pqd.adapters.persistence.product;

import com.pqd.adapters.persistence.product.jenkins.JenkinsInfoEntity;
import com.pqd.adapters.persistence.product.jira.JiraInfoEntity;
import com.pqd.adapters.persistence.product.sonarqube.SonarqubeInfoEntity;
import com.pqd.application.domain.jenkins.JenkinsInfo;
import com.pqd.application.domain.jira.JiraInfo;
import com.pqd.application.domain.product.Product;
import com.pqd.application.domain.sonarqube.SonarqubeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "product", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_generator", sequenceName = "product_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "token")
    private String token;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sq_info_id", referencedColumnName = "id")
    private SonarqubeInfoEntity sonarqubeInfoEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "jira_info_id", referencedColumnName = "id")
    private JiraInfoEntity jiraInfoEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "jenkins_info_id", referencedColumnName = "id")
    private JenkinsInfoEntity jenkinsInfoEntity;

    public static Product buildProduct(ProductEntity entity) {
        Product product = Product.builder()
                                 .name(entity.getName())
                                 .sonarqubeInfo(Optional.empty())
                                 .jiraInfo(Optional.empty())
                                 .jenkinsInfo(Optional.empty())
                                 .token(entity.getToken())
                                 .id(entity.getId())
                                 .build();
        if (entity.getSonarqubeInfoEntity() != null) {
            product.setSonarqubeInfo(Optional.of(
                    SonarqubeInfo.builder()
                                 .baseUrl(entity.getSonarqubeInfoEntity().getBaseUrl())
                                 .componentName(entity.getSonarqubeInfoEntity().getComponentName())
                                 .token(entity.getSonarqubeInfoEntity().getToken())
                                 .id(entity.getSonarqubeInfoEntity().getId())
                                 .build()));
        }
        if (entity.getJiraInfoEntity() != null) {
            product.setJiraInfo(Optional.of(
                    JiraInfo.builder()
                            .userEmail(entity.getJiraInfoEntity().getUserEmail())
                            .boardId(entity.getJiraInfoEntity().getBoardId())
                            .id(entity.getJiraInfoEntity().getId())
                            .token(entity.getJiraInfoEntity().getToken())
                            .baseUrl(entity.getJiraInfoEntity().getBaseUrl())
                            .build()));
        }
        if (entity.getJenkinsInfoEntity() != null) {
            product.setJenkinsInfo(Optional.of(
                    JenkinsInfo.builder()
                            .token(entity.getJenkinsInfoEntity().getToken())
                            .baseUrl(entity.getJenkinsInfoEntity().getBaseUrl())
                            .username(entity.getJenkinsInfoEntity().getUsername())
                            .id(entity.getJenkinsInfoEntity().getId())
                            .lastBuildNumber(entity.getJenkinsInfoEntity().getLastBuildNumber())
                            .build()));
        }

        return product;
    }

    public static ProductEntity buildProductEntity(Product product) {
        ProductEntity entity = builder()
                .name(product.getName())
                .token(product.getToken())
                .build();

        if (product.getSonarqubeInfo().isPresent()) {
            entity.setSonarqubeInfoEntity(
                    SonarqubeInfoEntity.builder()
                                       .token(product.getSonarqubeInfo().get().getToken())
                                       .baseUrl(product.getSonarqubeInfo().get().getBaseUrl())
                                       .componentName(product.getSonarqubeInfo().get().getComponentName())
                                       .build());
        }

        if (product.getJiraInfo().isPresent()) {
            entity.setJiraInfoEntity(JiraInfoEntity.builder()
                                                   .token(product.getJiraInfo().get().getToken())
                                                   .userEmail(product.getJiraInfo().get().getUserEmail())
                                                   .boardId(product.getJiraInfo().get().getBoardId())
                                                   .baseUrl(product.getJiraInfo().get().getBaseUrl())
                                                   .build());
        }
        if (product.getJenkinsInfo().isPresent()) {
            entity.setJenkinsInfoEntity(JenkinsInfoEntity.builder()
                    .token(product.getJenkinsInfo().get().getToken())
                    .baseUrl(product.getJenkinsInfo().get().getBaseUrl())
                    .username(product.getJenkinsInfo().get().getUsername())
                    .lastBuildNumber(product.getJenkinsInfo().get().getLastBuildNumber())
                    .build());
        }
        return entity;
    }
}
