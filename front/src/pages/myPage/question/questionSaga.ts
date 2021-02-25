import { PayloadAction } from '@reduxjs/toolkit';
import { all, call, fork, put, takeLatest } from 'redux-saga/effects';
import { getQuestionRequestAsync, getQuestionDetailRequestAsync } from '../../../api/questionApi';
import {
  getQuestionRequest,
  getQuestionRequestSuccess,
  getQuestionRequestError,
  getQuestionDetailRequest,
  getQuestionDetailRequestSuccess,
  getQuestionDetailRequestError,
} from './questionSlice';

function* getQuestionRequestSaga(action: PayloadAction<number>) {
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

function* getQuestionDetailRequestSaga(action: PayloadAction<string>) {
  try {
    const result = yield call(getQuestionDetailRequestAsync, action.payload);
    console.log(result);

    yield put({
      type: getQuestionDetailRequestSuccess,
      payload: result,
    });
  } catch (error) {
    yield put({
      type: getQuestionDetailRequestError,
      payload: error,
    });
  }
}

function* watchNotice(): Generator {
  yield takeLatest(getQuestionRequest, getQuestionRequestSaga);
  yield takeLatest(getQuestionDetailRequest, getQuestionDetailRequestSaga);
}

export default function* loginSaga(): Generator {
  yield all([
    fork(watchNotice),
  ]);
}
