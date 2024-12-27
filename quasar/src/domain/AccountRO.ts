export type AccountRO = {
  id: string; // 用户ID
  username: string; // 用户名
  nickname: string; // 昵称
  avatar: string; // 头像URL
  authorities: string[]; // 权限列表
  createTime: Date; // 创建时间
  updateTime: Date; // 更新时间
  accountNonExpired: boolean; // 是否未过期
  accountNonLocked: boolean; // 是否未被锁定
  enabled: boolean; // 是否启用
};
