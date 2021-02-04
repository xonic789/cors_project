import { all, fork } from 'redux-saga/effects';

import postSaga from './features/postList/postSaga';
import loginSaga from './features/login/LoginSaga';

export default function* rootSaga() {
  yield all([fork(postSaga), fork(loginSaga)]);
}
