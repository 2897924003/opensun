package message.sender.registry;


import message.sender.MessageSender;
import message.sender.selector.MessageSenderSelector;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 消息发送者策略注册表
 * <p>注册表ID：消息发送媒介</p>
 */
@Service
public class MessageSenderRegistry implements MessageSenderSelector {

    private final Map<String, MessageSender> senderMap;

    public MessageSenderRegistry(List<MessageSender> senders) {

        this.senderMap = senders.stream().filter(sender -> sender.getMedium() != null)
                .collect(Collectors.toMap(s -> s.getMedium().name(), sender -> sender));
    }

    /**
     * 选择消息发送者
     *
     * @param medium 策略选择因子-发送媒介
     * @return 被选中的消息发送者
     */
    @Override
    public MessageSender selectSender(String medium) {
        MessageSender sender = senderMap.get(medium);
        if (sender == null) {
            throw new UnsupportedOperationException("不支持这种发送媒介：" + medium);
        }
        return sender;
    }
}