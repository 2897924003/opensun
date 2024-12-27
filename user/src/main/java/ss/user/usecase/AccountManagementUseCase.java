package ss.user.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ss.user.controller.co.ApiCO;
import ss.user.controller.co.account_management.ChangeAccountStatusCO;
import ss.user.controller.ro.AccountRO;
import ss.user.controller.ro.ApiRO;
import ss.user.controller.ro.PageMetaRO;
import ss.user.domain.User;
import ss.user.domain.UserSummary;
import ss.user.infrastructure.persistance.consistence.UserBasedConsistentRepository;
import ss.user.usecase.exception.DataNotConsistentException;

import java.util.Optional;

/**
 * DDD-应用层-用户账号管理
 */
@Service
public class AccountManagementUseCase {

    @Autowired
    private UserBasedConsistentRepository userBasedConsistentRepositories;

    /**
     * 查询用户
     *
     * @return 用户
     */
    public ApiRO<AccountRO> findUserById(ApiCO<Long> co) {
        Optional<User> optUser = userBasedConsistentRepositories.findUserById(co.co);
        Optional<UserSummary> optSummary = userBasedConsistentRepositories.findUserSummaryById(co.co);
        ApiRO<AccountRO> apiRO = ApiRO.unknown();
        optUser.ifPresentOrElse(
                user -> {
                    optSummary
                            .ifPresentOrElse(summary -> apiRO.beSuccess(AccountRO.create(user, summary)), () -> {
                                throw DataNotConsistentException.msg(this.getClass().getName());
                            });
                },
                () -> apiRO.beFail("用户不存在")
        );

        return apiRO;
    }

    /**
     * [启用/禁用，锁定/解锁，过期/续期]
     */
    public ApiRO<Void> changeAccountStatus(ApiCO<ChangeAccountStatusCO> apiCO) {
        ChangeAccountStatusCO co = apiCO.co;
        Optional<User> optUser = userBasedConsistentRepositories.findUserById(co.userId());
        ApiRO<Void> apiRO = ApiRO.unknown();
        optUser.ifPresentOrElse(
                user -> {
                    switch (co.op()) {
                        case "enabled" -> user.setEnabled(!user.isEnabled());
                        case "accountNonLocked" -> user.setAccountNonLocked(!user.isAccountNonLocked());
                        case "accountNonExpired" -> user.setAccountNonExpired(!user.isAccountNonExpired());
                        default -> apiRO.beFail("op无匹配选项");
                    }

                    userBasedConsistentRepositories.updateUserStatus(user);
                    apiRO.beSuccess();
                },
                () -> apiRO.beFail("用户不存在")
        );
        return apiRO;
    }


    /**
     * 删除用户
     *
     * @return Void
     */
    public ApiRO<Void> deleteUserById(ApiCO<Long> apiCO) {
        userBasedConsistentRepositories.deleteUserById(apiCO.co);
        return ApiRO.success();
    }

    public ApiRO<PageMetaRO<AccountRO>> findAllUser(Pageable pageable) {
        Page<User> users = userBasedConsistentRepositories.findAllUser(pageable);
        PageMetaRO<AccountRO> ro = PageMetaRO.getElementsAndConvert(users,
                (user) -> {
                    UserSummary userSummary = userBasedConsistentRepositories
                            .findUserSummaryById(user.getId())
                            .orElseThrow(DataNotConsistentException.msg("数据不一致，发生在AccountManagementUseCase.findAllUser(Pageable pageable)"));
                    return AccountRO.create(user, userSummary);
                });

        return ApiRO.success(ro);
    }


}
