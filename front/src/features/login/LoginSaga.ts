import cookie from 'react-cookies';
import { all, call, fork, ForkEffect, put, takeLatest } from 'redux-saga/effects';
import { postLogin, postLoginSuccess, postLoginError } from './LoginSlice';
import postLoginAsync from '../../api/loginApi';

function* postLoginSaga(action:any) {
  try {
    const token = yield call(postLoginAsync, action.payload);
    console.log(action.payload);

    if (token.data.accessToken === '' || token.data.refreshToken === '') {
      console.log('로그인 실패');
    } else {
      console.log('로그인 성공');
      const accessExpires = new Date();
      accessExpires.setTime(accessExpires.getTime() + 1000 * 60 * 60 * 2);
      const refreshExpires = new Date();
      refreshExpires.setTime(refreshExpires.getTime() + 1000 * 60 * 60 * 24);

      cookie.save(
        'access_token',
        token.data.accessToken,
        {
          path: '/',
          expires: accessExpires,
        },
      );
      cookie.save(
        'refresh_token',
        token.data.refreshToken,
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
}

export default function* loginSaga() {
  yield all([fork(watchLogin)]);
}
