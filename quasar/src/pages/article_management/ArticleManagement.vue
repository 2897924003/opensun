<template>
  <q-page class="column full-height">

    <q-toolbar class="bg-amber">
      <q-btn flat label="文章管理" />
      <q-space />
      <q-tabs v-model="nav">
        <q-tab name="view" label="展示中心" />
        <q-tab name="create" label="创作中心" />
        <q-tab name="data" label="数据中心" />
      </q-tabs>
    </q-toolbar>

    <q-tab-panels v-model="nav" class="col ">
      <q-tab-panel name="view">
        <ViewCenter></ViewCenter>
      </q-tab-panel>
      <q-tab-panel name="create">
        <draft-page></draft-page>
      </q-tab-panel>
      <q-tab-panel name="data"></q-tab-panel>
    </q-tab-panels>

  </q-page>
</template>
<script setup lang='ts'>
import ViewCenter from 'pages/article_management/ViewCenter.vue';
import DraftPage from 'pages/article_management/DraftPage.vue';
import { ref } from 'vue';

const nav = ref<string>('view');
</script>

