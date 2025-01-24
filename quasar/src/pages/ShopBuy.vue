<script setup lang="ts">
// 示例商品数据
import { ref } from 'vue';

const categories = [
  { name: 'Java' },
  { name: 'Python' },
  { name: 'Spring' },
  { name: 'C/C++' }
];

const featuredProducts = [
  {
    id: 1,
    name: '捐赠',
    image: 'https://cdn.quasar.dev/img/mountains.jpg',
    price: '$999'
  },
  {
    id: 2,
    name: '资料',
    image: 'https://cdn.quasar.dev/img/mountains.jpg',
    price: '$120'
  },
  {
    id: 3,
    name: 'Luxury Sofa',
    image: 'https://cdn.quasar.dev/img/mountains.jpg',
    price: '$799'
  }
];
const slide = ref<number>(1);
const autoplay = ref<boolean>(true);
</script>

<template>
  <q-page>

    <!-- 轮播图 -->
    <q-carousel
      animated
      v-model="slide"
      navigation
      infinite
      :autoplay="autoplay"
      transition-prev="slide-right"
      transition-next="slide-left"
      @mouseenter="autoplay = false"
      @mouseleave="autoplay = true"
    >
      <q-carousel-slide :name="1" img-src="https://cdn.quasar.dev/img/mountains.jpg" />
      <q-carousel-slide :name="2" img-src="https://cdn.quasar.dev/img/parallax1.jpg" />
      <q-carousel-slide :name="3" img-src="https://cdn.quasar.dev/img/parallax2.jpg" />
      <q-carousel-slide :name="4" img-src="https://cdn.quasar.dev/img/quasar.jpg" />
    </q-carousel>

    <!-- 商品分类 -->
    <q-list>
      <q-item-label class="text-h6 text-center">商品分类</q-item-label>
      <div class="row q-gutter-sm q-mt-md">
        <div v-for="category in categories" :key="category.name" class="col-3 text-center">
          <q-item-label>{{ category.name }}</q-item-label>
        </div>
      </div>
    </q-list>

    <!-- 推荐商品 -->

    <q-item-label class="text-h6 text-center">每日推荐</q-item-label>
    <div class="row">
      <q-card v-for="product in featuredProducts" :key="product.id" class="col-xs-6 col-sm-3 no-padding">
        <q-img :src="product.image" class="cursor-pointer" />
        <q-card-section class="no-padding">
          <q-item>
            <!-- 中间内容 -->
            <q-item-section>
              <q-item-label class="text-weight-medium text-h6">{{ product.name }}</q-item-label>
              <q-item-label class="text-primary text-subtitle2">{{ product.price }}</q-item-label>
            </q-item-section>
            <!-- 右侧按钮 -->
            <q-item-section side>
              <q-btn size="12px" flat dense round icon="more_vert" />
            </q-item-section>
          </q-item>
        </q-card-section>
      </q-card>

    </div>

  </q-page>
</template>

<style scoped>
.q-header {
  position: sticky;
  top: 0;
  z-index: 1000;
}

.q-carousel-slide {
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
