export interface Article {
  id: number; // 对应 Long 类型
  authorId: number; // 对应 Long 类型
  authorImg: string;
  authorName: string;
  title: string;
  img: string;
  summary: string;
  link: string;
  score: number; // 对应 Long 类型
  views: number; // 对应 Long 类型
  votes: number; // 对应 Long 类型
  comments: number; // 对应 Long 类型
  shares: number; // 对应 Long 类型
  collects: number; // 对应 Long 类型
  publishDate: string; // 对应 LocalDateTime，使用字符串表示（ISO 8601 格式）
  editDate: string; // 对应 LocalDateTime，使用字符串表示（ISO 8601 格式）
  isPublished: boolean; // 对应 Boolean 类型
}
