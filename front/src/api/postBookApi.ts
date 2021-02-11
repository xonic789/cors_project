import axios, { AxiosResponse } from 'axios';
import { AddBookPostInterface } from '../interfaces/PostList.interface';

export function getAladinBook(title: string):Promise<AxiosResponse> {
  return axios.get(`http://local.corsmarket.ml/ttb/api/ItemSearch.aspx?ttbkey=ttbehaakdl1816001&QueryType=Title&Query=${title}&output=js`);
}
// 일반사용자 판매/구매글 추가
export function addBookPostAPI(data: AddBookPostInterface):Promise<AxiosResponse> {
  const config = {
    headers: {
      'content-type': 'multipart/form-data',
    },
  };
  return axios.post('http://local.corsmarket.ml/api/article', data, config);
}
export function deleteBookPostAPI(id: number):Promise<AxiosResponse> {
  return axios.delete(`http://local.corsmarket.ml/api/article/${id}`);
}
// 일반사용자 판매/구매글 리스트 불러오기
export function getBookPostAPI(filtering:{ division:string, category:string }, id?: number):Promise<AxiosResponse> {
  console.log(filtering);
  return axios.get(`http://local.corsmarket.ml/api/articles/${filtering.division}`, {
    params: {
      category: filtering.category, lastId: id,
    },
  });
}
// 일반사용자 판매/구매글 상세페이지 불러오기
export function getBookPostDetailViewAPI(postId: number):Promise<AxiosResponse> {
  return axios.get(`http://local.corsmarket.ml/api/article/${postId}`);
}
// 마켓리스트 불러오기
export function getMarketListAPI():Promise<AxiosResponse> {
  return axios.get('http://local.corsmarket.ml/api/market');
}
// 마켓글 불러오기
export function getMarketBookAPI(marketId: number):Promise<AxiosResponse> {
  return axios.get(`http://local.corsmarket.ml/api/market/${marketId}`);
}
// 해당 마켓에 판매글 추가
export function addMarketBookPostAPI(marketId: number):Promise<AxiosResponse> {
  return axios.post(`http://local.corsmarket.ml/api/market/${marketId}`);
}
