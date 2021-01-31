import { PayloadAction } from '@reduxjs/toolkit';
import shortId from 'shortid';
import { all, takeLatest, put, fork, call } from 'redux-saga/effects';
import { addPostInterface } from '../../interfaces/PostList.interface';

import { addBookPostAPI, getBookPostPurchaseAPI, getBookPostSaleAPI } from '../../api/postBookApi';
import { loadBookPostRequest, loadBookPostSuccess, loadBookPostError, addBookPostRequest, addBookPostError, addBookPostSuccess } from './postSlice';

interface addBookPostPayloadInterface {
  data: addPostInterface
}

function* loadSaleBookPost() {
  try {
    const result = yield call(getBookPostSaleAPI);
    yield put({
      type: loadBookPostSuccess,
      data: result,
    });
  } catch (err) {
    yield put({
      type: loadBookPostError,
      error: err.response.data,
    });
  }
}
function* loadPurchaseBookPost() {
  try {
    const result = yield call(getBookPostPurchaseAPI);
    yield put({
      type: loadBookPostSuccess,
      data: result,
    });
  } catch (err) {
    yield put({
      type: loadBookPostError,
      error: err.response.data,
    });
  }
}
function* addBookPost(action: PayloadAction<addBookPostPayloadInterface>) {
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

function* watchLoadSaleBookPost() {
  yield takeLatest(loadBookPostRequest, loadSaleBookPost);
}
function* watchAddBookPost() {
  yield takeLatest(addBookPostRequest, addBookPost);
}
function* watchLoadPurchaseBookPost() {
  yield takeLatest(loadBookPostRequest, loadPurchaseBookPost);
}

export default function* postSaga():Generator {
  yield all([
    fork(watchAddBookPost),
    fork(watchLoadPurchaseBookPost),
    fork(watchLoadSaleBookPost),
  ]);
}
