import { createSlice } from '@reduxjs/toolkit';
import { memberInterface } from '../../interfaces/UserInterface';

const initialUser: memberInterface = {
  nickname: '', // 닉네임
  profileImg: '', // 프로필 이미지
  latitude: 0, // 위도
  longitude: 0, // 경도
  role: '', // 권한
  articles: [], // 아이디 만
  wishList: [], // 아이디 만
};

const mySaleArticleSlice = createSlice({
  name: 'user',
  initialState: {
    isGetMySaleArticlesLoading: false, // 내 판매글 가져오기
    isGetMySaleArticlesSuccess: false,
    isGetMySaleArticlesError: null,
  },
  reducers: {
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
  },
});

export const {
  getMySaleArticleRequest,
  getMySaleArticleRequestSuccess,
  getMySaleArticleRequestError,
} = mySaleArticleSlice.actions;

export default mySaleArticleSlice.reducer;
