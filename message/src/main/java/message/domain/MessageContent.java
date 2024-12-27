package message.domain;

import lombok.Data;

@Data
public class MessageContent {
    private String subject;//消息标题
    private String message;//消息主体
}
