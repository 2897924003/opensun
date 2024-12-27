package message.inbound.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import message.code_service.domain.Code;
import message.code_service.service.CodeService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * 采用消息中间件RabbitMQ的实现
 *
 * @author 2897924003@qq.com
 */
@Service
public class CodeMessageListener {

    private final CodeService codeService;

    public CodeMessageListener(CodeService codeService) {
        this.codeService = codeService;
    }

    /**
     * 声明路由键为send.codeTopic交换机code，并绑定send_code_queue队列，监听send_code_queue队列的发送验证码任务
     *
     * @param jsonCode json格式的待发送验证码
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "send_code_queue", durable = "true"),
            exchange = @Exchange(value = "code", type = ExchangeTypes.TOPIC),
            key = "send.code"
    ))
    public void receiveSendCodeCO(@Payload String jsonCode) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Code code = objectMapper.readValue(jsonCode, Code.class);

            codeService.sendCode(code);
        } catch (Exception e) {
            System.out.println("=======================================解析出错" + jsonCode);
        }

    }

    /**
     * 声明路由键为verify.code的Topic交换机code，并绑定verify_code_queue队列，监听verify_code_queue队列的发送验证码任务
     *
     * @param jsonCode json格式的待校验验证码
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "verify_code_queue", durable = "true"),
            exchange = @Exchange(value = "code", type = ExchangeTypes.TOPIC),
            key = "verify.code"
    ))
    public String receiveVerifyCodeCO(@Payload String jsonCode) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Code code = objectMapper.readValue(jsonCode, Code.class);
            return codeService.verifyCode(code) ? "true" : "false";
        } catch (Exception e) {
            System.out.println("=======================================解析出错" + jsonCode);
        }
        return "false";
    }
}
