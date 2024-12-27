package ss.user.infrastructure.persistance.consistence;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ss.user.domain.User;
import ss.user.domain.UserInfo;
import ss.user.domain.UserSummary;
import ss.user.infrastructure.persistance.UserInfoRepository;
import ss.user.infrastructure.persistance.UserRepository;
import ss.user.infrastructure.persistance.UserSummaryRepository;

import java.util.Optional;

/**
 * 由用户，与用户相关表组成的确保多表一致性的仓储服务。此服务的提供的所有方法，都不会导致一致性问题
 */
@Service
public class UserBasedConsistentRepository {
    @Autowired
    private final UserSummaryRepository userSummaryRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserInfoRepository userInfoRepository;


    public UserBasedConsistentRepository(UserSummaryRepository userSummaryRepository, UserRepository userRepository, UserInfoRepository userInfoRepository) {
        this.userSummaryRepository = userSummaryRepository;
        this.userRepository = userRepository;
        this.userInfoRepository = userInfoRepository;
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     */
    @Transactional
    public void deleteUserById(long id) {
        userSummaryRepository.deleteById(id);
        userRepository.deleteById(id);
        userInfoRepository.deleteById(id);
    }


    /**
     * 创建用户
     *
     * @param user 用户
     */
    @Transactional
    public void saveUser(User user) {
        Long id = userRepository.save(user).getId();
        UserSummary userSummary = UserSummary.of(id);
        UserInfo userInfo = UserInfo.of(id);
        BeanUtils.copyProperties(userSummary, userInfo);
        userInfoRepository.save(userInfo);
        userSummaryRepository.save(userSummary);
    }

    /*提供由user延伸的查询操作*/
    public Optional<User> findUserById(long id) {
        return userRepository.findById(id);
    }

    public Optional<UserSummary> findUserSummaryById(long id) {
        return userSummaryRepository.findById(id);
    }

    public Page<User> findAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    /*保证一致性的改操作*/
    public void updateUserStatus(User user) {
        userRepository.save(user);
    }

    public void updateUserPassword(User user) {
        userRepository.save(user);
    }
}
