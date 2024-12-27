<template>
  <q-page padding>
    <q-card class="q-mb-md">
      <q-card-section>
        <div class="text-h6">用户信息</div>
      </q-card-section>
      <q-card-section>
        <div><strong>用户名:</strong> {{ account?.username }}</div>
        <div><strong>权限级别:</strong> {{ account?.authorities }}</div>
        <div>
          <strong>账户未过期:</strong>
          {{ account?.accountNonExpired ? '是' : '否' }}
        </div>
        <div>
          <strong>账户未锁定:</strong> {{ account?.accountNonLocked ? '是' : '否' }}
        </div>
        <div><strong>账户启用:</strong> {{ account?.enabled ? '是' : '否' }}</div>
      </q-card-section>
    </q-card>

    <q-card class="q-mb-md">
      <q-card-section>
        <div class="text-h6">账号安全</div>
      </q-card-section>

      <q-card-section>
        <q-btn
          label="修改密码"
          color="primary"
          @click="showChangePasswordDialog = true"
          class="q-mb-sm"
        />
        <q-btn
          label="更换绑定手机号"
          color="primary"
          @click="showChangePhoneDialog = true"
          class="q-mb-sm"
        />
        <q-btn
          label="更换绑定邮箱"
          color="primary"
          @click="showChangeEmailDialog = true"
          class="q-mb-sm"
        />
        <q-toggle
          v-model="twoFactorEnabled"
          label="启用双因素验证"
          color="primary"
          @change="toggleTwoFactor"
        />
      </q-card-section>
    </q-card>


    <q-dialog v-model="showChangePasswordDialog">
      <q-card style="min-width: 350px">
        <q-card-section>
          <div class="text-h6">修改密码</div>
        </q-card-section>

        <q-card-section>
          <q-form @submit="changePassword" class="q-gutter-md">
            <q-select
              filled
              v-model="verificationMethod"
              label="选择身份验证方式"
              :options="['手机号', '邮箱']"
              required
            />
            <q-input
              filled
              v-model="to"
              :label="verificationMethod === '手机号' ? '手机号' : '邮箱号'"
              required
            />
            <q-input
              filled
              v-model="newPassword"
              label="新密码"
              type="password"
              required
              :rules="[(val) => val.length >= 8 || '新密码至少8个字符']"
            />
            <q-input
              filled
              v-model="code"
              label="验证码"
              required
              :rules="[(val) => val.length === 6 || '验证码必须是6位数']"
            />
            <q-btn
              label="获取验证码"
              color="secondary"
              @click="sendCode"
              :loading="sendingCode"
            />
            <q-btn
              label="修改密码"
              type="submit"
              color="primary"
              :loading="loading"
            />
          </q-form>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn
            flat
            label="关闭"
            color="negative"
            @click="showChangePasswordDialog = false"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>

    <q-ajax-bar ref="ajaxBar" position="top" />
  </q-page>
</template>

<script setup lang='ts'>
import { onMounted, ref } from 'vue';
import { useQuasar } from 'quasar';
import { gatewayCAS } from 'boot/axios';
import { type AccountRO } from 'src/domain/AccountRO';
import { type ApiRO } from 'src/domain/ApiRO';

const $q = useQuasar();
const account = ref<AccountRO | null>(null);
const showChangePasswordDialog = ref(false);
const showChangePhoneDialog = ref(false);
const showChangeEmailDialog = ref(false);
const twoFactorEnabled = ref(false);
const verificationMethod = ref('手机号');
const to = ref('');
const newPassword = ref('');
const code = ref('');
const loading = ref(false);
const sendingCode = ref(false);

onMounted(async () => {
  try {
    const response = await gatewayCAS.get('/users/profile');
    account.value = response.data;
  } catch (error) {
    $q.notify({
      color: 'negative',
      message: '加载用户信息失败: ' + error,
      timeout: 3000
    });
  }
});

const changePassword = async () => {
  loading.value = true;
  try {
    await gatewayCAS.post('/users/change_password', {
      changePasswordContext: {
        username: to.value,
        newPassword: newPassword.value
      },
      codeContext: {
        to: to.value,
        type: verificationMethod.value === '手机号' ? 'Sms' : 'Email',
        code: code.value,
        operation: 'changepassword'
      }
    });

    $q.notify({
      color: 'positive',
      message: '密码修改成功!',
      timeout: 3000
    });
    showChangePasswordDialog.value = false;
  } catch (error) {
    $q.notify({
      color: 'negative',
      message: '修改密码失败: ' + error,
      timeout: 3000
    });
  } finally {
    loading.value = false;
  }
};

const sendCode = async () => {
  sendingCode.value = true;

  const response = await gatewayCAS.post<ApiRO<void>>('/users/code', {
    co: {
      to: to.value,
      type: verificationMethod.value === '手机号' ? 'Sms' : 'Email',
      operation: 'changepassword'
    }
  });

  if (response.data.status === 1) {
    $q.notify({
      color: 'positive',
      message: '验证码已发送!',
      timeout: 3000
    });
  } else {
    $q.notify({
      color: 'negative',
      message: '发送验证码失败: ' + response.data.msg,
      timeout: 3000
    });
  }
  sendingCode.value = false;

};

const toggleTwoFactor = async () => {
  try {
    // 假设API返回双因素验证状态
    const response = await gatewayCAS.post('/user/toggle_2fa', {
      enabled: twoFactorEnabled.value
    });
    twoFactorEnabled.value = response.data.enabled;
    $q.notify({
      color: 'positive',
      message: `双因素验证已${twoFactorEnabled.value ? '启用' : '禁用'}`,
      timeout: 3000
    });
  } catch (error) {
    $q.notify({
      color: 'negative',
      message: '更新双因素验证设置失败: ' + error,
      timeout: 3000
    });
  }
};
</script>

<style scoped></style>

