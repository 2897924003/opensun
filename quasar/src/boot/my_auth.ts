import { defineBoot } from '#q-app/wrappers';
import { useAuthStore } from 'stores/user';
import { Cookies, useQuasar } from 'quasar';

import { gatewayCAS } from 'boot/axios';
import { type UserLoginRO } from 'src/domain/UserLoginRO';

export default defineBoot(async ({ router }) => {
  // 自动检查登录状态
  try {

    if (!useAuthStore().user.id) {
      if (Cookies.get('access_token') === '') {
        Cookies.remove('access_token');
        useAuthStore().user.id = null;
        return;
      }
      const response = await gatewayCAS.post<UserLoginRO>(
        '/maintainLogin',
        {},
        {
          withCredentials: true,
          headers: {},
          timeout: 1500
        }
      );
      if (response.status === 200) {
        useAuthStore().login(response.data);
      }
    }
  } catch (error) {
    console.error('未登录：', error);
  }


  router.beforeEach((to, from, next) => {
    const authStore = useAuthStore();
    const $q = useQuasar();


    // 检查是否需要认证且用户未登录
    if (to.meta.requireAuthenticated && !authStore.isLoggedIn) {
      // 显示未登录通知
      $q.notify({
        color: 'negative',
        position: 'top',
        message: '请先登录以访问该页面',
        timeout: 3000
      });

      // 重定向到登录页
      next({ name: 'login' });
    } else {
      // 否则继续导航
      next();
    }
  });
});
