package com.pqd.application.domain.release;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
public class ReleaseInfoJira {

    private final Long id;

    private final String name;

    private final LocalDateTime start;

    private final LocalDateTime end;

    private final Long boardId;

    private final String goal;
}
