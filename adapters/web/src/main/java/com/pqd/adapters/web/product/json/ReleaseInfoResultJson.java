package com.pqd.adapters.web.product.json;

import com.pqd.application.domain.release.ReleaseInfo;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ReleaseInfoResultJson {

    Long id;

    Long productId;

    String created;

    Double qualityLevel;

    ReleaseInfoSonarqubeResultJson releaseInfoSonarqube;

    public static ReleaseInfoResultJson buildResultJson(ReleaseInfo releaseInfo) {
        return ReleaseInfoResultJson.builder()
                                    .id(releaseInfo.getId())
                                    .productId(releaseInfo.getProductId())
                                    .created(releaseInfo.getCreated().toString())
                                    .qualityLevel(releaseInfo.getQualityLevel())
                                    .releaseInfoSonarqube(
                                            ReleaseInfoSonarqubeResultJson
                                                    .buildResultJson(releaseInfo.getReleaseInfoSonarqube()))
                                    .build();
    }

}
