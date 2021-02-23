import { createSlice } from '@reduxjs/toolkit';

const wishListSlice = createSlice({
  name: 'myArticle',
  initialState: {
    wishList: [],
    totalPage: 0,
    isGetWishListsLoading: false, // 내 판매글 가져오기
    isGetWishListsSuccess: false,
    isGetWishListsError: null,
  },
  reducers: {
    getWishListRequest: (state, action) => {
      state.isGetWishListsLoading = true;
    },
    getWishListRequestSuccess: (state, action) => {
      state.isGetWishListsLoading = false;
      state.isGetWishListsSuccess = true;
      state.wishList = action.payload.myArticleList;
      state.totalPage = action.payload.totalPage;
    },
    getWishListRequestError: (state, action) => {
      state.isGetWishListsLoading = false;
      state.isGetWishListsError = action.payload;
    },
  },
});

export const {
  getWishListRequest,
  getWishListRequestSuccess,
  getWishListRequestError,
} = wishListSlice.actions;

export default wishListSlice.reducer;
