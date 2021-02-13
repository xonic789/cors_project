import { all, call, fork, put, takeLatest } from 'redux-saga/effects';
import {
  mySalesArtilceAsync,
} from '../../api/myArticleApi';
import dummyMyArticleList from './mockdata';
import {
  getMySaleArticleRequest,
  getMySaleArticleRequestSuccess,
  getMySaleArticleRequestError,
  getMySaleArticleScrollRequest,
  getMySaleArticleScrollRequestSuccess,
  getMySaleArticleScrollRequestError,
} from './mySaleArticleSlice';

function* getMySaleArticleRequestSaga() {
  try {
    // const result = yield call(mySalesArtilceAsync);
    const result = yield dummyMyArticleList;
    console.log(result);
    yield put({
      type: getMySaleArticleRequestSuccess,
      payload: result,
    });
  } catch (error) {
    yield put({
      type: getMySaleArticleRequestError,
      payload: error,
    });
  }
}

// function* getMySaleArticleScrollRequestSaga() {
//   try {
//     const result = dummyMyArticleList;
//     console.log(result);
//     yield put(loadScrollBookPostSuccess(result));
//   } catch (error) {
//     yield put(loadScrollBookPostError({ error: error.response.data }));
//   }
// }

function* watchMySaleArticle(): Generator {
  yield takeLatest(getMySaleArticleRequest, getMySaleArticleRequestSaga);
}

export default function* loginSaga(): Generator {
  yield all([
    fork(watchMySaleArticle),
  ]);
}
