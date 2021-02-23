import { combineReducers } from '@reduxjs/toolkit';
import noticeSlice from './notice/noticeSlice';
import myPurchaseArticleSlice from './myPurchaseArticle/myPurchaseArticleSlice';
import mySaleArticleSlice from './mySlaeArticle/mySaleArticleSlice';
import wishListSlice from './wishList/wishListSlice';
import questionSlice from './question/questionSlice';
import myMarketSlice from './myMarket/myMarketSlice';

export default combineReducers({
  myPurchaseArticleSlice,
  mySaleArticleSlice,
  wishListSlice,
  noticeSlice,
  questionSlice,
  myMarketSlice,
});
