<script setup lang='ts'>
import { ref } from 'vue';
import { gatewayArticleService } from 'boot/axios';
import { type Article } from 'src/domain/Article';
import { type ApiRO } from 'src/domain/ApiRO';

const page = ref<number>(1); //mybatisplus：1；springdatajpa：0
const size = ref<number>(10);
const articles = ref<Article[]>([]);
const search = ref<string>('');

//TODO 新建页面，分页查找，多种模式查找
//es搜索-spring-data-jpa从零开始
const es = async () => {
  articles.value = [];
  try {
    const response = await gatewayArticleService.get<ApiRO<Article[]>>(
      `/es/${search.value}?page=0&size=100`
    );
    search.value = '';
    articles.value.push(...response.data.ro);

  } catch (error) {
    console.error('失败:', error);
  }
};


// 获取文章数据
const loadArticles = async (index: number, done: (stop: boolean) => void) => {
  try {
    const response = await gatewayArticleService.get(
      `/articles?page=${page.value}&size=${size.value}`
    );
    const newArticles = response.data;

    if (newArticles.length > 0) {
      articles.value.push(...newArticles);
      done(false);
      page.value++;
    } else {
      done(true);
    }
  } catch (error) {
    console.error('Error loading articles:', error);
  }
};

const goToArticle = (articleId: number) => {
  window.open(`/discuss/article/${articleId}`);

};
</script>

<template>


  <q-page class="full-height" style="background: linear-gradient(135deg,slateblue,beige,cadetblue);">

    <div class="full-height row q-col-gutter-md ">

      <!-- 左侧栏 -->
      <div class="col-xs-0 col-sm-2 "></div>

      <!-- 主内容区 -->
      <div class="full-height col-xs-12 col-sm-7">

        <!-- 固定顶部搜索栏 -->
        <div style="position: sticky;background: transparent;">
          <q-toolbar>
            <q-input v-model="search" placeholder="搜索">
              <template #after>
                <q-btn @click="es" icon="search"></q-btn>
              </template>
            </q-input>
          </q-toolbar>
        </div>

        <!--内容区-->
        <div class="full-height scroll">

          <q-infinite-scroll @load="loadArticles" :offset="200">
            <!-- 卡片列表 -->
            <q-card
              v-for="article in articles"
              :key="article.id"
              class="q-mb-md rounded-borders"
              @click="goToArticle(article.id)"
              style="cursor: pointer"
            >
              <q-card-section>
                <div class="text-h6">{{ article.title }}</div>
                <q-img :src="article.img" fit="contain" :ratio="16/9"></q-img>
                <div class="text-subtitle2 text-grey-7">
                  {{ article.publishDate }}
                </div>
              </q-card-section>
              <q-card-section>
                <div class="text-body2">
                  {{ article.summary }}
                </div>
              </q-card-section>
              <q-separator />
              <q-card-actions class="row items-center justify-between">
                <div class="row items-center q-pr-md">
                  <q-badge align="top" label="浏览" color="primary">
                    <q-icon name="visibility" />
                    {{ article?.views }}
                  </q-badge>

                  <q-badge align="top" label="点赞" color="secondary">
                    <q-icon name="thumb_up" />
                    {{ article?.votes }}
                  </q-badge>

                  <q-badge align="top" label="评论" color="accent">
                    <q-icon name="comment" />
                    {{ article?.comments }}
                  </q-badge>

                  <q-badge align="top" label="分享" color="warning">
                    <q-icon name="share" />
                    {{ article?.shares }}
                  </q-badge>

                  <q-badge align="top" label="收藏" color="teal">
                    <q-icon name="favorite" />
                    {{ article?.collects }}
                  </q-badge>
                </div>
                <q-separator vertical class="q-mx-md" />
                <div class="row items-center">
                  作者：
                  <q-avatar>
                    <q-img src="/icons/china1.png" />
                  </q-avatar>
                  <div class="q-ml-sm">{{ article.authorName }}</div>
                  <q-btn flat label="收藏" class="q-ml-md" />
                </div>
              </q-card-actions>
            </q-card>
          </q-infinite-scroll>
        </div>
      </div>
      <!-- 右侧栏 -->
      <div class="col-xs-0 col-sm-3 "></div>

    </div>

  </q-page>

</template>

<style scoped>
/* 添加卡片的美化样式 */
</style>

