import { PayloadAction } from '@reduxjs/toolkit';
import { all, call, fork, put, takeLatest } from 'redux-saga/effects';
import { getNoticeRequestAsync } from '../../api/noticeApi';
import {
  adminLoginRequest,
  adminLoginRequestSuccess,
  adminLoginRequestError,
  getNoticeRequest,
  getNoticeRequestSuccess,
  getNoticeRequestError,
} from './adminSlice';

function* adminLogin(action: PayloadAction<{ email: string, passwd: string }>) {
  try {
    yield console.log(action);
  } catch (error) {
    yield put({
      type: adminLoginRequestError,
      payload: error,
    });
  }
}

function* getNotice(action: PayloadAction<number>) {
  try {
    const result = yield getNoticeRequestAsync(action.payload);
    console.log(result);
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

function* watchAdminLogin(): Generator {
  yield takeLatest(adminLoginRequest, adminLogin);
}

function* watchGetNotice(): Generator {
  yield takeLatest(getNoticeRequest, getNotice);
}

export default function* noticeSaga(): Generator {
  yield all([
    fork(watchAdminLogin),
    fork(watchGetNotice),
  ]);
}
