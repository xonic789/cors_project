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

const initialState = {
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
  isAddWishListLoading: false, // 찜하기
  isAddWishListSuccess: false,
  isAddWishListError: null,
  isRemoveWishListLoading: false, // 찜 해제하기
  isRemoveWishListSuccess: false,
  isRemoveWishListError: null,
};

const userSlice = createSlice({
  name: 'user',
  initialState,
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
      state.isLogoutSucceed = false;
      state.isLogoutError = null;
    },
    postLogoutRequestSuccess: (state, action) => {
      state.isLogoutLoading = false;
      state.isLogoutSucceed = true;
      state.isLogoutError = null;
      state = initialState;
    },
    postLogoutRequestError: (state, action) => {
      state.isLogoutLoading = false;
      state.isLogoutSucceed = false;
      state.isLogoutError = action.payload;
    },
    postModifyProfileRequest: (state, action) => {
      state.isModifyProfileLoading = true;
    },
    postModifyProfileRequestSuccess: (state, action) => {
      state.isModifyProfileLoading = false;
      state.isModifyProfileSuccess = true;
      state.user.nickname = action.payload;
    },
    postModifyProfileRequestError: (state, action) => {
      state.isModifyProfileLoading = false;
      state.isModifyProfileError = action.payload;
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
  postAddWishListRequest,
  postAddWishListRequestSuccess,
  postAddWishListRequestError,
  postRemoveWishListRequest,
  postRemoveWishListRequestSuccess,
  postRemoveWishListRequestError,
} = userSlice.actions;

export default userSlice.reducer;
