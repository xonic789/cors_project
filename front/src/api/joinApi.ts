import axios from 'axios';

export function emailDuplicationAsync(email: string): Promise<boolean> {
  return axios({
    method: 'POST',
    url: '/api/check/email',
    params: { email },
  }).then((res) => (res.status !== 200));
}

export function emailCertificationAsync(email: string, code: string): Promise<boolean> {
  return axios({
    method: 'POST',
    url: '/api/check/code',
    params: { email, code },
  }).then((res) => (res.status === 200));
}

export function nicknameDuplicationAsync(nickname: string): Promise<boolean> {
  return axios({
    method: 'POST',
    url: '/api/check/nickname',
    params: { nickname },
  }).then((res) => (res.status !== 200));
}

export function joinRequestAsync(email: string, nickname: string, passwd: string, address: string): Promise<boolean> {
  return axios({
    method: 'POST',
    url: '/api/join',
    params: { email, passwd, nickname, address },
  }).then((res) => (res.status === 200));
}
