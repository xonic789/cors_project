import { PayloadAction } from '@reduxjs/toolkit';
import { all, takeLatest, put, fork, call, throttle } from 'redux-saga/effects';
import { AddBookPostInterface } from '../../interfaces/PostList.interface';
import { dummyBookPost, generateDummyPost } from './mockdata';
import { addBookPostAPI, getBookPostAPI } from '../../api/postBookApi';
import { loadBookPostRequest, loadBookPostSuccess, loadBookPostError,
  loadScrollBookPostRequest, loadScrollBookPostSuccess, loadScrollBookPostError,
  addBookPostRequest, addBookPostError, addBookPostSuccess } from './postSlice';

interface addBookPostPayloadInterface {
  data: AddBookPostInterface
}
interface loadBookPost {
  division: 'purchase' | 'sale',
  categoty?: string
}
function* loadBookPost(action: PayloadAction<loadBookPost>) {
  try {
    // const result = yield call(getBookPostAPI, action.payload.division);
    const result = generateDummyPost(10);
    console.log(result);
    yield put(loadBookPostSuccess(result));
  } catch (err) {
    yield put(loadBookPostError({ error: err }));
  }
}
function* loadScrollBookPost(action: PayloadAction<loadBookPost>) {
  try {
    // const result = yield call(getBookPostAPI, action.payload.division);
    const result = generateDummyPost(20);
    console.log(result);
    yield put(loadScrollBookPostSuccess(result));
  } catch (err) {
    yield put(loadScrollBookPostError({ error: err }));
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
function* watchloadScrollBookPost() {
  yield throttle(1000, loadScrollBookPostRequest, loadScrollBookPost);
}
function* watchAddBookPost() {
  yield takeLatest(addBookPostRequest, addBookPost);
}

export default function* postSaga():Generator {
  yield all([
    fork(watchloadScrollBookPost),
    fork(watchAddBookPost),
    fork(watchLoadBookPost),
  ]);
}
