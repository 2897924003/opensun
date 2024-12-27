<script setup lang="ts">

import { ref } from 'vue';
import type { CommentViewAggregationRO } from 'pages/article_management/ArticleDetailPage.vue';
import { gatewayArticleService } from 'boot/axios';
import { ApiCO } from 'src/domain/ApiCO';
import { useAuthStore } from 'stores/user';
import { Cookies } from 'quasar';
import { useRoute } from 'vue-router';

defineProps({
  comment: Object,
  voteComment: Function,
  findAllSubComment: Function
});

const commentContext = ref<string>('');
const route = useRoute();


const makeComment = async (parentId: string, level: number) => {
  await gatewayArticleService.post(
    '/comment/comment',
    ApiCO.createTEST({
        comment: commentContext.value,
        articleId: route.params.article_id,
        parentId: parentId,
        level: level
      }, useAuthStore().user.id, Cookies.get('access_token')
    )
  );

};

</script>

<template>
  <q-card>

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
      <q-input v-model="commentContext" type="text" autogrow>
        <template #append>
          <q-btn @click="makeComment(comment?.comment.id.commentId,comment?.comment.level+1);" icon="send"></q-btn>
        </template>
      </q-input>
      <q-btn @click="voteComment!(comment?.comment.id.commentId,1)" icon="thumb_up"></q-btn>
      <q-btn @click="voteComment!(comment?.comment.id.commentId,-1)" icon="thumb_down"></q-btn>
      <q-btn v-if="comment?.comment.comments!==0" @click="comment!.comment.isFold=!comment!.comment.isFold"
             icon="expand_more"></q-btn>
    </q-card-section>

    <!-- 子评论（递归渲染） -->
    <q-card-section v-if="!comment?.comment.isFold" class="scroll" style="height: 500px">
      <q-infinite-scroll
        @load="(index, done) => findAllSubComment!(index, done, comment?.comment.id.commentId, comment?.comment.level + 1,<CommentViewAggregationRO>comment)"
        :offset="200"
      >
        <CommentCard
          v-for="child in comment?.children?.comments"
          :key="child.comment.id.commentId"
          :comment="child"
        />
      </q-infinite-scroll>
    </q-card-section>

  </q-card>
</template>


<style scoped>

</style>
