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
      icon="system"
      label='后台系统'
      @click='$router.push("/accountmanagement")'
      rounded
      dense
      glossy
    />

    <q-btn
      class='col-12 col-sm-4'
      icon='love'
      label='内容分发平台'
      @click='$router.push("/discuss")'
      rounded
      dense
      glossy
    />


  </q-page>
</template>

<style scoped></style>
