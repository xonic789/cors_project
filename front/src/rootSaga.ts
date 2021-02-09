import { all, fork } from 'redux-saga/effects';

import postSaga from './features/postList/postSaga';
import userSaga from './features/login/userSaga';

export default function* rootSaga() {
  yield all([fork(postSaga), fork(userSaga)]);
}
