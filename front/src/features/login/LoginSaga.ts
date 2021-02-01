import cookie from 'react-cookies';
import { all, call, fork, ForkEffect, put, takeLatest } from 'redux-saga/effects';
import { postLogin, postSocialLogin, postLoginSuccess, postLoginError } from './LoginSlice';
import { postLoginAsync, socialLoginAsync } from '../../api/loginApi';

function* postLoginSaga(action: { payload: { email: string, passwd: string } }) {
  try {
    const token = yield call(postLoginAsync, action.payload);

    if (token.accessToken === '' || token.refreshToken === '') {
      console.log('로그인 실패');
      throw new Error('유저 정보 불일치');
    } else {
      console.log('로그인 성공');
      const accessExpires = new Date();
      accessExpires.setTime(accessExpires.getTime() + 1000 * 60 * 60 * 2);
      const refreshExpires = new Date();
      refreshExpires.setTime(refreshExpires.getTime() + 1000 * 60 * 60 * 24);

      cookie.save(
        'access_token',
        token.accessToken,
        {
          path: '/',
          expires: accessExpires,
        },
      );
      cookie.save(
        'refresh_token',
        token.refreshToken,
        {
          path: '/',
          expires: refreshExpires,
        },
      );

      yield put({
        type: postLoginSuccess,
        payload: action.payload,
      });
    }
  } catch (e) {
    yield put({
      type: postLoginError,
      payload: e,
    });
  }
}

function* postSocialLoginSaga(action: { payload: { social: string } }) {
  try {
    const token = yield call(socialLoginAsync, action.payload.social);

    if (token.accessToken === '' || token.refreshToken === '') {
      console.log('로그인 실패');
      throw new Error('유저 정보 불일치');
    } else {
      console.log('로그인 성공');
      const accessExpires = new Date();
      accessExpires.setTime(accessExpires.getTime() + 1000 * 60 * 60 * 2);
      const refreshExpires = new Date();
      refreshExpires.setTime(refreshExpires.getTime() + 1000 * 60 * 60 * 24);

      cookie.save(
        'access_token',
        token.accessToken,
        {
          path: '/',
          expires: accessExpires,
        },
      );
      cookie.save(
        'refresh_token',
        token.refreshToken,
        {
          path: '/',
          expires: refreshExpires,
        },
      );

      yield put({
        type: postLoginSuccess,
        payload: action.payload,
      });
    }
  } catch (e) {
    yield put({
      type: postLoginError,
      payload: e,
    });
  }
}

function* watchLogin(): Generator<ForkEffect<never>, void, unknown> {
  yield takeLatest(postLogin, postLoginSaga);
  yield takeLatest(postSocialLogin, postSocialLoginSaga);
}

export default function* loginSaga() {
  yield all([fork(watchLogin)]);
}
