package message.code_service.domain;

import lombok.Data;
import message.domain.Message;

import java.text.MessageFormat;

/**
 * 验证码消息。
 */
@Data
public class Code extends Message {


    private String code;//验证码值
    private String type;//验证码类型：邮箱验证码，手机验证码，二维码
    private String operation;//关联操作
    private String to;//收码人

    public Code() {
    }

    public Code(String code, String type, String operation, String to) {
        this.to = to;
        this.code = code;
        this.type = type;
        this.operation = operation;
    }

    /**
     * 按固定模板编写邮箱验证码消息。
     */
    @Override
    public void writeMessageContentAndTransmissionInfo() {
        messageTransmissionInfo
                .setTo(this.to);
        //TODO 这里写死了，所有邮件都由我发出
        messageTransmissionInfo.setFrom("2897924003@qq.com");
        messageContent
                .setMessage(
                        MessageFormat.format("你好{0}，你正在执行{1}操作，操作许可码为{2}。", messageTransmissionInfo.getTo(), operation, code));
        messageContent
                .setSubject(operation);


    }

    /**
     * 根据某种规则生成key
     */
    @Override
    public void generateKey() {
        String key = MessageFormat.format("{0}:{1}", messageTransmissionInfo.getTo(), operation);
        setKey(key);
    }


}