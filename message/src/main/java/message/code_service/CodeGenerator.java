package message.code_service;

import java.security.SecureRandom;

/**
 * 验证码生成器
 */
public class CodeGenerator {

    /**
     * 生成验证码
     *
     * @param length 验证码长度
     * @return code
     */
    public static String generateVerificationCode(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10)); // 生成 0-9 之间的随机数字
        }
        return sb.toString();
    }
}

