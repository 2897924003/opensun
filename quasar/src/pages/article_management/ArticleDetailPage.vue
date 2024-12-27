<template>
  <q-page class='full-height scroll'>
    <q-card class="window-width">
      <q-card-section>
        <div class='text-h4'>{{ article?.article?.title }}</div>
        <div class='text-subtitle2 text-grey-7'>
          {{ article?.article?.publishDate }}
        </div>
      </q-card-section>
      <q-card-section>
        <q-markdown :src='article?.articleContent?.content' />
      </q-card-section>
      <q-separator />
      <!-- 排序规则按钮 -->
      <q-toolbar>
        <q-btn flat label="评论区" />
        <q-space />
        <q-tabs v-model="order">
          <q-tab name="date" label="最近发布" />
          <q-tab name="vote" label="最多点赞" />
          <q-tab name="comment" label="最多评论" />
        </q-tabs>
      </q-toolbar>

      <q-tab-panels v-model="order" class="full-height">
        <q-tab-panel name="date"></q-tab-panel>
        <q-tab-panel name="vote"></q-tab-panel>
        <q-tab-panel name="comment"></q-tab-panel>
      </q-tab-panels>

      <div class="row items-center q-pr-md">
        <q-badge align="top" label="浏览" color="primary">
          <q-icon name="visibility" />
          {{ article?.article.views }}
        </q-badge>

        <q-badge align="top" label="点赞" color="secondary">
          <q-icon name="thumb_up" />
          {{ article?.article.votes }}
        </q-badge>

        <q-badge align="top" label="评论" color="accent">
          <q-icon name="comment" />
          {{ article?.article.comments }}
        </q-badge>

        <q-badge align="top" label="分享" color="warning">
          <q-icon name="share" />
          {{ article?.article.shares }}
        </q-badge>

        <q-badge align="top" label="收藏" color="teal">
          <q-icon name="favorite" />
          {{ article?.article.collects }}
        </q-badge>
      </div>

      <q-card-actions>
        <q-btn @click='vote' icon="favorite_border"></q-btn>
        <q-btn @click='collect' icon="star_rate"></q-btn>
        <q-btn @click='share' icon="share"></q-btn>

        <div class='q-ml-md'>
          作者:
          <q-avatar>
            <q-img :src='article?.article?.img' />
          </q-avatar>
          {{ article?.article?.authorName }}
        </div>
        <q-btn flat label='返回' @click='goBack' />
      </q-card-actions>

      <q-card-section>
        <q-input v-model="commentRootContext" type="text" autogrow>
          <template #append>
            <q-btn @click="makeComment('0',0,commentRootContext)" icon="send"></q-btn>
          </template>
        </q-input>

        <q-infinite-scroll @load="findAllComment" :offset="200">
          <q-card
            v-for="(comment,index) in comments"
            :key="comment.comment.id.commentId"
          >
            <q-card-section>
              <!-- 用户信息 -->
              <div class="q-mb-sm row items-center">
                <img
                  :src="comment?.userSummary.avatar"
                  alt="avatar"
                  class="q-mr-sm"
                  style="width: 40px; height: 40px; border-radius: 50%"
                />
                <span class="text-bold">{{ comment?.userSummary.nickname }}</span>
              </div>
              <!-- 评论内容 -->
              <p>{{ comment?.comment.comment }}</p>
              <!-- 时间和点赞数 -->
              <div class="text-muted q-mb-sm">
                <span>{{ comment?.comment.ct }}</span> |
                <span>{{ comment?.comment.votes }} votes</span> |
                <span>{{ comment?.comment.comments }} comments</span>
              </div>

              <!-- 回复按钮 -->
              <q-input v-model="tempCommentContext[index]" type="text" autogrow>
                <template #append>
                  <q-btn
                    @click="makeComment(comment?.comment.id.commentId,comment?.comment.level+1,tempCommentContext[index])"
                    icon="send"></q-btn>
                </template>
              </q-input>
              <q-btn @click="voteComment(comment?.comment.id.commentId,1)" icon="thumb_up"></q-btn>
              <q-btn @click="voteComment(comment?.comment.id.commentId,-1)" icon="thumb_down"></q-btn>
              <q-btn v-if="comment?.comment.comments!==0" @click="comment!.comment.isFold=!comment!.comment.isFold"
                     icon="expand_more"></q-btn>
            </q-card-section>

            <q-card-section v-if="!comment?.comment.isFold" class="scroll" style="height: 500px">
              <q-infinite-scroll
                @load="(index, done) => findAllSubComment(index, done, comment?.comment.id.commentId, comment?.comment.level + 1,comment)"
                :offset="200"
              >
                <CommentCard
                  v-for="child in comment?.children?.comments"
                  :key="child.comment.id.commentId"
                  :comment="child"
                  :vote-comment="voteComment"
                  :find-all-sub-comment="findAllSubComment"
                />
              </q-infinite-scroll>
            </q-card-section>
          </q-card>


        </q-infinite-scroll>
      </q-card-section>
    </q-card>

  </q-page>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import { gatewayArticleService } from 'boot/axios';
//import {QMarkdown} from '@quasar/quasar-ui-qmarkdown';
import { useAuthStore } from 'stores/user';
import { ApiCO } from 'src/domain/ApiCO';
import { type ApiRO, type PageMetaRO } from 'src/domain/ApiRO';
import { Cookies, useQuasar } from 'quasar';
import type { Article } from 'src/domain/Article';
import CommentCard from 'pages/article_management/CommentCard.vue';

const $q = useQuasar();
const route = useRoute();
const tempCommentContext = ref<string[]>([]);
const commentRootContext = ref<string>('');
const order = ref<string>('');

// 文章数据
const article = ref<{ article: Article; articleContent: { content: string; id: number } }>();

export interface CommentViewAggregationRO {
  comment: {
    id: {
      commentId: string;
      articleId: number;
    };
    parentId: string;
    level: number;
    userId: string;
    comment: string;
    votes: number;
    comments: number;
    ct: Date;
    ut: Date;
    isFold: boolean;
  };
  userSummary: {
    id: string;
    nickname: string;
    avatar: string;
  };
  children: {
    comments: CommentViewAggregationRO[]; // 子评论列表
    pagination: { // 子评论分页信息
      page: number;
      size: number;
    };
  };
}

const comments = ref<CommentViewAggregationRO[]>([]);

const pagination = ref<{ size: number, page: number }>({ page: 1, size: 10 });

//首次查询评论
const findAllComment = async (index: number, done: (stop: boolean) => void) => {
  const articleId = route.params.article_id;

  const response = await gatewayArticleService.get<ApiRO<PageMetaRO<CommentViewAggregationRO>>>(
    `/comment/${articleId}`,
    {
      params: {
        page: pagination.value.page,
        size: pagination.value.size,
        level: 0,
        parentId: 0,
        sort: ['votes', 'ct'].join(',')
      }
    }
  );

  if (response.data.status === 1) {
    if (response.data.ro.elements.length > 0) {
      response.data.ro.elements.forEach(element => {
        element.comment.isFold = true;
        element.children = {
          comments: [], // 子评论列表
          pagination: { // 子评论分页信息
            page: response.data.ro.currentPage,
            size: response.data.ro.elements.length
          }
        };
      });
      done(false);
      comments.value.push(...response.data.ro.elements);
      pagination.value.page++;
    } else {
      done(true);
    }
  } else {
    $q.notify({
      message: response.data.msg,
      position: 'top',
      color: 'negative'
    });
  }
};
//查询子评论
const findAllSubComment = async (index: number, done: (stop: boolean) => void, parentId: string, level: number, parent: CommentViewAggregationRO) => {
  const articleId = route.params.article_id;

  const response = await gatewayArticleService.get<ApiRO<PageMetaRO<CommentViewAggregationRO>>>(`/comment/${articleId}`,
    {
      params: {
        page: parent.children.pagination.page,
        size: pagination.value.size,
        parentId: parentId,
        level: level,
        sort: ['votes', 'ct'].join(',')
      }
    }
  );
  if (response.data.status === 1) {
    if (response.data.ro.elements.length > 0) {
      response.data.ro.elements.forEach(element => {
        element.comment.isFold = true;
        element.children = {
          comments: [], // 子评论列表
          pagination: { // 子评论分页信息
            page: response.data.ro.currentPage,
            size: response.data.ro.elements.length
          }
        };
      });
      done(false);


      parent.children.comments.push(...response.data.ro.elements);
      console.log(parent);
      parent.children.pagination.page++;
    } else {
      done(true);
    }
  } else {
    $q.notify({
      message: response.data.msg,
      position: 'top',
      color: 'negative'
    });
  }
};

const reloadComment = async () => {
  // 重置评论数据和分页
  comments.value = [];
  pagination.value.page = 1;

  // 重新加载评论
  const done = (stop: boolean) => {
    if (stop) {
      $q.notify({
        message: '没有更多评论',
        position: 'top',
        color: 'info'
      });
    }
  };
  await findAllComment(0, done);
};

const makeComment = async (parentId: string, level: number, comment: string | undefined) => {
  await gatewayArticleService.post(
    '/comment/comment',
    ApiCO.createTEST({
        comment: comment,
        articleId: route.params.article_id,
        parentId: parentId,
        level: level
      }, useAuthStore().user.id, Cookies.get('access_token')
    )
  );
  await reloadComment();
};

const voteComment = async (commentId: string, delta: number) => {
  await gatewayArticleService.post(
    '/comment/vote',
    ApiCO.createTEST({
        'id': {
          'articleId': route.params.article_id,
          'commentId': commentId
        },
        'delta': delta
      }, useAuthStore().user.id, Cookies.get('access_token')
    )
  );
  await reloadComment();
};
const vote = async () => {
  await gatewayArticleService.post('/article/vote', {
    articleId: route.params.article_id,
    actorId: useAuthStore().user.id
  });
};

async function collect() {
  await gatewayArticleService.post('/article/collect', {
    articleId: route.params.article_id,
    actorId: useAuthStore().user.id
  });
}

async function share() {
  await gatewayArticleService.post('/article/share', {
    articleId: route.params.article_id,
    actorId: useAuthStore().user.id
  });
}

// 加载文章详情
const loadArticle = async () => {
  try {
    const articleId = route.params.article_id;
    const response = await gatewayArticleService.get(`/article/${articleId}`);
    article.value = response.data;
  } catch (error) {
    console.error('Failed to load article:', error);
  }
};

// 返回上一个页面
const goBack = () => {
  window.location.href = '/discuss/home';
};

onMounted(() => {
  loadArticle();
});
</script>

<style scoped>
.q-avatar img {
  width: 30px;
  height: 30px;
}
</style>
