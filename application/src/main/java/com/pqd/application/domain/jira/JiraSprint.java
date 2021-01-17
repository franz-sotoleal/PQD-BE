package com.pqd.application.domain.jira;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;


@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
public class JiraSprint {

    private final Long id;

    private final Long sprintId;

    private final String name;

    private final LocalDateTime start;

    private final LocalDateTime end;

    private final Long boardId;

    private final String goal;

    private final String browserUrl;

    public static String createBrowserUrl(String baseUrl, Long sprintId) {
        String browserUrl = baseUrl + "/issues/?";
        try {
            String jql = "jql=" + URLEncoder.encode(String.format("Sprint=%s", sprintId), StandardCharsets.UTF_8.toString());
            browserUrl += jql;
        } catch (UnsupportedEncodingException ignore) { }

        return browserUrl;
    }

}
