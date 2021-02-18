import { all, fork } from 'redux-saga/effects';

import postSaga from './features/postList/postSaga';
import addPostSaga from './features/addPost/addPostSaga';
import detailPostSaga from './features/detailPostView/detailViewSaga';
import myPageSaga from './pages/myPage/myPageSaga';
import userSaga from './pages/signIn/userSaga';

export default function* rootSaga(): Generator {
  yield all([
    fork(postSaga),
    fork(detailPostSaga),
    fork(userSaga),
    fork(addPostSaga),
    fork(myPageSaga)]);
}
