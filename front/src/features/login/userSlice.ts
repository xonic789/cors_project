import { createSlice } from '@reduxjs/toolkit';
import { memberInterface } from '../../interfaces/UserInterface';

const initialUser: memberInterface = {
  userId: '',
  nickname: '', // 닉네임
  profileImg: '', // 프로필 이미지
  latitude: 0, // 위도
  longitude: 0, // 경도
  role: '', // 권한
  articles: [], // 아이디 만
  wishList: [], // 아이디 만
};

const userSlice = createSlice({
  name: 'user',
  initialState: {
    user: initialUser,
    isLoginLoading: false, // 로그인
    isLoginSucceed: false,
    isLoginError: null,
    isLogoutLoading: false, // 로그아웃
    isLogoutSucceed: false,
    isLogoutError: null,
    isModifyProfileLoading: false, // 프로필 수정
    isModifyProfileSuccess: false,
    isModifyProfileError: null,
    isGetWishListLoading: false, // 내 찜목록 가져오기
    isGetWishListSuccess: false,
    isGetWishListError: null,
    isAddWishListLoading: false, // 찜하기
    isAddWishListSuccess: false,
    isAddWishListError: null,
    isRemoveWishListLoading: false, // 찜 해제하기
    isRemoveWishListSuccess: false,
    isRemoveWishListError: null,
    isGetMySaleArticlesLoading: false, // 내 판매글 가져오기
    isGetMySaleArticlesSuccess: false,
    isGetMySaleArticlesError: null,
    isGetMyPurchaseArticlesLoading: false, // 내 구매글 가져오기
    isGetMyPurchaseArticlesSuccess: false,
    isGetMyPurchaseArticlesError: null,
  },
  reducers: {
    postLoginRequest: (state, action) => {
      state.isLoginLoading = true;
      state.isLoginSucceed = false;
      state.isLoginError = null;
    },
    postSocialLoginRequest: (state, action) => {
      state.isLoginLoading = true;
      state.isLoginSucceed = false;
      state.isLoginError = null;
    },
    postLoginRequestSuccess: (state, action) => {
      state.isLoginLoading = false;
      state.isLoginSucceed = true;
      state.user = action.payload;
    },
    postLoginRequestError: (state, action) => {
      state.isLoginLoading = false;
      state.isLoginError = action.payload;
    },
    postLogoutRequest: (state, action) => {
      state.isLogoutLoading = true;
    },
    postLogoutRequestSuccess: (state, action) => {
      state.isLogoutLoading = false;
      state.isLogoutSucceed = true;
      state.user = initialUser;
    },
    postLogoutRequestError: (state, action) => {
      state.isLogoutLoading = false;
      state.isLogoutError = action.payload;
    },
    postModifyProfileRequest: (state, action) => {
      state.isModifyProfileLoading = true;
    },
    postModifyProfileRequestSuccess: (state, action) => {
      state.isModifyProfileLoading = false;
      state.isModifyProfileSuccess = true;
    },
    postModifyProfileRequestError: (state, action) => {
      state.isModifyProfileLoading = false;
      state.isModifyProfileError = action.payload;
    },
    getWishListRequest: (state, action) => {
      state.isGetWishListLoading = true;
    },
    getWishListRequestSuccess: (state, action) => {
      state.isGetWishListLoading = false;
      state.isGetWishListSuccess = true;
    },
    getWishListRequestError: (state, action) => {
      state.isGetWishListLoading = false;
      state.isGetWishListError = action.payload;
    },
    postAddWishListRequest: (state, action) => {
      state.isAddWishListLoading = true;
    },
    postAddWishListRequestSuccess: (state, action) => {
      state.isAddWishListLoading = false;
      state.isAddWishListSuccess = true;
    },
    postAddWishListRequestError: (state, action) => {
      state.isAddWishListLoading = false;
      state.isAddWishListError = action.payload;
    },
    postRemoveWishListRequest: (state, action) => {
      state.isRemoveWishListLoading = true;
    },
    postRemoveWishListRequestSuccess: (state, action) => {
      state.isRemoveWishListLoading = false;
      state.isRemoveWishListSuccess = true;
    },
    postRemoveWishListRequestError: (state, action) => {
      state.isRemoveWishListLoading = false;
      state.isRemoveWishListError = action.payload;
    },
    getMySaleArticleRequest: (state, action) => {
      state.isGetMySaleArticlesLoading = true;
    },
    getMySaleArticleRequestSuccess: (state, action) => {
      state.isGetMySaleArticlesLoading = false;
      state.isGetMySaleArticlesSuccess = true;
    },
    getMySaleArticleRequestError: (state, action) => {
      state.isGetMySaleArticlesLoading = false;
      state.isGetMySaleArticlesError = action.payload;
    },
    getMyPurchaseArticleRequest: (state, action) => {
      state.isGetMyPurchaseArticlesLoading = true;
    },
    getMyPurchaseArticleRequestSuccess: (state, action) => {
      state.isGetMyPurchaseArticlesLoading = false;
      state.isGetMyPurchaseArticlesSuccess = true;
    },
    getMyPurchaseArticleRequestError: (state, action) => {
      state.isGetMyPurchaseArticlesLoading = false;
      state.isGetMyPurchaseArticlesError = action.payload;
    },
  },
});

export const {
  postLoginRequest,
  postSocialLoginRequest,
  postLoginRequestSuccess,
  postLoginRequestError,
  postLogoutRequest,
  postLogoutRequestSuccess,
  postLogoutRequestError,
  postModifyProfileRequest,
  postModifyProfileRequestSuccess,
  postModifyProfileRequestError,
  getWishListRequest,
  getWishListRequestSuccess,
  getWishListRequestError,
  postAddWishListRequest,
  postAddWishListRequestSuccess,
  postAddWishListRequestError,
  postRemoveWishListRequest,
  postRemoveWishListRequestSuccess,
  postRemoveWishListRequestError,
  getMySaleArticleRequest,
  getMySaleArticleRequestSuccess,
  getMySaleArticleRequestError,
  getMyPurchaseArticleRequest,
  getMyPurchaseArticleRequestSuccess,
  getMyPurchaseArticleRequestError,
} = userSlice.actions;

export default userSlice.reducer;
