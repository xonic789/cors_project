import { combineReducers } from '@reduxjs/toolkit';
import myPurchaseArticleSlice from './myPurchaseArticle/myPurchaseArticleSlice';
import mySaleArticleSlice from './mySlaeArticle/mySaleArticleSlice';
import myWishListSlice from './wishList/wishListSlice';
import noticeSlice from './notice/noticeSlice';
import questionSlice from './question/questionSlice';

export default combineReducers({
  myPurchaseArticleSlice,
  mySaleArticleSlice,
  myWishListSlice,
  noticeSlice,
  questionSlice,
});
