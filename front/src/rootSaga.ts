import { all, fork } from 'redux-saga/effects';

import postSaga from './pages/post/postSaga';
import detailPostSaga from './pages/post/detailPostView/detailViewSaga';
import myPageSaga from './pages/myPage/myPageSaga';
import marketSaga from './pages/market/marketSaga';
import userSaga from './pages/signIn/userSaga';

export default function* rootSaga(): Generator {
  yield all([
    fork(postSaga),
    fork(marketSaga),
    fork(detailPostSaga),
    fork(userSaga),
    fork(marketSaga),
    fork(myPageSaga),
  ]);
}
