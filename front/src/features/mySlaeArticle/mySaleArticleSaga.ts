import { all, call, fork, put, takeLatest } from 'redux-saga/effects';
import {
  mySalesArtilceAsync,
} from '../../api/myArticleApi';
import {
  getMySaleArticleRequest,
  getMySaleArticleRequestSuccess,
  getMySaleArticleRequestError,
} from './mySaleArticleSlice';

function* getMySaleArticleRequestSaga() {
  try {
    const result = yield call(mySalesArtilceAsync);
    console.log(result);
  } catch (error) {
    alert('에러');
  }
}

function* watchMySaleArticle(): Generator {
  yield takeLatest(getMySaleArticleRequest, getMySaleArticleRequestSaga);
}

export default function* loginSaga(): Generator {
  yield all([
    fork(watchMySaleArticle),
  ]);
}
