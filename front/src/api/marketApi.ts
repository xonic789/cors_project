import axios, { AxiosResponse } from 'axios';

const URL = 'https://corsmarket.ml';

// 마켓리스트 불러오기
export function loadMarketAPI(): Promise<AxiosResponse> {
  return axios.get(`${URL}/api/markets`);
}
// 마켓개별로 디테일 불러오기
export function loadMarketDetailAPI(marketId: number): Promise<AxiosResponse> {
  return axios.get(`${URL}/api/mypage/market/detail/${marketId}`);
}
// 마켓게시글 불러오기
export function loadMarketPostDetailAPI(marketId: number, postId: number): Promise<AxiosResponse> {
  return axios.get(`${URL}/api/market/${marketId}/${postId}`);
}
// 마켓 게시물 삭제하기
export function deleteMarketPost(marketId: number, postId: number): Promise<AxiosResponse> {
  return axios.delete(`${URL}/api/mypage/market/${marketId}/${postId}`);
}
// 마켓 삭제하기
export function deleteMarket(marketId: number): Promise<AxiosResponse> {
  return axios.delete(`${URL}/api/mypage/market/${marketId}`);
}
// 마켓 등록 요청
export function addMarketPostAPI(market: FormData): Promise<AxiosResponse> {
  return axios({
    method: 'post',
    url: ' /api/mypage/market',
    headers: {
      'Content-Type': 'multipart/form-data',
    },
    data: market,
  });
}
