import { PayloadAction } from '@reduxjs/toolkit';
import { all, takeLatest, put, fork, call, throttle, select } from 'redux-saga/effects';
import { AddBookPostInterface } from '../../interfaces/PostList.interface';
import { dummyBookPost, dummyDetailBookPost, generateDummyPost } from './mockdata';
import { addBookPostAPI, getBookPostAPI, getBookPostDetailViewAPI } from '../../api/postBookApi';
import postSlice, { loadBookPostRequest, loadBookPostSuccess, loadBookPostError,
  loadScrollBookPostRequest, loadScrollBookPostSuccess, loadScrollBookPostError,
  addBookPostRequest, addBookPostError, addBookPostSuccess,
  loadDetailBookPostSuccess } from './postSlice';

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
  } catch (error) {
    yield put(loadBookPostError({ error: error.response.data }));
  }
}
function* loadScrollBookPost() {
  try {
    const { filtering } = yield select((state) => state.postSlice);
    // const result = yield call(getBookPostAPI, filtering);
    console.log(filtering);
    const result = generateDummyPost(10);
    console.log(result);
    yield put(loadScrollBookPostSuccess(result));
  } catch (error) {
    yield put(loadScrollBookPostError({ error: error.response.data }));
  }
}
function* loadDetailBookPost(action: PayloadAction<number>) {
  try {
    // const result = yield call(getBookPostDetailViewAPI, action.payload);
    const result = dummyDetailBookPost;
    yield put(loadDetailBookPostSuccess(result));
  } catch (error) {
    yield put(addBookPostError({ error: error.response.data }));
  }
}
function* addBookPost(action: PayloadAction<addBookPostPayloadInterface>) {
  try {
    const result = yield call(addBookPostAPI, action.payload.data);
    yield put(addBookPostSuccess({ result }));
  } catch (error) {
    yield put(addBookPostError({ error: error.response.data }));
  }
}
function* watchLoadBookPost() {
  yield takeLatest(loadBookPostRequest, loadBookPost);
}
function* watchloadScrollBookPost() {
  yield throttle(2000, loadScrollBookPostRequest, loadScrollBookPost);
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
