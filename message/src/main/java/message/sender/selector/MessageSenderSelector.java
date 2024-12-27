package message.sender.selector;

import message.sender.MessageSender;

public interface MessageSenderSelector {
    MessageSender selectSender(String type);
}
