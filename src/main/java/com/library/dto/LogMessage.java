package com.library.dto;

import com.library.constant.MessageType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LogMessage {

    private String time;

    private String executor;

    private MessageType type;

    private String body;
}
