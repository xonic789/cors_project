import { all, fork } from 'redux-saga/effects';

import postSaga from './features/postList/postSaga';

export default function* rootSaga() {
  yield all([fork(postSaga)]);
}
