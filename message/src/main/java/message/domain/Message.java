package message.domain;

import lombok.Data;

/**
 * 消息本质抽象：
 * <p>
 * 消息内容
 * <p>
 * 传输信息
 */
@Data
public abstract class Message {
    protected String key;//存储标识
    protected MessageTransmissionInfo messageTransmissionInfo; // 传输信息
    protected MessageContent messageContent;//消息内容

    public Message() {
        messageContent = new MessageContent();
        messageTransmissionInfo = new MessageTransmissionInfo();
    }

    /**
     * 将消息内容的生成延迟到具体实现类，以便根据具体策略动态生成高度自定义的消息内容。
     */
    public abstract void writeMessageContentAndTransmissionInfo();

    /**
     * 生成消息的存储标识，以便在有需要时（code）从redis（默认）或内存（map）中获取特定消息。
     */
    public abstract void generateKey();

}
