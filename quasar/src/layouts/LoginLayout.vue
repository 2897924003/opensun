<script setup lang="ts">
import { ref } from 'vue';
import { useQuasar } from 'quasar';
import { useRouter } from 'vue-router';
import { useAuthStore } from 'stores/user';
import { gatewayCAS } from 'boot/axios';
import { LoginForm } from 'src/domain/LoginForm';
import { RegisterFrom } from 'src/domain/RegisterFrom';
import { ApiCO } from 'src/domain/ApiCO';

const $q = useQuasar();
const loginFrom = ref(new LoginForm());
const registerFrom = ref(new RegisterFrom());
const LoginOrRegisterCard = ref(true);
const verificationMethod = ref('邮箱');
const $router = useRouter();
const authStore = useAuthStore();

/*匿名登陆,访问受限*/
const anonymous = async () => {
  await $router.push('mainlayout');
};


//发送验证码
const sendCode = async () => {


  try {
    await gatewayCAS.post('/users/code',
      ApiCO.create({
        code: '',
        type: verificationMethod.value === '手机号' ? 'Sms' : 'Email',
        to: registerFrom.value.username,
        operation: 'create_account'
      }, null)
      , {});


    $q.notify({
      color: 'positive',
      message: '验证码已发送!',
      timeout: 3000
    });
  } catch (error) {
    $q.notify({
      color: 'negative',
      message: '发送验证码失败: ' + error,
      timeout: 3000
    });
  }
};

//表单登录
const formLogin = async () => {

  const response = await loginFrom.value.login();

  if (response.data.status === 1) {
    $q.cookies.set('access_token', response.headers.access_token);

    //持久化用户到pinia存储中
    authStore.login(response.data.ro);
    await $router.push('/mainlayout');
  } else {
    $q.notify({
      message: response.data.msg,
      position: 'top',
      color: 'red'
    });
  }
};


//注册账号
const formRegister = async () => {
  const response = await registerFrom.value.register();

  if (response.data.status === 1) {
    $q.notify({
      message: '注册成功',
      position: 'top',
      color: 'green'
    });
    authStore.login(response.data.ro);
    await $router.push('/mainlayout');
  } else {
    $q.notify({
      message: response.data.msg,
      position: 'top',
      color: 'red'
    });
  }
};


</script>

<template>
  <q-layout>
    <q-page-container>

      <q-page class="bg-green">;

        <!--     <q-page class="background-container">
              <video  autoplay loop muted playsinline class="background-video">
                    <source src='/bg-video-day-01.mp4' type="video/mp4" />
              </video>
              -->
        <div class="q-gutter-md row justify-center items-center" style="min-height: 100vh">
          <div class="col-12 col-md-6 col-lg-3">
            <!-- 注册卡片 -->
            <q-card
              v-show="!LoginOrRegisterCard"
              class="bg-transparent"
              flat>
              <q-card-section>
                <q-form class="q-gutter-md" @submit="formRegister">

                  <q-select
                    v-model="verificationMethod"
                    :options="['手机号', '邮箱']"
                    label="选择注册方式"
                    outlined
                    dense
                    clearable
                    color="black"
                  />
                  <q-input
                    v-model="registerFrom.username"
                    :label="verificationMethod === '邮箱' ? '邮箱' : '手机号'"
                    clearable
                    outlined
                    dense
                    lazy-rules
                    :rules="[
                (val) => (val && val.length > 0) || '不能为空',
                (val) => /.+@.+\..+/.test(val) || '邮箱格式不正确'
              ]"
                  />
                  <q-input
                    clearable
                    rounded
                    outlined
                    v-model="registerFrom.password"
                    label="密码"
                    type="password"
                    lazy-rules
                    :rules="[
                (val) => (val && val.length >= 6) || '密码不能少于6位',
                (val) => (val.length <= 50) || '密码过长'
              ]"
                  />
                  <q-input
                    clearable
                    rounded
                    outlined
                    v-model="registerFrom.confirmPassword"
                    label="确认密码"
                    type="password"
                    lazy-rules
                    :rules="[(val) => val === registerFrom.password || '密码不一致']"
                  />

                  <q-input
                    clearable
                    outlined
                    dense
                    v-model="registerFrom.code"
                    label="验证码"
                    :rules="[(val) => val.length === 6 || '验证码必须是6位数']"
                  >
                    <template #append>
                      <q-btn label="获取验证码" @click="sendCode" dense />
                    </template>
                  </q-input>


                  <q-btn-group spread glossy push flat outline>
                    <q-btn label="注册" type="submit" />
                    <q-btn label="返回登录" @click="LoginOrRegisterCard = !LoginOrRegisterCard" />
                  </q-btn-group>

                </q-form>
              </q-card-section>
            </q-card>

            <!-- 登录卡片 -->
            <q-card
              v-show="LoginOrRegisterCard"
              class="bg-transparent"
              flat>
              <q-card-section>
                <q-form class="q-gutter-md" @submit="formLogin">
                  <q-input
                    clearable
                    rounded
                    outlined
                    v-model="loginFrom.username"
                    label="账号"
                    lazy-rules
                    :rules="[(val) => (val && val.length > 0) || '不能为空']"
                  />
                  <q-input
                    clearable
                    rounded
                    outlined
                    v-model="loginFrom.password"
                    label="密码"
                    type="password"
                    lazy-rules
                    :rules="[(val) => (val !== null && val !== '') || '不能为空',(val) => (val.length > 0 && val.length < 50) || '密码过长']"
                  />
                  <q-btn-group spread glossy push flat outline>
                    <q-btn label="登录" type="submit" class="q-mr-sm" />
                    <q-btn label="匿名访问" @click="anonymous" class="q-mr-sm" />
                    <q-btn label="注册" @click="LoginOrRegisterCard = !LoginOrRegisterCard" class="q-mr-sm" />
                    <q-btn label="GitHub" href="https://115.120.246.30/cas/oauth2/authorization/github" />
                  </q-btn-group>

                </q-form>
              </q-card-section>
            </q-card>
          </div>
        </div>
      </q-page>
    </q-page-container>
  </q-layout>
</template>

<style scoped>

.background-container {
  position: relative;
  width: 100%;
  height: 100vh;
  overflow: hidden;
}

.background-video {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover; /* 确保视频覆盖整个背景区域 */
  z-index: -1; /* 将视频放在背景层 */
}

</style>
