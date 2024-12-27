<script setup lang='ts'>
import { useAuthStore } from 'stores/user';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { type User } from 'src/domain/User';

// 定义路由和状态管理
const $router = useRouter();
const authStore = useAuthStore();

// 使用ref并明确指定类型
const user = ref<User | null>(null);
const userImg = ref<string>();

onMounted(() => {
  // 获取用户信息并构造头像URL
  const userData = authStore.getUser;
  if (userData) {
    user.value = userData;
    userImg.value = `http://localhost:9000/icons/${'china' + userData.id + '.png'}`;
  }
});

// 退出登录
const logout = () => {
  // 清除用户信息
  authStore.logout(); // 退出时可以调用store里的logout方法，清理用户数据
  $router.push({ name: 'login' });
};
</script>

<template>
  <q-layout>

    <q-page-container class="fullscreen column">

      <q-header elevated style="background: linear-gradient(to right, #3498db, #9b59b6);">
        <q-toolbar class="q-pa-md">
          <q-btn dense label="首页" to="/discuss" />
          <q-btn dense label="创作" :to="{ name: 'ArticleManagement' }" />
          <q-btn dense label="我的" to="/discuss/user" />
          <q-btn dense label="导航页" to="/home" />
          <q-space />
          <q-avatar>
            <q-img :src="userImg" alt="User Profile" />
          </q-avatar>
          <q-btn flat label="退出登录" color="black" @click="logout" />
        </q-toolbar>
      </q-header>
      <router-view></router-view>
      <q-footer class="text-white" style="background: radial-gradient(circle, #3498db, #9b59b6);">
        <div class="row justify-center items-center">
          <p>@ 2024 2897924003. All Rights Reserved.</p>
        </div>
      </q-footer>

    </q-page-container>

  </q-layout>
</template>

<style scoped></style>

