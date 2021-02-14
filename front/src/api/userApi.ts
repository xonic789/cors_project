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

export function socialLoginAsync(social: string): Promise<AxiosResponse> {
  return axios({
    method: 'post',
    url: `/oauth2/authorization/${social}`,
  }).then((result) => result.data);
}

export function logoutAsync(): Promise<AxiosResponse> {
  return axios({
    method: 'post',
    url: '/api/logout',
  });
}

export function modifyProfileAsync(modifyProfile: modifyProfileInterface): Promise<AxiosResponse> {
  console.log('async~!');
  return axios({
    method: 'post',
    url: '/api/change/mypage',
    params: modifyProfile,
  });
}
