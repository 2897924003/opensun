package ss.user.usecase;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ss.user.controller.co.ApiCO;
import ss.user.controller.co.CodeContext;
import ss.user.controller.co.account_setting.ChangePasswordCO;
import ss.user.controller.co.account_setting.CreateAccountCO;
import ss.user.controller.ro.ApiRO;
import ss.user.domain.User;
import ss.user.infrastructure.persistance.UserRepository;
import ss.user.infrastructure.persistance.consistence.UserBasedConsistentRepository;

import java.nio.charset.StandardCharsets;

/**
 * 应用层-负责账号设置相关用例流的业务操作控制协调。
 */
@Service
public class AccountSettingUseCase {


    private final UserRepository userRepository;
    private final RabbitTemplate rabbitTemplate;
    private final static PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    @Autowired
    private UserBasedConsistentRepository consistentRepository;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public AccountSettingUseCase(UserRepository userRepository, RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 修改密码
     * 支持：1. 手机验证 2. 邮箱验证
     */
    @Transactional
    public ApiRO<Void> changePassword(ChangePasswordCO co) {
        // 1. 校验验证码
        if (!isCodeValid(co.codeContext())) {
            return ApiRO.fail("验证码错误或过期");
        }
        ApiRO<Void> apiRO = ApiRO.unknown();
        consistentRepository.findUserByUsername(co.codeContext().to()).ifPresentOrElse(
                user -> {
                    // 2. 修改密码
                    user.setPassword(passwordEncoder.encode(co.changePasswordContext().newPassword()));
                    // 3. 持久化更新用户
                    consistentRepository.updateUserPassword(user);
                    apiRO.beSuccess();
                },
                () -> apiRO.beFail("用户不存在")
        );
        return apiRO;
    }

    /**
     * 发送验证码
     *
     * @param co 命令对象
     */
    public ApiRO<Void> sendVerificationCode(ApiCO<CodeContext> co) {
        try {
            rabbitTemplate.convertAndSend("code", "send.code", co.co);
            return ApiRO.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ApiRO.fail("验证码发送失败");
        }
    }

    /**
     * 创建用户
     *
     * @param co
     * @return
     */
    public ApiRO<Void> createUser(CreateAccountCO co) {
        //判断用户名是否已存在,以及验证码是否正确
        if (userRepository.existsByUsername(co.username)) {
            return ApiRO.fail("用户已存在");
        }
        if (!isCodeValid(co.codeContext)) {
            return ApiRO.fail("验证码错误");
        }

        User user = User.create(co.username, passwordEncoder.encode(co.password));
        consistentRepository.saveUser(user);
        return ApiRO.success();
    }


    /**
     * 调用消息中间件远程校验验证码
     */
    private boolean isCodeValid(CodeContext co) {
        byte[] verifiedResult = (byte[]) rabbitTemplate.convertSendAndReceive("code", "verify.code", co);
        return verifiedResult != null && Boolean.parseBoolean(new String(verifiedResult, StandardCharsets.UTF_8));
    }

}
