package com.pqd.adapters.jira.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class JiraActiveSprintResponse {

    @JsonProperty(value = "values")
    JiraSprint[] activeSprints;

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class JiraSprint {

        Long id;

        String name;

        @JsonProperty(value = "startDate")
        LocalDateTime start;

        @JsonProperty(value = "endDate")
        LocalDateTime end;

        @JsonProperty(value = "originBoardId")
        Long boardId;

        String goal;
    }

}
