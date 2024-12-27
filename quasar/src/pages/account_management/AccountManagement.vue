<template>
  <q-page>
    <q-table
      :grid="$q.platform.is.mobile"
      title="用户账号信息概览"
      ref="tableRef"
      :rows="rows"
      row-key="id"
      :columns="columns"
      v-model:pagination="pagination"
      @request="onRequest"
      no-data-label="没有指定数据"
      no-results-label="没有匹配数据"
      class="full-height scroll"
    >
      <template #body-cell-avatar="props">
        <q-td :props="props">
          <q-img :src="props.row.avatar" style="width: 50px; height: auto;" />
        </q-td>
      </template>
      <template #body-cell-authorities="props">
        <q-td :props="props">
          <textarea disabled v-model="props.row.authorities" style="width: 50px; height: auto;" />
        </q-td>
      </template>
      <template #body-cell-enabled="props">
        <q-td :props="props">
          <q-toggle
            v-model="props.row.enabled"
            @click="changeAccountStatus(props.row,props.col.name)"
            checked-icon="check"
            unchecked-icon="clear"
            color="green"
            :label="props.row.enabled ? '启用' : '禁用'"
            :disable="String(props.row.id) === String(useAuthStore().user.id)"
          />
        </q-td>
      </template>
      <template #body-cell-accountNonExpired="props">
        <q-td :props="props">
          <q-toggle
            v-model="props.row.accountNonExpired"
            @click="changeAccountStatus(props.row,props.col.name)"
            checked-icon="check"
            unchecked-icon="clear"
            color="green"
            :label="props.row.accountNonExpired ? '过期' : '正常'"
            :disable="String(props.row.id) === String(useAuthStore().user.id)"
          />
        </q-td>
      </template>
      <template #body-cell-accountNonLocked="props">
        <q-td :props="props">
          <q-toggle
            v-model="props.row.accountNonLocked"
            @click="changeAccountStatus(props.row,props.col.name)"
            checked-icon="check"
            unchecked-icon="clear"
            color="green"
            :label="props.row.accountNonLocked ? '锁定' : '正常'"
            :disable="String(props.row.id) === String(useAuthStore().user.id)"
          />
        </q-td>
      </template>
      <template #top>
        <q-toolbar class="q-pa-md">
          <q-btn label="新增" />
          <q-btn label="删除" />
          <q-btn label="导出" />
          <q-space />
          <q-input v-model="searchParam" clearable dense placeholder="搜索..."
                   :rules="[(val) => (val && val.length > 0) || '不能为空',]">
            <template #append>
              <q-btn @click="search(searchParam)" label="搜索"></q-btn>
            </template>
          </q-input>
        </q-toolbar>
      </template>
      <template #no-data="{ icon, message }">
        <div class="full-width row flex-center text-accent q-gutter-sm">
          <q-icon size="2em" name="sentiment_dissatisfied" />
          <span> Well this is sad... {{ message }} </span>
          <q-icon size="2em" :name="searchParam ? 'filter_b_and_w' : icon" />
        </div>
      </template>
    </q-table>
  </q-page>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { gatewayCAS } from 'boot/axios';
import { type AccountRO } from 'src/domain/AccountRO';
import { ApiCO } from 'src/domain/ApiCO';
import { useAuthStore } from 'stores/user';
import { type ApiRO, type PageMetaRO } from 'src/domain/ApiRO';
import { type QTableProps, useQuasar } from 'quasar';

//quasar引用
const $q = useQuasar();

//table声明
const columns: QTableProps['columns'] = [
  {
    name: 'id',//唯一标识
    label: 'ID',//表头名
    required: true,
    align: 'center',
    field: 'id',//赋值的字段名
    sortable: false,
    classes: 'text-center', // 设置额外样式
    headerStyle: 'text-align: center;'
  },
  {
    name: 'avatar',
    required: true,
    label: '头像',
    align: 'center',
    field: 'avatar',
    sortable: false,
    style: 'max-width: 50px; height: 50px;', // 限制图片大小
    classes: 'text-center', // 设置额外样式
    headerStyle: 'text-align: center;'
  },
  {
    name: 'username',
    required: true,
    label: '用户名',
    align: 'left',
    field: 'username',
    sortable: true
  },
  {
    name: 'nickname',
    label: '昵称',
    align: 'left',
    field: 'nickname',
    sortable: true
  },
  {
    name: 'authorities',
    label: '权限列表',
    align: 'left',
    field: 'authorities',
    sortable: false
  },
  {
    name: 'createTime',
    label: '创建时间',
    align: 'left',
    field: 'createTime',
    sortable: true
    //format: (val: Date) => new Date(val).toLocaleString(),
  },
  {
    name: 'updateTime',
    label: '更新时间',
    align: 'left',
    field: 'updateTime',
    sortable: true
    //format: (val: Date) => new Date(val).toLocaleString(),
  },
  {
    name: 'accountNonExpired',
    label: '未过期',
    align: 'center',
    field: 'accountNonExpired',
    sortable: false
  },
  {
    name: 'accountNonLocked',
    label: '未锁定',
    align: 'center',
    field: 'accountNonLocked',
    sortable: false
  },
  {
    name: 'enabled',
    label: '启用',
    align: 'center',
    field: 'enabled',
    sortable: false
  }
];

//用户列表
const rows = ref<AccountRO[]>([]);

//查询参数
const searchParam = ref<string>('');

//table引用
const tableRef = ref();

//分页对象
const pagination = ref<NonNullable<QTableProps['pagination']>>({
  sortBy: null,
  descending: false,
  page: 1,
  rowsPerPage: 10,
  rowsNumber: 100
});

//分页获取用户
const onRequest: QTableProps['onRequest'] = async (props) => {
  const { page, rowsPerPage } = props.pagination;
  const response = await gatewayCAS.post<ApiRO<PageMetaRO<AccountRO>>>('/account/list', null, {
    params: {
      page: page - 1,
      size: rowsPerPage
    }
  });
  rows.value.splice(0, rows.value.length, ...response.data.ro.elements);
  pagination.value.page = page;
  pagination.value.rowsPerPage = rowsPerPage;
  pagination.value.rowsNumber = response.data.ro.totalElements;
};

//初始化数据
onMounted(() => {
  tableRef.value.requestServerInteraction();
});

//查询特定用户
const search = async <T>(co: T): Promise<void> => {
  const apiCO = ApiCO.create(co, String(useAuthStore().user.id));
  const response = await gatewayCAS.post<ApiRO<AccountRO>>('/account/user', apiCO);
  if (response.data.status === 1) {
    // 清空旧数据
    rows.value = [];
    // 添加新数据
    rows.value.push(response.data.ro);
  } else {
    $q.notify({
      message: response.data.msg,
      position: 'top'
    });
  }
};

//修改用户状态
const changeAccountStatus = async (row: AccountRO, name: keyof AccountRO) => {
  if (!useAuthStore().user.role?.includes('ROLE_ADMIN')) {
    (row[name] as boolean) = !(row[name] as boolean);
    $q.notify({
      message: '权限不足',
      position: 'top',
      color: 'negative'
    });
    return;
  }
  const apiRO = await gatewayCAS.post<ApiRO<void>>(
    'account/changeStatus',
    ApiCO.create({
      userId: row.id,
      op: name
    }, useAuthStore().user.id),
    {
      withCredentials: true
    });

  if (apiRO.data.status === 0) {
    console.log(apiRO.data.msg);
  }
};
</script>

<style scoped>

</style>

