package com.pqd.adapters.persistence.product;

import com.pqd.adapters.persistence.product.jira.JiraInfoEntity;
import com.pqd.adapters.persistence.product.sonarqube.SonarqubeInfoEntity;
import com.pqd.application.domain.jira.JiraInfo;
import com.pqd.application.domain.product.Product;
import com.pqd.application.domain.sonarqube.SonarqubeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public static Product buildProduct(ProductEntity entity) {
        return Product.builder()
                      .sonarqubeInfo(
                                       SonarqubeInfo.builder()
                                                    .baseUrl(entity.getSonarqubeInfoEntity().getBaseUrl())
                                                    .componentName(entity.getSonarqubeInfoEntity().getComponentName())
                                                    .token(entity.getSonarqubeInfoEntity().getToken())
                                                    .id(entity.getSonarqubeInfoEntity().getId())
                                                    .build())
                      .jiraInfo(JiraInfo.builder()
                                                 .userEmail(entity.getJiraInfoEntity().getUserEmail())
                                                 .boardId(entity.getJiraInfoEntity().getBoardId())
                                                 .id(entity.getJiraInfoEntity().getId())
                                                 .token(entity.getJiraInfoEntity().getToken())
                                                 .baseUrl(entity.getJiraInfoEntity().getBaseUrl())
                                                 .build())
                      .name(entity.getName())
                      .token(entity.getToken())
                      .id(entity.getId())
                      .build();
    }

    public static ProductEntity buildProductEntity(Product product) {
        return builder()
                            .name(product.getName())
                            .token(product.getToken())
                            .sonarqubeInfoEntity(SonarqubeInfoEntity.builder()
                                                                    .token(product.getSonarqubeInfo().getToken())
                                                                    .baseUrl(product.getSonarqubeInfo().getBaseUrl())
                                                                    .componentName(product.getSonarqubeInfo()
                                                                                          .getComponentName())
                                                                    .build())

                            .build();
    }
}
