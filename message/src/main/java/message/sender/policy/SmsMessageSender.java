package message.sender.policy;


import message.domain.Message;
import message.sender.MessageSender;
import message.sender.selector.MessageSendMedium;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

//TODO 待完成
@Service
public class SmsMessageSender implements MessageSender {

    private final RestClient restClient = RestClient.create();

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public MessageSendMedium getMedium() {
        return MessageSendMedium.SMS;
    }
}
