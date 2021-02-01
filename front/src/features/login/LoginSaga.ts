import cookie from 'react-cookies';
<<<<<<< HEAD
import { all, call, ForkEffect, put, takeLatest } from 'redux-saga/effects';
import { postLogin, postLoginSuccess, postLoginError } from './LoginSlice';
import { loginPostApi } from '../../api/loginApi';

function* postLoginSaga(action: any) {
  const token = yield call(loginPostApi);

  if (token.data.accessToken === '' || token.data.refreshToken === '') {
    console.log('로그인 실패');
  } else {
    console.log('로그인 성공');
    const expires = new Date();
    expires.setDate(Date.now() + 1000 * 60 * 60 * 24 * 14);

    cookie.save(
      'access_token',
      token.data.accessToken,
      {
        path: '/',
        expires,
      },
    );
    cookie.save(
      'refresh_token',
      token.data.refreshToken,
      {
        path: '/',
        expires,
      },
    );

    console.log(token);
    console.log(action.payload);
  }
}

function* loginSaga(): Generator<ForkEffect<never>, void, unknown> {
  yield takeLatest(postLogin, postLoginSaga);
=======
import { all, call, fork, ForkEffect, put, takeLatest } from 'redux-saga/effects';
import { postLogin, postSocialLogin, postLoginSuccess, postLoginError } from './LoginSlice';
import { postLoginAsync, socialLoginAsync } from '../../api/loginAPI';

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
>>>>>>> e81864fa6fa2a2b3e0e5463822257c93a9ed12f9
}

export default function* loginSaga() {
  yield all([fork(watchLogin)]);
}
