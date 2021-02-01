<<<<<<< HEAD
import axios, { AxiosResponse } from 'axios';
=======
import axios from 'axios';
>>>>>>> e81864fa6fa2a2b3e0e5463822257c93a9ed12f9

// 로그인 요청
export function loginPostApi(): Promise<AxiosResponse> {
  return axios.post('/api/login');
}

<<<<<<< HEAD
// 소셜 로그인 요청
export function socialLoginPostApi(social: string): Promise<AxiosResponse> {
  return axios.post(`/api/auth2/authorization/${social}`);
=======
export function postLoginAsync(user: { email: string, passwd: string }): Promise<tokenInterface> {
  return axios({
    method: 'post',
    url: '/api/login',
    data: {
      email: user.email,
      passwd: user.passwd,
    },
  }).then((result) => result.data);
}

export function socialLoginAsync(social: string): Promise<tokenInterface> {
  return axios({
    method: 'post',
    url: `/api/oauth2/authorization/${social}`,
  }).then((result) => result.data);
>>>>>>> e81864fa6fa2a2b3e0e5463822257c93a9ed12f9
}
