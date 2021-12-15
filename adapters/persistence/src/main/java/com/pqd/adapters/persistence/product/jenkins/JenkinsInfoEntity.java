package com.pqd.adapters.persistence.product.jenkins;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

@Entity
@Table(name = "jenkins_info", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class JenkinsInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jenkins_info_seq")
    @SequenceGenerator(name = "jenkins_info_generator", sequenceName = "jenkins_info_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "base_url")
    private String baseUrl;

    @Column(name = "username")
    private String username;

    @Column(name = "token")
    private String token;

    @Min(0)
    @Column(name = "last_build_number")
    private Integer lastBuildNumber;
}
