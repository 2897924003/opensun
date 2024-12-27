import { type AxiosResponse } from 'axios';
import { gatewayCAS } from 'boot/axios';
import { type UserLoginRO } from 'src/domain/UserLoginRO';
import { type ApiRO } from 'src/domain/ApiRO';

/**
 * 登录表单
 */
export class LoginForm {
  public username: string;
  public password: string;
  public remember: boolean;

  constructor(
    username: string = '',
    password: string = '',
    remember: boolean = false
  ) {
    this.username = username;
    this.password = password;
    this.remember = remember;
  }

  /**
   * 表单登录
   */
  public async login(): Promise<AxiosResponse<ApiRO<UserLoginRO>>> {
    try {
      return await gatewayCAS.post(
        '/login',
        new URLSearchParams({
          username: this.username,
          password: this.password,
          remember: String(this.remember)
        }),
        {
          withCredentials: true,
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          }
        }
      );
    } catch (error) {
      //通常是网络问题引起的失败
      return Promise.reject({
        data: {
          ro: null,
          msg: error,
          status: 0
        } as ApiRO<null>
      });
    }

  }
}
