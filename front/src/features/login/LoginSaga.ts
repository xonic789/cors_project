import cookie from 'react-cookies';
import { all, call, ForkEffect, put, takeEvery } from 'redux-saga/effects';
import { postLogin, postLoginSuccess, postLoginError } from './LoginSlice';
import postLoginAsync from '../../api/loginAPI';

function* postLoginSaga(action:any) {
  const token = yield call(postLoginAsync);

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
  yield takeEvery(postLogin, postLoginSaga);
}

export default function* rootSaga() {
  yield all([loginSaga()]);
}
