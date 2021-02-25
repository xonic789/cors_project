import { PayloadAction } from '@reduxjs/toolkit';
import { all, takeLatest, put, fork, call, throttle, select } from 'redux-saga/effects';
import { AddBookPostInterface } from '../../interfaces/PostList.interface';
import { addBookPostAPI, deleteBookPostAPI, getBookPostAPI, modifyBookPostAPI } from '../../api/postBookApi';
import {
  loadBookPostRequest, loadBookPostSuccess, loadBookPostError,
  loadScrollBookPostRequest, loadScrollBookPostSuccess, loadScrollBookPostError,
  deleteBookPostSuccess, deleteBookPostError, deleteBookPostRequest,
  addBookPostRequest, addBookPostError, addBookPostSuccess,
  modifyBookPostRequest, modifyBookPostSuccess, modifyBookPostError,
} from './postSlice';
import { push } from '../../utils/historyUtil';

interface loadBookPost {
  filtering: { division:string, category:string, title?:string },
  lastId?: number,
}
interface addBookPostPayloadInterface {
  data: AddBookPostInterface
}
interface modifyBookPostPayloadInterface {
  id: number,
  data: AddBookPostInterface
}
function* loadBookPost(action: PayloadAction<loadBookPost>) {
  try {
    const result = yield call(getBookPostAPI, action.payload.filtering);
    yield put(loadBookPostSuccess(result.data));
  } catch (error) {
    yield put(loadBookPostError({ error }));
  }
}
function* loadScrollBookPost(action: PayloadAction<loadBookPost>) {
  try {
    const { filtering } = yield select((state) => state.postSlice);
    const result = yield call(getBookPostAPI, filtering, action.payload.lastId);
    yield put(loadScrollBookPostSuccess(result.data));
  } catch (error) {
    yield put(loadScrollBookPostError({ error }));
  }
}
function* addBookPost(action: PayloadAction<addBookPostPayloadInterface>) {
  try {
    const result = yield call(addBookPostAPI, action.payload.data);
    yield put(addBookPostSuccess(result.data));
    yield call(push, '/home');
  } catch (error) {
    yield put(addBookPostError({ error: error.response }));
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
function* modifyBookPost(action: PayloadAction<modifyBookPostPayloadInterface>) {
  try {
    const result = yield call(modifyBookPostAPI, action.payload.id, action.payload.data);
    yield put(modifyBookPostSuccess(result.data));
  } catch (error) {
    yield put(modifyBookPostError({ error }));
  }
}

function* watchLoadBookPost() {
  yield takeLatest(loadBookPostRequest, loadBookPost);
}
function* watchAddBookPost() {
  yield takeLatest(addBookPostRequest, addBookPost);
}
function* watchloadScrollBookPost() {
  yield throttle(2000, loadScrollBookPostRequest, loadScrollBookPost);
}
function* watchdeleteBookPost() {
  yield takeLatest(deleteBookPostRequest, deleteBookPost);
}
function* watchloadModifyBookPost() {
  yield takeLatest(modifyBookPostRequest, modifyBookPost);
}
export default function* postSaga():Generator {
  yield all([
    fork(watchloadScrollBookPost),
    fork(watchloadModifyBookPost),
    fork(watchAddBookPost),
    fork(watchLoadBookPost),
    fork(watchdeleteBookPost),
  ]);
}
