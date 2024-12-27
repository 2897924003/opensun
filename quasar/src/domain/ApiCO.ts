//注意避免大数问题
export class ApiCO<T> {
  // 命令对象：后端所需对象
  co: T;
  // 命令发起者：通常是用户唯一标识
  actor: string;//后续版本将逐渐废除
  token: string | null;
  policy = {};

  constructor(co: T, actor: string | number | null, token: string | null) {
    if (actor === null) {
      throw new Error('actor cannot be null');
    }
    this.co = co;
    this.actor = String(actor);
    this.token = token;
  }

  /**
   * 构建命令对象
   * <p><strong>注意，不要用在请求体用{}包裹，否则会多一层包裹，而后端规范是无包裹</strong></p>
   * @param co
   * @param actor
   */
  public static create<T>(co: T, actor: string | number | null) {
    if (actor === null) {
      actor = -1;
    }
    return new ApiCO<T>(co, actor, null);
  }

  /**
   * 构建命令对象
   * <p><strong>注意，不要用在请求体用{}包裹，否则会多一层包裹，而后端规范是无包裹</strong></p>
   * @param co
   * @param actor
   */
  public static createTEST<T>(co: T, actor: string | number | null, token: string) {
    if (actor === null) {
      actor = -1;
    }
    return new ApiCO<T>(co, actor, token);
  }
}
