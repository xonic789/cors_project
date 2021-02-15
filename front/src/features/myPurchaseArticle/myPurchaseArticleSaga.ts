import { all, call, fork, put, takeLatest } from 'redux-saga/effects';
import {
  myPurchaseArtilceAsync,
} from '../../api/myArticleApi';
import {
  getMyPurchaseArticleRequest,
  getMyPurchaseArticleRequestSuccess,
  getMyPurchaseArticleRequestError,
} from './myPurchaseArticleSlice';

function* getMyPurchaseArticleRequestSaga(action: { payload: number }) {
  try {
    const result = yield call(myPurchaseArtilceAsync, action.payload);
    const { myAricleList, pageTotal } = result.data.data;

    yield put({
      type: getMyPurchaseArticleRequestSuccess,
      payload: { myAricleList, pageTotal },
    });
  } catch (error) {
    yield put({
      type: getMyPurchaseArticleRequestError,
      payload: error,
    });
  }
}

function* watchMyPurchaseArticle(): Generator {
  yield takeLatest(getMyPurchaseArticleRequest, getMyPurchaseArticleRequestSaga);
}

export default function* loginSaga(): Generator {
  yield all([
    fork(watchMyPurchaseArticle),
  ]);
}
