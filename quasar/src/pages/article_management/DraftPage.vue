
<script setup>
import { nextTick, onMounted, ref, watch } from 'vue';
import { useAuthStore } from 'stores/user';
import { gatewayArticleService } from 'boot/axios';
import { useQuasar } from 'quasar';


/*这里是q-markdown的所有插件的导入*/
import abbreviation from 'markdown-it-abbr';
import deflist from 'markdown-it-deflist';
//import emoji from 'markdown-it-emoji'
import footnote from 'markdown-it-footnote';
import insert from 'markdown-it-ins';
import mark from 'markdown-it-mark';
import subscript from 'markdown-it-sub';
import superscript from 'markdown-it-sup';
import taskLists from 'markdown-it-task-lists';
//import mermaid from '@datatraccorporation/markdown-it-mermaid'

// 引用的内容

const noEmoji = ref(false);
const noSubscript = ref(false);
const noSuperscript = ref(false);
const noFootnote = ref(false);
const noDeflist = ref(false);
const noAbbreviation = ref(false);
const noInsert = ref(false);
const noMark = ref(false);
const noTasklist = ref(false);
const noMermaid = ref(false);
const plugins = ref([]);
const count = ref(0);

// 监听多个配置变量变化
watch([
  noAbbreviation,
  noDeflist,
  noEmoji,
  noFootnote,
  noInsert,
  noMark,
  noSubscript,
  noSuperscript,
  noTasklist,
  noMermaid
], rebuildPlugins);

// 根据插件状态重建插件数组
function rebuildPlugins() {
  plugins.value.splice(0, plugins.value.length);

  if (!noAbbreviation.value) plugins.value.push(abbreviation);
  if (!noDeflist.value) plugins.value.push(deflist);
  //if (!noEmoji.value) plugins.value.push(emoji);
  if (!noFootnote.value) plugins.value.push(footnote);
  if (!noInsert.value) plugins.value.push(insert);
  if (!noMark.value) plugins.value.push(mark);
  if (!noSubscript.value) plugins.value.push(subscript);
  if (!noSuperscript.value) plugins.value.push(superscript);
  if (!noTasklist.value) plugins.value.push(taskLists);

  // 强制刷新 QMarkdown
  count.value += 1;
}

// 挂载时初始化插件
onMounted(() => {
  rebuildPlugins();
});


const authStore = useAuthStore();
const $q = useQuasar();


const articleContent = ref({
  content: '',
});

const articleMeta = ref({
  title: '',
  summary: '',
  img: '',
  authorId: '',
  authorName: '',
  authorImg: '',
});
const editorRef = ref(null)
// 插入块引用功能
const mybar = {

  block: {
    tip: '插入块',
    icon: 'save',
    label: '块',
    cmd: 'insertHTML',
    param: '<blockquote class="q-markdown&#45;&#45;note"></blockquote>'
  },

}




const createArticle = async () => {
  try {
    await gatewayArticleService.post(
      '/articles/create',
      {
        articleContent: articleContent.value,
        articleMeta: articleMeta.value,
        actorId: authStore.getUser.id,
      },
      {
        withCredentials: true,
      },
    );
    $q.notify({type: 'positive', message: '文章保存成功'});
  } catch (error) {
    console.error('Error creating article:', error);
    $q.notify({type: 'negative', message: '文章保存失败'});
  }
};
const onRejected = async (rejectedEntries) => {
  $q.notify({
    type: 'negative',
    message: `${rejectedEntries.length} -》图片过大`,
  });
};

const onAdded = async () => {
  //TODO 现在文件系统基础设施还没完成，先全部默认图片
  articleMeta.value.img = 'https://115.120.246.30/icons/china1.png';
};

onMounted(() => {
  articleMeta.value.authorId = authStore.getUser.id;
  articleMeta.value.authorName = authStore.getUser.nickname;
  //TODO 有问题
  articleMeta.value.authorImg = 'https://115.120.246.30/icons/china1.png';
  articleMeta.value.img = 'https://115.120.246.30/icons/china1.png';
});

/*常见问题-确保光标能够跳出当前DOM*/
function addNewLine() {
  nextTick(() => {
    if (!articleContent.value.content.startsWith('<br>')) {
      articleContent.value.content = '<br>' + articleContent.value.content;
    }
    if (!articleContent.value.content.endsWith('<br>')) {
      articleContent.value.content += '<br>';
    }
  });
}
</script>

<template>
  <q-page>
    <q-card class="fit">
      <q-input
        v-model="articleMeta.title"
        label="请输入标题（建议30字以内）"
        counter
        maxlength="30"
        filled
      />

      <q-card-section >
          <q-input
            v-model="articleMeta.summary"
            label="请输入概览"
            counter
            maxlength="32"
            filled
          />
        <q-space/>
          <q-uploader
            url=""
            label="暂不支持上传，文件基础设施搭建中"
            multiple
            accept="image/*"
            max-file-size="2048000"
            max-files="1"
            @rejected="onRejected"
            @added="onAdded"
          />
      </q-card-section>

      <q-card-section>
        <q-editor
          ref="editorRef"
          placeholder="开始创作吧...."
          toolbar-push
          :definitions="mybar"
          :toolbar="[['block']]"
          v-model="articleContent.content"
          @click="addNewLine"
        />
      </q-card-section>


      <q-card-section>
        <q-expansion-item label="更多设置" expand-icon="expand_more">
          <div>分类、封面、标签、文集、定时发布等设置</div>
        </q-expansion-item>
      </q-card-section>

      <q-card-section>
        <q-btn color="primary" label="提交文章"/>
        <q-btn @click="createArticle" color="primary" outline label="存草稿"></q-btn>
        <q-btn color="primary" outline label="手机端预览"/>
        <q-btn color="primary" outline label="网页端预览"/>
      </q-card-section>

    </q-card>
  </q-page>
</template>


<style scoped></style>


