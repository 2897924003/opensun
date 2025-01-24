<script setup lang='ts'>
import { useRouter } from 'vue-router';

const $router = useRouter();
</script>

<template>
  <q-page class='q-pa-md row'>


    <q-btn
      class='col-12 col-sm-4'
      icon='architecture'
      label='建筑图纸设计展示平台'
      to="/examplepage"
      rounded
      dense
      glossy
    />
    <q-btn
      class='col-12 col-sm-4'
      icon="code"
      label='在线代码编译运行平台'
      @click='$router.push("/discuss/code")'
      rounded
      dense
      glossy
    />

    <q-btn
      class='col-12 col-sm-4'
      icon="settings_suggest"
      label='后台系统'
      @click='$router.push("/accountmanagement")'
      rounded
      dense
      glossy
    />

    <q-btn
      class='col-12 col-sm-4'
      icon='live_tv'
      label='内容分发平台'
      @click='$router.push({ name: "content" })'
      rounded
      dense
      glossy
    />

    <q-btn
      class='col-12 col-sm-4'
      icon='shop'
      label='电商'
      @click='$router.push({ name: "home" })'
      rounded
      dense
      glossy
    />


  </q-page>
</template>

<style scoped></style>
