import axios, { AxiosResponse } from 'axios';
import { AddBookPostInterface } from '../interfaces/PostList.interface';

const BASEURL = 'https://corsmarket.shop';

const FormDataConfig = {
  headers: {
    'Content-Type': 'multipart/form-data',
  },
};
// 알라딘 API
export function getAladinBook(title: string):Promise<AxiosResponse> {
  return axios.get(`https://corsmarket.shop/ttb/api/ItemSearch.aspx?ttbkey=ttbehaakdl1816001&QueryType=Title&Query=${title}&output=js`);
}
// 일반사용자 판매/구매글 추가
export function addBookPostAPI(data: AddBookPostInterface):Promise<AxiosResponse> {
  return axios.post(`${BASEURL}/api/article`, data, FormDataConfig);
}
export function deleteBookPostAPI(id: number):Promise<AxiosResponse> {
  return axios.delete(`${BASEURL}/api/article/${id}`);
}
// 일반사용자게시글 수정하기
export function modifyBookPostAPI(id: number, data: AddBookPostInterface):Promise<AxiosResponse> {
  return axios.put(`${BASEURL}/api/article/${id}`, data, FormDataConfig);
}
// 일반사용자 판매/구매글 리스트 불러오기
export function getBookPostAPI(filtering:{ division:string, category:string, title?: string}, id?: number):Promise<AxiosResponse> {
  console.log(filtering.title);
  return axios.get(`${BASEURL}/api/articles/${filtering.division}`, {
    params: {
      category: filtering.category,
      lastId: id,
      title: filtering.title,
    },
  });
}
// 일반사용자 판매/구매글 상세페이지 불러오기
export function getBookPostDetailViewAPI(postId: number):Promise<AxiosResponse> {
  return axios.get(`${BASEURL}/api/article/${postId}`);
}
// 마켓리스트 불러오기
export function getMarketListAPI():Promise<AxiosResponse> {
  return axios.get(`${BASEURL}/api/market`);
}
export function deleteMarketPostAPI(id: number):Promise<AxiosResponse> {
  return axios.get(`${BASEURL}/api/market/delete`,
    { params: { articleId: id } });
}
// 마켓글 불러오기
export function getMarketBookAPI(marketId: number):Promise<AxiosResponse> {
  return axios.get(`${BASEURL}/api/market/${marketId}`);
}
// 해당 마켓에 판매글 추가
export function addMarketBookPostAPI(data: AddBookPostInterface):Promise<AxiosResponse> {
  return axios.post(`${BASEURL}/api/market/article`, data, FormDataConfig);
}
