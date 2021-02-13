import { ActionCreatorWithPayload, createSlice } from '@reduxjs/toolkit';
import { myArticleInterface } from '../../interfaces/MyArticle.interface';
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
  name: 'myArticle',
  initialState: {
    mySaleArticle: [],
    isGetMySaleArticlesLoading: false, // 내 판매글 가져오기
    isGetMySaleArticlesSuccess: false,
    isGetMySaleArticlesError: null,
    isGetMySaleArticlesScrollLoading: false, // 내 판매글 추가 가져오기(스크롤)
    isGetMySaleArticlesScrollSuccess: false,
    isGetMySaleArticlesScrollError: null,
  },
  reducers: {
    getMySaleArticleRequest: (state, action) => {
      state.isGetMySaleArticlesLoading = true;
    },
    getMySaleArticleRequestSuccess: (state, action) => {
      state.isGetMySaleArticlesLoading = false;
      state.isGetMySaleArticlesSuccess = true;
      state.mySaleArticle = action.payload;
    },
    getMySaleArticleRequestError: (state, action) => {
      state.isGetMySaleArticlesLoading = false;
      state.isGetMySaleArticlesError = action.payload;
    },
    getMySaleArticleScrollRequest: (state, action) => {
      state.isGetMySaleArticlesScrollLoading = true;
    },
    getMySaleArticleScrollRequestSuccess: (state, action) => {
      state.isGetMySaleArticlesScrollLoading = false;
      state.isGetMySaleArticlesScrollSuccess = true;
      state.mySaleArticle = state.mySaleArticle.concat(action.payload);
    },
    getMySaleArticleScrollRequestError: (state, action) => {
      state.isGetMySaleArticlesScrollLoading = false;
      state.isGetMySaleArticlesScrollError = action.payload;
    },
  },
});

export const {
  getMySaleArticleRequest,
  getMySaleArticleRequestSuccess,
  getMySaleArticleRequestError,
  getMySaleArticleScrollRequest,
  getMySaleArticleScrollRequestSuccess,
  getMySaleArticleScrollRequestError,
} = mySaleArticleSlice.actions;

export default mySaleArticleSlice.reducer;
