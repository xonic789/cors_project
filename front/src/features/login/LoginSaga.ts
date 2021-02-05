import cookie from 'react-cookies';
import { all, call, fork, ForkEffect, put, takeLatest } from 'redux-saga/effects';
import { postLogin, postSocialLogin, postLoginSuccess, postLoginError, postLogoutError, postLogoutSuccess, postLogout } from './LoginSlice';
import { logoutAsync, postLoginAsync, socialLoginAsync } from '../../api/loginAPI';

function* postLoginSaga(action: { payload: { email: string, passwd: string } }) {
  try {
    yield call(postLoginAsync, action.payload);

    yield put({
      type: postLoginSuccess,
      payload: action.payload.email,
    });
  } catch (e) {
    yield put({
      type: postLoginError,
      payload: e,
    });
    alert('로그인 정보를 확인하세요.');
  }
}

function* postSocialLoginSaga(action: { payload: { social: string } }) {
  try {
    yield call(socialLoginAsync, action.payload.social);

    yield put({
      type: postLoginSuccess,
      payload: 'social',
    });
  } catch (e) {
    yield put({
      type: postLoginError,
      payload: e,
    });
  }
}

function* postLogoutSaga() {
  try {
    yield call(logoutAsync);

    yield put({
      type: postLogoutSuccess,
    });
  } catch (e) {
    yield put({
      type: postLogoutError,
      payload: e,
    });
  }
}

function* watchLogin(): Generator<ForkEffect<never>, void, unknown> {
  yield takeLatest(postLogin, postLoginSaga);
  yield takeLatest(postSocialLogin, postSocialLoginSaga);
  yield takeLatest(postLogout, postLogoutSaga);
}

export default function* loginSaga() {
  yield all([fork(watchLogin)]);
}
