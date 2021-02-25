import { all, fork } from 'redux-saga/effects';
import myPurchaseArticleSaga from './myPurchaseArticle/myPurchaseArticleSaga';
import mySaleArticleSaga from './mySlaeArticle/mySaleArticleSaga';
import myWishListSaga from './wishList/wishListSaga';
import noticeSaga from './notice/noticeSaga';
import questionSaga from './question/questionSaga';

export default function* myPageSaga(): Generator {
  yield all([
    fork(myPurchaseArticleSaga),
    fork(mySaleArticleSaga),
    fork(myWishListSaga),
    fork(noticeSaga),
    fork(questionSaga)]);
}
