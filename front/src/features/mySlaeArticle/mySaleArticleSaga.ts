import { all, call, fork, put, takeLatest } from 'redux-saga/effects';
import {
  mySalesArtilceAsync,
} from '../../api/myArticleApi';
import {
  getMySaleArticleRequest,
  getMySaleArticleRequestSuccess,
  getMySaleArticleRequestError,
} from './mySaleArticleSlice';

function* getMySaleArticleRequestSaga(action: { payload: number }) {
  try {
    const result = yield call(mySalesArtilceAsync, action.payload);
    const { myAricleList, totalPage } = result.data.data;

    yield put({
      type: getMySaleArticleRequestSuccess,
      payload: { myAricleList, totalPage },
    });
  } catch (error) {
    yield put({
      type: getMySaleArticleRequestError,
      payload: error,
    });
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
