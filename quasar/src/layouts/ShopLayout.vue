<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const search = ref<string>('');
</script>

<template>
  <q-layout view="lHh Lpr lFf">
    <q-page-container>
      <q-header elevated style="height:45px;background: linear-gradient(to bottom right, #3498db, #8e44ad);">
        <q-toolbar>
          <!-- 中央应用标题 -->
          <q-input
            v-model="search"
            filled
            debounce="300"
            placeholder="搜索..."
            class="q-ml-auto q-mr-auto"
            dense
            style="max-width: 300px;"
          />
          <!-- 右上角搜索框 -->
          <q-btn
            flat
            icon="search"
            class="text-white"
          />
        </q-toolbar>
      </q-header>

      <router-view></router-view>
      <q-footer class="text-white" style="height:60px;background: radial-gradient(circle, #3498db, #9b59b6);">
        <q-tabs
          class="text-white justify-around"
          align="center"
          active-color="white"
          indicator-color="white"
        >
          <!-- 首页 -->
          <q-tab
            label="首页"
            icon="home"
            class="q-pa-sm text-white"
            @click='router.push({name:"home"})'
          />

          <!-- 动态 -->
          <q-tab
            label="动态"
            icon="dynamic_feed"
            class="q-pa-sm text-white"
          />
          <!-- AI 辅助助手 -->
          <q-tab
            label="AI助手"
            icon="smart_toy"
            class="q-pa-sm text-white"

          />
          <!-- 商城 -->
          <q-tab
            label="商城"
            icon="store"
            class="q-pa-sm text-white"
          />
          <!-- 我的 -->
          <q-tab
            label="我的"
            icon="person"
            class="q-pa-sm text-white"
            @click='router.push({name:"mine"})'
          />
        </q-tabs>
      </q-footer>

    </q-page-container>
  </q-layout>
</template>

