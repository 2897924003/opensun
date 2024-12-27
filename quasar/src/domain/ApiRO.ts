/**
 * 与后端ApiRO<T>类保持一致
 */
export interface ApiRO<E> {
  // 响应对象：前端所需对象
  ro: E;
  // 响应描述：用户提示词
  msg: string;
  // 响应状态：0-失败，1-成功，2-其他错误
  status: number;
}

/**
 * 分页响应
 */
export interface PageMetaRO<E> {
  // 总数据条数
  totalElements: number;
  // 总页数
  totalPages: number;
  // 当前页
  currentPage: number;
  // 每页数据条数
  pageSize: number;
  // 当前页的数据
  elements: E[];
}

