<template>

  <div
    class=" bg-blue text-white text-center q-pa-md "
  >
    <div>
      <div style="font-size: 30vh">页面不存在</div>
      <div class="text-h2" style="opacity: 0.4">什么也没有...</div>
      <q-btn
        class="q-mt-xl"
        color="white"
        text-color="blue"
        unelevated
        @click="goBack"
        label="返回上一页"
        no-caps
      />
    </div>
  </div>
</template>

<script setup>
function goBack() {
  window.location.href = '/';
}
</script>

