package message.sender;


import message.domain.Message;
import message.sender.selector.MessageSendMedium;

/**
 * 消息发送策略接口
 */
public interface MessageSender {
    /**
     * 发送消息
     *
     * @param message 消息内容
     */
    void sendMessage(Message message);

    /**
     * 获取策略选择因子-发送媒介
     * <p>eg. sms，email</p>
     * <p><strong>默认实现=不以发送媒介作为策略选择因子</strong></p>
     *
     * @return 发送媒介
     */
    default MessageSendMedium getMedium() {
        return null;
    }

}
