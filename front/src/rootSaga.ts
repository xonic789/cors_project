import { all, fork } from 'redux-saga/effects';

import postSaga from './features/postList/postSaga';
import addPostSaga from './features/addPost/addPostSaga';
import detailPostSaga from './features/detailPostView/detailViewSaga';
import loginSaga from './features/login/LoginSaga';

export default function* rootSaga(): Generator {
  yield all([fork(postSaga), fork(detailPostSaga), fork(loginSaga), fork(addPostSaga)]);
}
