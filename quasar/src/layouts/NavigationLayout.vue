<template>
  <q-layout view='hHh Lpr lFf'>

    <q-header style="background: linear-gradient(to right, #3498db, #9b59b6);">
      <q-toolbar class='row justify-between'>
        <q-toolbar-title>导航页</q-toolbar-title>
        <q-btn @click='logout' round push icon="logout" />
        <q-btn @click="$q.dark.toggle" push round :icon="$q.dark.isActive?'dark_mode':'light'" />
      </q-toolbar>
    </q-header>

    <q-page-container>
      <router-view></router-view>
    </q-page-container>

  </q-layout>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';
import { useQuasar } from 'quasar';
import { useAuthStore } from 'stores/user';

const $q = useQuasar();
const $router = useRouter();
const authStore = useAuthStore();

//退出登录
const logout = () => {
  authStore.logout();
  $q.notify({
    message: '退出登录成功',
    color: 'positive'
  });
  $router.push({ name: 'login' });
};

</script>

<style>
</style>
