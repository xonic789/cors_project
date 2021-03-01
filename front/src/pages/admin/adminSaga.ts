import { PayloadAction } from '@reduxjs/toolkit';
import { all, call, fork, put, takeLatest } from 'redux-saga/effects';
import {
  adminLoginRequest,
  adminLoginRequestSuccess,
  adminLoginRequestError,
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

function* watchAdminLogin(): Generator {
 yield takeLatest(adminLoginRequest, adminLogin);
}

export default function* noticeSaga(): Generator {
  yield all([
    fork(watchAdminLogin),
  ]);
}
