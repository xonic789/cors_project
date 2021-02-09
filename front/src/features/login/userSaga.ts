import { all, call, fork, put, takeLatest } from 'redux-saga/effects';
import {
  logoutAsync,
  modifyProfileAsync,
  postLoginAsync,
  socialLoginAsync,
} from '../../api/userApi';
import {
  postLoginRequest,
  postSocialLoginRequest,
  postLoginRequestSuccess,
  postLoginRequestError,
  postLogoutRequest,
  postLogoutRequestSuccess,
  postLogoutRequestError,
  postModifyProfileRequest,
  postAddWishListRequest,
  postRemoveWishListRequest,
  postModifyProfileRequestError,
  postModifyProfileRequestSuccess,
} from './userSlice';
import { modifyProfileInterface } from '../../interfaces/UserInterface';

function* postLoginRequestSaga(action: { payload: { user: { email: string, passwd: string }, history: any } }) {
  try {
    const loginUser = yield call(postLoginAsync, action.payload.user);

    yield put({
      type: postLoginRequestSuccess,
      payload: loginUser,
    });

    action.payload.history.push('/home');
  } catch (error) {
    yield put({
      type: postLoginRequestError,
      payload: error,
    });
    alert('로그인 정보를 확인하세요.');
  }
}

function* postSocialLoginRequestSaga(action: { payload: { social: string } }) {
  try {
    yield call(socialLoginAsync, action.payload.social);

    yield put({
      type: postLoginRequestSuccess,
      payload: 'social',
    });
  } catch (error) {
    yield put({
      type: postLoginRequestError,
      payload: error,
    });
  }
}

function* postLogoutRequestSaga() {
  try {
    yield call(logoutAsync);

    yield put({
      type: postLogoutRequestSuccess,
    });
  } catch (error) {
    yield put({
      type: postLogoutRequestError,
      payload: error,
    });
  }
}

function* postModifyProfileRequestSaga(action: {payload: modifyProfileInterface}) {
  try {
    yield call(modifyProfileAsync, action.payload);

    yield put({
      type: postModifyProfileRequestSuccess,
    });
  } catch (error) {
    yield put({
      type: postModifyProfileRequestError,
      payload: error,
    });
  }
}

function* postAddWishListRequestSaga() {
  yield console.log('찜하기');
}

function* postRemoveWishListRequestSaga() {
  yield console.log('찜 해제하기');
}

function* watchLogin(): Generator {
  yield takeLatest(postLoginRequest, postLoginRequestSaga);
  yield takeLatest(postSocialLoginRequest, postSocialLoginRequestSaga);
  yield takeLatest(postLogoutRequest, postLogoutRequestSaga);
}

function* watchProfile(): Generator {
  yield takeLatest(postModifyProfileRequest, postModifyProfileRequestSaga);
}

function* watchWishList(): Generator {
  yield takeLatest(postAddWishListRequest, postAddWishListRequestSaga);
  yield takeLatest(postRemoveWishListRequest, postRemoveWishListRequestSaga);
}

export default function* loginSaga(): Generator {
  yield all([
    fork(watchLogin),
    fork(watchProfile),
    fork(watchWishList),
  ]);
}
