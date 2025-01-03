package ss.user.controller.co.account_setting;

import ss.user.controller.co.CodeContext;

/**
 * 修改密码命令
 *
 * @param changePasswordContext
 * @param codeContext
 */
public record ChangePasswordCO(
        ChangePasswordContext changePasswordContext,
        CodeContext codeContext
) {

    /**
     * 自检逻辑
     *
     * @param changePasswordContext 修改密码上下文
     * @param codeContext           验证码上下文
     */
    public ChangePasswordCO {
    }
}

