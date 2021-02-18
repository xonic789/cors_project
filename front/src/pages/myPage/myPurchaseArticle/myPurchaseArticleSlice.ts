import { createSlice } from '@reduxjs/toolkit';

const myPurchaseArticleSlice = createSlice({
  name: 'myArticle',
  initialState: {
    myPurchaseArticle: [],
    totalPage: 0,
    isGetMyPurchaseArticlesLoading: false, // 내 판매글 가져오기
    isGetMyPurchaseArticlesSuccess: false,
    isGetMyPurchaseArticlesError: null,
  },
  reducers: {
    getMyPurchaseArticleRequest: (state, action) => {
      state.isGetMyPurchaseArticlesLoading = true;
    },
    getMyPurchaseArticleRequestSuccess: (state, action) => {
      state.isGetMyPurchaseArticlesLoading = false;
      state.isGetMyPurchaseArticlesSuccess = true;
      state.myPurchaseArticle = action.payload.myArticleList;
      state.totalPage = action.payload.totalPage;
    },
    getMyPurchaseArticleRequestError: (state, action) => {
      state.isGetMyPurchaseArticlesLoading = false;
      state.isGetMyPurchaseArticlesError = action.payload;
    },
  },
});

export const {
  getMyPurchaseArticleRequest,
  getMyPurchaseArticleRequestSuccess,
  getMyPurchaseArticleRequestError,
} = myPurchaseArticleSlice.actions;

export default myPurchaseArticleSlice.reducer;
