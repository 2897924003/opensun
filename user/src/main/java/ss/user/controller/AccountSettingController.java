package ss.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ss.user.controller.co.ApiCO;
import ss.user.controller.co.CodeContext;
import ss.user.controller.co.account_setting.ChangePasswordCO;
import ss.user.controller.co.account_setting.CreateAccountCO;
import ss.user.controller.ro.ApiRO;
import ss.user.usecase.AccountSettingUseCase;

/**
 * 账号设置相关服务接口
 * <p>面向用户</p>
 */
@RestController
@RequestMapping("/users")
public class AccountSettingController {

    private final AccountSettingUseCase accountSettingUseCase;

    public AccountSettingController(AccountSettingUseCase settingBusinessCase) {
        this.accountSettingUseCase = settingBusinessCase;
    }

    //TODO 这里似乎前端问题，username，password为null
    /**
     * 创建账号
     */
    @PostMapping("/register")
    public ApiRO<Void> createAccount(@RequestBody CreateAccountCO co) {
        return accountSettingUseCase.createUser(co);
    }

    //TODO

    /**
     * 删除账号
     *
     * @param co
     * @return
     */
    @PostMapping("/delete")
    public ApiRO<Void> deleteAccount(@RequestBody ApiCO co) {
        return ApiRO.success();
    }


    //TODO

    /**
     * 修改账号名(用于密码登录的账号名-手机号）
     *
     * @param co
     * @return
     */
    @PostMapping("/change_username")
    public ApiRO<Void> changeUsername(@RequestBody ApiCO co) {
        return ApiRO.success();
    }


    //TODO

    /**
     * 修改账号绑定的邮箱
     *
     * @param co
     * @return
     */
    @PostMapping("/change_email")
    public ApiRO<Void> changeEmail(@RequestBody ApiCO co) {
        return ApiRO.success();
    }

    //TODO

    /**
     * 绑定邮箱
     *
     * @param co
     * @return
     */
    @PostMapping("/bind_email")
    public ApiRO<Void> bindEmail(@RequestBody ApiCO co) {
        return ApiRO.success();
    }


    /**
     * 修改账号密码
     */
    @PostMapping("/change_password")
    public ApiRO<Void> changePassword(@RequestBody ChangePasswordCO co) {
        return accountSettingUseCase.changePassword(co);
    }


    //TODO 找个好去处

    /**
     * 发送验证码
     */
    @PostMapping("/code")
    public ApiRO<Void> sendChangePasswordCode(@RequestBody ApiCO<CodeContext> co) {
        return accountSettingUseCase.sendVerificationCode(co);
    }


}
