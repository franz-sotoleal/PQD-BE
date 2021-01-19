package com.pqd.adapters.persistence.product.jira;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "jira_info", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JiraInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jira_info_seq")
    @SequenceGenerator(name = "jira_info_generator", sequenceName = "jira_info_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "base_url")
    private String baseUrl;

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "token")
    private String token;
}
