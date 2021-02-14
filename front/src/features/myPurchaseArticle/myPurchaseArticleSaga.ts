import { all, call, fork, put, takeLatest } from 'redux-saga/effects';
import {
  myPurchaseArtilceAsync,
} from '../../api/myArticleApi';
import dummyMyArticleList from './mockdata';
import {
  getMyPurchaseArticleRequest,
  getMyPurchaseArticleRequestSuccess,
  getMyPurchaseArticleRequestError,
} from './myPurchaseArticleSlice';

function* getMyPurchaseArticleRequestSaga(action: { payload: number }) {
  try {
    const result = yield call(myPurchaseArtilceAsync, action.payload);
    const { myAricleList, pageTotal } = result.data.data;
    console.log(result.data.data);
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

// function* getMyPurchaseArticleScrollRequestSaga() {
//   try {
//     const result = dummyMyArticleList;
//     console.log(result);
//     yield put(loadScrollBookPostSuccess(result));
//   } catch (error) {
//     yield put(loadScrollBookPostError({ error: error.response.data }));
//   }
// }

function* watchMyPurchaseArticle(): Generator {
  yield takeLatest(getMyPurchaseArticleRequest, getMyPurchaseArticleRequestSaga);
}

export default function* loginSaga(): Generator {
  yield all([
    fork(watchMyPurchaseArticle),
  ]);
}
