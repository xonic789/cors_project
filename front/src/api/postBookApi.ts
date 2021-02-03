import axios, { AxiosResponse } from 'axios';
import { addPostInterface } from '../interfaces/PostList.interface';

// 일반사용자 판매/구매글 추가
export function addBookPostAPI(data: addPostInterface):Promise<AxiosResponse> {
  return axios.post('api/article', data);
}
// 일반사용자 판매/구매글 리스트 불러오기
export function getBookPostAPI(division:string, categoryFilter?:string):Promise<AxiosResponse> {
  return axios.get(`http://local.corsmarket.ml/api/articles/${division}`, { params: { category: categoryFilter } });
}
// 일반사용자 판매/구매글 상세페이지 불러오기
export function getBookPostDetailViewAPI(postId: number):Promise<AxiosResponse> {
  return axios.get(`http://local.corsmarket.ml/api/article/${postId}`);
}
// 마켓리스트 불러오기
export function getMarketListAPI():Promise<AxiosResponse> {
  return axios.get('api/market');
}
// 마켓글 불러오기
export function getMarketBookAPI(marketId: number):Promise<AxiosResponse> {
  return axios.get(`api/market/${marketId}`);
}
// 해당 마켓에 판매글 추가
export function addMarketBookPostAPI(marketId: number):Promise<AxiosResponse> {
  return axios.post(`api/market/${marketId}`);
}
