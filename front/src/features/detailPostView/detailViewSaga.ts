import { PayloadAction } from '@reduxjs/toolkit';
import { all, takeLatest, put, fork, call } from 'redux-saga/effects';
import { dummyDetailBookPost } from '../../interfaces/mockdata';
import { loadDetailBookPostRequest, loadDetailBookPostSuccess, loadDetailBookPostError } from './detailViewSlice';

function* loadDetailBookPost(action: PayloadAction<number>) {
  try {
    // const result = yield call(getBookPostDetailViewAPI, action.payload);
    const result = dummyDetailBookPost;
    yield put(loadDetailBookPostSuccess(result));
  } catch (error) {
    yield put(loadDetailBookPostError({ error: error.response.data }));
  }
}

function* watchloadDetailBookPost() {
  yield takeLatest(loadDetailBookPostRequest, loadDetailBookPost);
}

export default function* postSaga():Generator {
  yield all([
    fork(watchloadDetailBookPost),
  ]);
}
