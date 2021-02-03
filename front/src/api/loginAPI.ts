import axios from 'axios';

interface tokenInterface {
  accessToken: string;
  refreshToken: string;
}

export function postLoginAsync(user: { email: string, passwd: string }): Promise<tokenInterface> {
  return axios({
    method: 'post',
    url: '/api/login',
    params: {
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
}
