import { all, call, fork, put, takeLatest } from 'redux-saga/effects';
import {
  mySalesArtilceAsync,
} from '../../api/myArticleApi';
import dummyMyArticleList from './mockdata';
import {
  getMySaleArticleRequest,
  getMySaleArticleRequestSuccess,
  getMySaleArticleRequestError,
} from './mySaleArticleSlice';

function* getMySaleArticleRequestSaga(action: { payload: number }) {
  try {
    const result = yield call(mySalesArtilceAsync, action.payload);
    const { myAricleList, pageTotal } = result.data.data;
    console.log(result.data.data);
    yield put({
      type: getMySaleArticleRequestSuccess,
      payload: { myAricleList, pageTotal },
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
