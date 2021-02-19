import { all, call, fork, put, takeLatest } from 'redux-saga/effects';
import {
  myPurchaseArtilceAsync,
} from '../../../api/myArticleApi';
import {
  getMyPurchaseArticleRequest,
  getMyPurchaseArticleRequestSuccess,
  getMyPurchaseArticleRequestError,
} from './myPurchaseArticleSlice';

function* getMyPurchaseArticleRequestSaga(action: { payload: number }) {
  try {
    const result = yield call(myPurchaseArtilceAsync, action.payload);
    console.log(result);
    const { myArticleList, totalPage } = result.data.data;

    yield put({
      type: getMyPurchaseArticleRequestSuccess,
      payload: { myArticleList, totalPage },
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

export default function* myPurchaseArticleSaga(): Generator {
  yield all([
    fork(watchMyPurchaseArticle),
  ]);
}
