import { combineReducers } from '@reduxjs/toolkit';
import postSlice from './pages/post/postSlice';
import detailViewSlice from './pages/post/detailPostView/detailViewSlice';
import userSlice from './pages/login/userSlice';
import mySaleArticleSlice from './pages/mySlaeArticle/mySaleArticleSlice';
import myPurchaseArticleSlice from './pages/myPurchaseArticle/myPurchaseArticleSlice';
import noticeSlice from './pages/notice/noticeSlice';
import wishListSlice from './pages/wishList/wishListSlice';

export default combineReducers({
  postSlice,
  detailViewSlice,
  userSlice,
  mySaleArticleSlice,
  myPurchaseArticleSlice,
  noticeSlice,
  wishListSlice,
});
