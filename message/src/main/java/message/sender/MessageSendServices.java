package message.sender;

import message.domain.Message;
import message.sender.registry.MessageSenderRegistry;
import org.springframework.stereotype.Service;

/**
 * 消息发送服务接口
 */
@Service
public class MessageSendServices {

    /**
     * 已实现的消息发送接口注册表
     */
    private final MessageSenderRegistry messageSenderRegistry;

    public MessageSendServices(MessageSenderRegistry messageSenderRegistry) {
        this.messageSenderRegistry = messageSenderRegistry;
    }


    /**
     * 从策略注册表中选择并发送消息
     *
     * @param medium  策略选择因子-发送媒介
     * @param message 消息
     */
    public void sendMessage(String medium, Message message) {
        //大写
        String upperCase = medium.toUpperCase();
        messageSenderRegistry.selectSender(upperCase).sendMessage(message);
    }


}
