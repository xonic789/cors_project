import axios, { AxiosResponse } from 'axios';

export function postLoginAsync(user: { email: string, passwd: string }): Promise<AxiosResponse> {
  return axios({
    method: 'post',
    url: '/api/login',
    params: {
      email: user.email,
      passwd: user.passwd,
    },
  }).then((result) => result.data);
}

export function socialLoginAsync(social: string): Promise<AxiosResponse> {
  return axios({
    method: 'post',
    url: `/oauth2/authorization/${social}`,
  }).then((result) => result.data);
}

export function logoutAsync(): Promise<AxiosResponse> {
  return axios({
    method: 'post',
    url: '/api/logout',
  });
}
