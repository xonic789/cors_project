import axios, { AxiosResponse } from 'axios';

export function loadMarketAPI(): Promise<AxiosResponse> {
  return axios.get('http://local.corsmarket.ml/api/market');
}
export function loadMarketDetailAPI(marketId: number): Promise<AxiosResponse> {
  return axios.get(`http://local.corsmarket.ml/api/market/${marketId}`);
}
export function loadMarketPostDetailAPI(marketId: number, postId: number): Promise<AxiosResponse> {
  return axios.get(`http://local.corsmarket.ml/api/market/${marketId}/${postId}`);
}
