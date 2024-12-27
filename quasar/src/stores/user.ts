import { defineStore } from 'pinia';
import { type User } from 'src/domain/User';
import { Cookies } from 'quasar';

export const useAuthStore = defineStore('user', {

  state: () => ({
    isLoggedIn: false,
    user: {
      id: null, // 用户唯一标识
      username: null, // 用户登录账号名
      nickname: null, // 用户名
      avatar: null // 用户头像
    } as User // 强制指定类型为 User
  }),
  getters: {
    getUser: (state): User => state.user
  },
  actions: {
    login(userInfo: User): void {
      this.isLoggedIn = true;
      this.user.id = userInfo.id;
      this.user.username = userInfo.username;
      this.user.nickname = userInfo.nickname;
      this.user.avatar = userInfo.avatar;
      this.user.role = userInfo.role;
    },
    logout(): void {
      this.isLoggedIn = false;
      this.user = {
        id: null,
        username: null,
        nickname: null,
        avatar: null,
        role: null
      };
      for (const key in Cookies.getAll) {
        Cookies.remove(key);
      }
    }
  },
  persist: true //使用localstorage持久化pinia状态
});
