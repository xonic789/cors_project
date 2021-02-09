import axios, { AxiosResponse } from 'axios';

export function mySalesArtilceAsync(): Promise<AxiosResponse> {
  return axios({
    method: 'get',
    url: '/api/mypage/sales',
  });
}

export function myPurchaseArtilceAsync(): Promise<AxiosResponse> {
  return axios({
    method: 'get',
    url: '/api/mypage/purchase',
  });
}
