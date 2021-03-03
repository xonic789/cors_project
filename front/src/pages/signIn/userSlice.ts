import { createSlice } from '@reduxjs/toolkit';
import { memberInterface } from '../../interfaces/UserInterface';

const initialUser: memberInterface = {
  email: '', // 고유번호
  nickname: '', // 닉네임
  profileImg: '', // 프로필 이미지
  latitude: 0, // 위도
  longitude: 0, // 경도
  role: '', // 권한
  articles: [], // 아이디 만
  wishList: [], // 아이디 만
  myMarketList: [],
};

const initialUserState = {
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
  isGetUserInfoLoading: false,
  isGetUserInfoSuccess: false,
  isGetUserInfoError: null,
};

const userSlice = createSlice({
  name: 'user',
  initialState: initialUserState,
  reducers: {
    postLoginRequest: (state, action) => {
      state.isLoginLoading = true;
    },
    postSocialLoginRequest: (state, action) => {
      state.isLoginLoading = true;
    },
    postLoginRequestSuccess: (state, action) => {
      state.isLoginLoading = false;
      state.isLoginSucceed = true;
      state.user = action.payload;
    },
    postLoginRequestError: (state, action) => {
      state.isLoginLoading = false;
      state.isLoginSucceed = false;
      state.isLoginError = action.payload;
    },
    postLogoutRequest: (state, action) => {
      state.isLogoutLoading = true;
    },
    postLogoutRequestSuccess: (state, action) => {
      state.isLogoutLoading = false;
      state.isLogoutSucceed = true;
      state.isLoginSucceed = false;
      state.user = initialUserState.user;
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
      state.user.nickname = action.payload.nickname;
      state.user.profileImg = action.payload.profileImg;
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
      state.user.wishList = state.user.wishList.concat(action.payload);
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
      state.user.wishList = state.user.wishList.filter((item) => (item !== action.payload));
    },
    postRemoveWishListRequestError: (state, action) => {
      state.isRemoveWishListLoading = false;
      state.isRemoveWishListError = action.payload;
    },
    setMyMarketList: (state, action) => {
      state.user.myMarketList = action.payload;
    },
    getUserInfoRequest: (state, action) => {
      state.isGetUserInfoLoading = true;
      state.isGetUserInfoSuccess = false;
      state.isGetUserInfoError = null;
    },
    getUserInfoRequestSuccess: (state, action) => {
      state.isGetUserInfoLoading = false;
      state.isGetUserInfoSuccess = true;
      state.isGetUserInfoError = null;
    },
    getUserInfoRequestError: (state, action) => {
      state.isGetUserInfoLoading = false;
      state.isGetUserInfoSuccess = false;
      state.isGetUserInfoError = action.payload;
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
  setMyMarketList,
  getUserInfoRequest,
  getUserInfoRequestSuccess,
  getUserInfoRequestError,
} = userSlice.actions;

export default userSlice.reducer;
