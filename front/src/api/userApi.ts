import axios, { AxiosResponse } from 'axios';
import { memberInterface, modifyProfileInterface } from '../interfaces/UserInterface';

export function postLoginAsync(user: { email: string, passwd: string }): Promise<memberInterface> {
  return axios({
    method: 'post',
    url: '/api/login',
    params: {
      email: user.email,
      passwd: user.passwd,
    },
  }).then((result) => {
    const { userId, nickname, profile_img: profileImg, latitude, longitude, role, articlelist, wishlist } = result.headers;

    console.log(result.headers);

    const loginUser: memberInterface = {
      userId,
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
    if (error.response.status !== 400) {
      throw new Error('서버 통신 에러');
    }
    return error;
  });
}

export function socialLoginAsync(social: string): Promise<boolean> {
  return axios({
    method: 'post',
    url: `/oauth2/authorization/${social}`,
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
  }).then((result) => true).catch((error) => {
    if (error.response.status !== 400) {
      throw new Error('서버 통신 에러');
    }
    return false;
  });
}

export function modifyProfileAsync(modifyProfile: modifyProfileInterface): Promise<AxiosResponse> {
  return axios({
    method: 'put',
    url: '/api/change/profile',
    params: modifyProfile,
  }).then((res) => true).catch((error) => {
    if (error.response.status !== 400) {
      throw new Error('서버통신에러');
    }
    return error;
  });
}
