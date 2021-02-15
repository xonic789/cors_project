import { PayloadAction } from '@reduxjs/toolkit';
import { all, call, fork, put, takeLatest } from 'redux-saga/effects';
import { getNoticeRequestAsync } from '../../api/noticeApi';
import {
  getNoticeRequest,
  getNoticeRequestSuccess,
  getNoticeRequestError,
} from './noticeSlice';

function* getNoticeRequestSaga(action: PayloadAction<number>) {
  try {
    const result = yield call(getNoticeRequestAsync, action.payload);

    yield put({
      type: getNoticeRequestSuccess,
      payload: result,
    });
  } catch (error) {
    yield put({
      type: getNoticeRequestError,
      payload: error,
    });
  }
}

function* watchNotice(): Generator {
  yield takeLatest(getNoticeRequest, getNoticeRequestSaga);
}

export default function* loginSaga(): Generator {
  yield all([
    fork(watchNotice),
  ]);
}
