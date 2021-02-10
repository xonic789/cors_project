import { PayloadAction } from '@reduxjs/toolkit';
import { all, takeLatest, put, fork, call } from 'redux-saga/effects';
import { loadDetailBookPostRequest, loadDetailBookPostSuccess, loadDetailBookPostError } from './detailViewSlice';

import { dummyDetailBookPost } from '../../interfaces/mockdata';
import { getBookPostDetailViewAPI } from '../../api/postBookApi';

function* loadDetailBookPost(action: PayloadAction<number>) {
  try {
    const result = yield call(getBookPostDetailViewAPI, action.payload);
    // const result = dummyDetailBookPost;
    yield put(loadDetailBookPostSuccess(result.data));
  } catch (error) {
    yield put(loadDetailBookPostError({ error }));
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
