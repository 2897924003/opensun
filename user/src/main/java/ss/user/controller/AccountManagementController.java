package ss.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ss.user.controller.co.ApiCO;
import ss.user.controller.co.account_management.ChangeAccountStatusCO;
import ss.user.controller.ro.AccountRO;
import ss.user.controller.ro.ApiRO;
import ss.user.controller.ro.PageMetaRO;
import ss.user.usecase.AccountManagementUseCase;

/**
 * 账号管理服务接口
 * <p>面向管理员</p>
 */
@RestController
@RequestMapping("/account")
public class AccountManagementController {
    @Autowired
    private AccountManagementUseCase accountManagementUseCase;

    @PostMapping("/user")
    public ApiRO<AccountRO> findUserById(@RequestBody ApiCO<Long> co) {
        return accountManagementUseCase.findUserById(co);
    }

    @PostMapping("/list")
    public ApiRO<PageMetaRO<AccountRO>> findAllUser(Pageable pageable) {
        return accountManagementUseCase.findAllUser(pageable);
    }

    @PostMapping("/changeStatus")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ApiRO<Void> enableUser(@RequestBody ApiCO<ChangeAccountStatusCO> co) {
        return accountManagementUseCase.changeAccountStatus(co);
    }


}
