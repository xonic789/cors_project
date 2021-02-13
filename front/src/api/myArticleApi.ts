import axios, { AxiosResponse } from 'axios';

export function mySalesArtilceAsync(page: number): Promise<AxiosResponse> {
  console.log(page);
  return axios({
    method: 'get',
    url: '/api/mypage/sales',
    params: {
      page,
    },
  });
}

export function myPurchaseArtilceAsync(): Promise<AxiosResponse> {
  return axios({
    method: 'get',
    url: '/api/mypage/purchase',
  });
}
