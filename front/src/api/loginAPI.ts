import axios, { AxiosResponse } from 'axios';

// 로그인 요청
export function loginPostApi(): Promise<AxiosResponse> {
  return axios.post('/api/login');
}

// 소셜 로그인 요청
export function socialLoginPostApi(social: string): Promise<AxiosResponse> {
  return axios.post(`/api/auth2/authorization/${social}`);
}
