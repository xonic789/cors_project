import { PayloadAction } from '@reduxjs/toolkit';
import { all, takeLatest, put, fork, call, throttle, select } from 'redux-saga/effects';
import { AddBookPostInterface } from '../../interfaces/PostList.interface';
import { generateDummyPost } from '../../interfaces/mockdata';
import { addBookPostAPI, deleteBookPostAPI, getBookPostAPI, getBookPostDetailViewAPI } from '../../api/postBookApi';
import { loadBookPostRequest, loadBookPostSuccess, loadBookPostError,
  loadScrollBookPostRequest, loadScrollBookPostSuccess, loadScrollBookPostError, deleteBookPostSuccess, deleteBookPostError, deleteBookPostRequest } from './postSlice';

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
function* deleteBookPost(action: PayloadAction<number>) {
  try {
    yield call(deleteBookPostAPI, action.payload);
    yield put(deleteBookPostSuccess(action.payload));
  } catch (error) {
    yield put(deleteBookPostError({ error: error.response.data }));
  }
}
function* watchLoadBookPost() {
  yield takeLatest(loadBookPostRequest, loadBookPost);
}
function* watchloadScrollBookPost() {
  yield throttle(2000, loadScrollBookPostRequest, loadScrollBookPost);
}
function* watchdeleteBookPost() {
  yield takeLatest(deleteBookPostRequest, deleteBookPost);
}

export default function* postSaga():Generator {
  yield all([
    fork(watchloadScrollBookPost),
    fork(watchLoadBookPost),
    fork(watchdeleteBookPost),
  ]);
}
