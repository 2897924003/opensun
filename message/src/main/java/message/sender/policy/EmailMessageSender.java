package message.sender.policy;

import message.domain.Message;
import message.domain.MessageContent;
import message.domain.MessageTransmissionInfo;
import message.sender.MessageSender;
import message.sender.selector.MessageSendMedium;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


/**
 * 消息发送服务-邮箱
 */
@Service
public class EmailMessageSender implements MessageSender {

    private final JavaMailSender mailSender;

    public EmailMessageSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * 发送消息
     *
     * @param message 消息
     */
    @Override
    public void sendMessage(Message message) {

        MessageContent messageContent = message.getMessageContent();
        MessageTransmissionInfo messageTransmissionInfo = message.getMessageTransmissionInfo();
        mailSender.send(mimeMessage ->
                {
                    mimeMessage.setFrom(messageTransmissionInfo.getFrom());
                    mimeMessage.setRecipients(jakarta.mail.Message.RecipientType.TO, messageTransmissionInfo.getTo());
                    mimeMessage.setSubject(messageContent.getSubject());
                    mimeMessage.setText(messageContent.getMessage());
                }
        );
    }

    /**
     * 获取消息发送媒介
     *
     * @return EMAIL
     */
    @Override
    public MessageSendMedium getMedium() {
        return MessageSendMedium.EMAIL;
    }

}
