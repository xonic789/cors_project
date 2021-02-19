import axios, { AxiosResponse } from 'axios';
import { memberInterface, modifyProfileInterface } from '../interfaces/UserInterface';

export const LOGIN_ERROR = 'LOGIN_ERROR';
export const SERVER_ERROR = 'SERVER_ERROR';

export function postLoginAsync(user: { email: string, passwd: string }): Promise<memberInterface> | Promise<AxiosResponse> {
  return axios({
    method: 'post',
    url: '/api/login',
    params: {
      email: user.email,
      passwd: user.passwd,
    },
  }).then((result) => {
    console.log(result);
    const { nickname, profile_img: profileImg, latitude, longitude, role, articlelist, wishlist } = result.headers;

    const loginUser: memberInterface = {
      email: user.email,
      nickname,
      profileImg,
      latitude,
      longitude,
      role,
      articles: articlelist === undefined ? [] : JSON.parse(articlelist),
      wishList: wishlist === undefined ? [] : JSON.parse(wishlist),
    };
    return loginUser;
  }).catch((error) => {
    if (error.response.status === 400) {
      throw new Error(LOGIN_ERROR);
    } else if (error.response.status === 500) {
      throw new Error(SERVER_ERROR);
    } else {
      alert('서버에러 관리자 호출');
    }
    return error;
  });
}

export function socialLoginAsync(social: string): Promise<boolean> {
  return axios({
    method: 'post',
    url: `/api/oauth2/authorization/${social}`,
  }).then((result) => true).catch((error) => {
    if (error.response.status !== 400) {
      throw new Error('서버 통신 에러');
    }
    return false;
  });
}

export function logoutAsync(): Promise<boolean> {
  return axios({
    method: 'post',
    url: '/api/logout',
  }).then((result) => {
    console.log(result);
    return true;
  }).catch((error) => false);
}

export function modifyProfileAsync(modifyProfile: modifyProfileInterface): Promise<AxiosResponse> {
  return axios({
    method: 'put',
    url: '/api/change/profile',
    headers: {
      'Content-Type': 'multipart/form-data',
    },
    data: modifyProfile,
  }).then((res) => true).catch((error) => {
    if (error.response.status !== 400) {
      throw new Error('서버통신에러');
    }
    return error;
  });
}
