import shortId from 'shortid';

import { all, takeLatest, put, fork, call } from 'redux-saga/effects';
import { addPostInterface } from '../../interfaces/PostList.interface';

import { addBookPostAPI, getBookPostPurchaseAPI, getBookPostSalesAPI } from '../../api/postBookApi';
import { loadBookPostRequest, addBookPostRequest, addBookPostError, addBookPostSuccess } from './postSlice';

function* addBookPost(action: any) { // any는 구조 잡히면 변경 할거임
  try {
    const result = yield call(addBookPostAPI, action.payload.data);
    yield put({
      type: addBookPostSuccess,
      data: result,
    });
  } catch (err) {
    yield put({
      type: addBookPostError,
      error: err.response.data,
    });
  }
}

function* watchAddBookPost() {
  yield takeLatest(addBookPostRequest, addBookPost);
}

export default function* postSaga():Generator {
  yield all([
    fork(watchAddBookPost),
  ]);
}
