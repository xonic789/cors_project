import { createSlice } from '@reduxjs/toolkit';

const mySaleArticleSlice = createSlice({
  name: 'myArticle',
  initialState: {
    mySaleArticle: [],
    totalPage: 0,
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
      state.mySaleArticle = action.payload.myAricleList;
      state.totalPage = action.payload.pageTotal;
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
