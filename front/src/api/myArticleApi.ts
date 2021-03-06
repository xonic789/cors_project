import axios, { AxiosResponse } from 'axios';

export function mySalesArtilceAsync(page: number): Promise<AxiosResponse> {
  return axios({
    method: 'get',
    url: '/api/mypage/sales',
    params: {
      page,
    },
  });
}

export function myPurchaseArtilceAsync(page: number): Promise<AxiosResponse> {
  return axios({
    method: 'get',
    url: '/api/mypage/purchase',
    params: {
      page,
    },
  });
}
