<script setup lang='ts'>
import { useAuthStore } from 'stores/user';
import { nextTick, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { type User } from 'src/domain/User';
import { gatewayPayService } from 'boot/axios';
import { ApiCO } from 'src/domain/ApiCO';
import { Cookies } from 'quasar';

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

const donate = async () => {
  //创建本地订单
  const response = await gatewayPayService.post(
    '/cart/commit',
    ApiCO.createTEST(
      {
        items: {
          '1': 1,
          '0': 6
        }
      },
      useAuthStore().user.id,
      Cookies.get('access_token')
    )
  );
  const orderId = response.data.ro.orderId;
  const amount = response.data.ro.amount;
  //创建支付宝订单，跳转到支付宝完成
  const payResponse = await gatewayPayService.get(
    '/payt',
    {
      params: {
        orderId: orderId,
        amount: amount
      }
    }
  );
  htmlContent.value = payResponse.data;
  await openDialog();
  /*// 获取支付宝支付表单HTML
  const payHtml = payResponse.data;

  // 使用 DOMParser 解析 HTML
  const parser = new DOMParser();
  const doc = parser.parseFromString(payHtml, 'text/html');

  // 获取所有的 input 元素
  const inputs = doc.querySelectorAll('input');

  // 创建一个新的表单
  const form = document.createElement('form');
  form.method = 'POST';
  form.action = 'https://openapi-sandbox.dl.alipaydev.com/gateway.do';  // 支付宝的支付接口URL

  // 将支付宝返回的 input 元素添加到新表单中
  inputs.forEach(input => {
    const clonedInput = document.createElement('input');
    clonedInput.name = input.name;
    clonedInput.value = input.value;
    form.appendChild(clonedInput);
  });

  // 将表单添加到 DOM 中
  document.body.appendChild(form);

  // 提交表单，自动跳转到支付宝支付页面
  form.submit();*/
};
const dialogVisible = ref<boolean>(false);
const htmlContent = ref<string>('');
// 打开 Dialog
const openDialog = async () => {
  // 从后端获取 HTML 内容
  dialogVisible.value = true;

  // 使用 nextTick 确保 HTML 内容渲染后执行脚本
  await nextTick(() => {
    // 手动执行所有 script 标签中的代码
    const scripts = document.querySelectorAll('script');
    scripts.forEach((script) => {
      const newScript = document.createElement('script');
      newScript.textContent = script.textContent;
      document.head.appendChild(newScript);
    });
  });

};
</script>

<template>
  <q-dialog v-model="dialogVisible">
    <q-card>
      <q-card-section>
        <div v-html="htmlContent"></div>
      </q-card-section>
    </q-card>
  </q-dialog>
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
          <p>
            @ 2024 2897924003. All Rights Reserved.
            <q-btn @click="donate" icon="paid" round dense />
          </p>
        </div>
      </q-footer>

    </q-page-container>

  </q-layout>
</template>

<style scoped></style>

