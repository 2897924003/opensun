export class User {
  public id: number | null;
  public username: string | null;
  public nickname: string | null;
  public avatar: string | null;
  public role: string[] | null;


  constructor(id: number, username: string, nickname: string, avatar: string, role: string[]) {
    this.id = id;
    this.username = username;
    this.nickname = nickname;
    this.avatar = avatar;
    this.role = role;
  }
}
