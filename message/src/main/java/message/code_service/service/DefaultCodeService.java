package message.code_service.service;

import message.code_service.CodeGenerator;
import message.code_service.domain.Code;
import message.sender.MessageSendServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class DefaultCodeService implements CodeService {


    @Autowired
    private MessageSendServices messageSendServices;
    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public void sendCode(Code code) {
        //生成验证码
        code.setCode(CodeGenerator.generateVerificationCode(6));
        //TODO 很容易出错，修改code类填充message为自动，或实现类似效果，不要修改message类
        //填充标准message
        code.writeMessageContentAndTransmissionInfo();
        ///根据规则拼接key
        code.generateKey();
        redisTemplate.opsForValue().set(code.getKey(), code.getCode(), 5, TimeUnit.MINUTES);
        messageSendServices.sendMessage(code.getType(), code);
    }

    @Override
    public boolean verifyCode(Code code) {
        //填充标准message
        code.writeMessageContentAndTransmissionInfo();
        //根据规则还原key
        code.generateKey();

        Object o = redisTemplate.opsForValue().get(code.getKey());
        return code.getCode().equals(o);
    }
}
