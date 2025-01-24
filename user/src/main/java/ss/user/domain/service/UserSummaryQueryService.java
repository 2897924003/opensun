package ss.user.domain.service;

import ss.user.domain.UserSummary;

public interface UserSummaryQueryService {
    UserSummary findUserSummaryById(Long id);
}
