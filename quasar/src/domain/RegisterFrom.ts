import { gatewayCAS } from 'boot/axios';
import { type ApiRO } from 'src/domain/ApiRO';
import { type UserLoginRO } from 'src/domain/UserLoginRO';
import { type AxiosResponse } from 'axios';
import { type Code } from 'src/domain/Code';

/**
 * 表单注册
 */
export class RegisterFrom {

  public username: string;
  public password: string;
  public confirmPassword: string;
  public email: string;
  public phone: string;
  public code: string;

  constructor(username: string = '', password: string = '', confirmPassword: string = '', email: string = '', code: string = '', phone: string = '') {
    this.username = username;
    this.password = password;
    this.confirmPassword = confirmPassword;
    this.email = email;
    this.code = code;
    this.phone = phone;
  }

  //注册
  public async register(): Promise<AxiosResponse<ApiRO<UserLoginRO>>> {
    try {
      return await gatewayCAS.post(
        '/users/register',
        {
          username: this.username,
          password: this.password,
          codeContext: {
            code: this.code
          } as Code

        },
        {
          headers: {
            'Content-Type': 'application/json'
          }
        }
      );
    } catch (error) {
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
