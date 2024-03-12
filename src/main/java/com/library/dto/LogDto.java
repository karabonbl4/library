package com.library.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LogDto {

    private LocalDateTime time;

    private String executor;

    private LogType type;

    private String body;
}
