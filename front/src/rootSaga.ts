import { all, fork } from 'redux-saga/effects';

import postSaga from './pages/post/postSaga';
import detailPostSaga from './pages/post/detailPostView/detailViewSaga';
import userSaga from './pages/login/userSaga';
import mySaleArticleSaga from './pages/mySlaeArticle/mySaleArticleSaga';
import myPurchaseArticleSaga from './pages/myPurchaseArticle/myPurchaseArticleSaga';
import noticeSaga from './pages/notice/noticeSaga';
import wishListSaga from './pages/wishList/wishListSaga';

export default function* rootSaga(): Generator {
  yield all([
    fork(postSaga),
    fork(detailPostSaga),
    fork(userSaga),
    fork(mySaleArticleSaga),
    fork(myPurchaseArticleSaga),
    fork(noticeSaga),
    fork(wishListSaga)]);
}
