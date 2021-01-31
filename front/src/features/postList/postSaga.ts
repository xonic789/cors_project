import { PayloadAction } from '@reduxjs/toolkit';
import { all, takeLatest, put, fork, call } from 'redux-saga/effects';
import { addPostInterface } from '../../interfaces/PostList.interface';
import PostList, { PurchasePostList } from './mockdata';

import { addBookPostAPI, getBookPostAPI } from '../../api/postBookApi';
import { loadBookPostRequest, loadBookPostSuccess, loadBookPostError, addBookPostRequest, addBookPostError, addBookPostSuccess } from './postSlice';

interface addBookPostPayloadInterface {
  data: addPostInterface
}
interface loadSaleBookPost {
  division: 'purchase' | 'sale',
  categoty?: string
}
function* loadBookPost(action: PayloadAction<loadSaleBookPost>) {
  try {
    // const result = yield call(getBookPostAPI, action.payload.division);
    let result = PostList;
    if (action.payload.division === 'purchase') {
      result = PurchasePostList;
    }
    yield put(loadBookPostSuccess(result));
  } catch (err) {
    yield put(loadBookPostError({ error: err.response.data }));
  }
}
function* addBookPost(action: PayloadAction<addBookPostPayloadInterface>) {
  try {
    const result = yield call(addBookPostAPI, action.payload.data);
    yield put(addBookPostSuccess({ result }));
  } catch (err) {
    yield put(addBookPostError({ error: err.response.data }));
  }
}

function* watchLoadBookPost() {
  yield takeLatest(loadBookPostRequest, loadBookPost);
}
function* watchAddBookPost() {
  yield takeLatest(addBookPostRequest, addBookPost);
}

export default function* postSaga():Generator {
  yield all([
    fork(watchAddBookPost),
    fork(watchLoadBookPost),
  ]);
}
