import { PayloadAction } from '@reduxjs/toolkit';
import { all, call, fork, put, takeLatest } from 'redux-saga/effects';
import { getQuestionRequestAsync } from '../../../api/questionAsync';
import {
  getQuestionRequest,
  getQuestionRequestSuccess,
  getQuestionRequestError,
} from './questionSlice';

function* getNoticeRequestSaga(action: PayloadAction<number>) {
  try {
    const result = yield call(getQuestionRequestAsync, action.payload);

    yield put({
      type: getQuestionRequestSuccess,
      payload: result,
    });
  } catch (error) {
    yield put({
      type: getQuestionRequestError,
      payload: error,
    });
  }
}

function* watchNotice(): Generator {
  yield takeLatest(getQuestionRequest, getNoticeRequestSaga);
}

export default function* loginSaga(): Generator {
  yield all([
    fork(watchNotice),
  ]);
}
