import { all, fork } from 'redux-saga/effects';

import postSaga from './features/postList/postSaga';
import addPostSaga from './features/addPost/addPostSaga';
import detailPostSaga from './features/detailPostView/detailViewSaga';
import userSaga from './features/login/userSaga';
import mySaleArticleSaga from './features/mySlaeArticle/mySaleArticleSaga';
import myPurchaseArticleSaga from './features/myPurchaseArticle/myPurchaseArticleSaga';
import noticeSaga from './features/notice/noticeSaga';
import wishListSaga from './features/wishList/wishListSaga';
import questionSaga from './features/question/questionSaga';

export default function* rootSaga(): Generator {
  yield all([
    fork(postSaga),
    fork(detailPostSaga),
    fork(userSaga),
    fork(addPostSaga),
    fork(mySaleArticleSaga),
    fork(myPurchaseArticleSaga),
    fork(noticeSaga),
    fork(wishListSaga),
    fork(questionSaga)]);
}
