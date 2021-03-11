import { all, call, fork, put, takeLatest } from 'redux-saga/effects';
import {
  logoutAsync,
  modifyProfileAsync,
  postLoginAsync,
  socialLoginAsync,
  getUserInfoAsync,
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
  postAddWishListRequestSuccess,
  postRemoveWishListRequestSuccess,
  postAddWishListRequestError,
  postRemoveWishListRequestError,
  getUserInfoRequest,
  getUserInfoRequestSuccess,
  getUserInfoRequestError,
} from './userSlice';
import { modifyProfileInterface } from '../../interfaces/UserInterface';
import { addWishs, removeWishs } from '../../api/wishsApi';
import { push } from '../../utils/historyUtil';

function* postLoginRequestSaga(action: { payload: { user: { email: string, passwd: string } } }) {
  try {
    const loginUser = yield call(postLoginAsync, action.payload.user);
    console.log(loginUser, 'asdaksldlsajdklasjdlkajkdl');
    if (loginUser.nickname) {
      yield put({
        type: postLoginRequestSuccess,
        payload: {
          ...loginUser,
          email: action.payload.user.email,
        },
      });
    }
  } catch (error) {
    yield put({
      type: postLoginRequestError,
      payload: error.message,
    });
  }
}

function* postSocialLoginRequestSaga(action: { payload: { social: string } }) {
  try {
    const result = yield call(socialLoginAsync, action.payload.social);

    if (result) {
      yield put({
        type: postLoginRequestSuccess,
        payload: 'social',
      });
    } else {
      yield put({
        type: postLoginRequestError,
        payload: '소셜 로그인 실패',
      });
    }
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

function* postModifyProfileRequestSaga(action: {payload: {modifyProfile: FormData, history: any, ToastsStore: any}}) {
  try {
    console.log(action.payload.modifyProfile);
    const result = yield call(modifyProfileAsync, action.payload.modifyProfile);

    if (result.state) {
      yield put({
        type: postModifyProfileRequestSuccess,
        payload: { nickname: result.nickname, profileImg: result.profileImg },
      });
      action.payload.history.push('/mypage');
      action.payload.ToastsStore.success('프로필 변경이 완료되었습니다.');
    } else {
      yield put({
        type: postModifyProfileRequestError,
        payload: '비밀번호 불일치',
      });
    }
  } catch (error) {
    yield put({
      type: postModifyProfileRequestError,
      payload: error,
    });
  }
}

function* postAddWishListRequestSaga(action: {payload: string }) {
  try {
    const result = yield addWishs(action.payload);
    if (result) {
      yield put({
        type: postAddWishListRequestSuccess,
        payload: action.payload,
      });
    } else {
      yield put({
        type: postAddWishListRequestError,
        paylose: '서버 통신 에러',
      });
    }
  } catch (error) {
    console.log(error);
  }
}

function* postRemoveWishListRequestSaga(action: {payload: string}) {
  try {
    const result = yield removeWishs(action.payload);
    if (result) {
      yield put({
        type: postRemoveWishListRequestSuccess,
        payload: action.payload,
      });
    } else {
      yield put({
        type: postRemoveWishListRequestError,
        paylose: '서버 통신 에러',
      });
    }
  } catch (error) {
    console.log(error);
  }
}

function* getUserInfo() {
  try {
    const result = yield getUserInfoAsync();
    console.log(result, '유저정보');
    yield put({
      type: getUserInfoRequestSuccess,
      payload: result,
    });
  } catch (error) {
    alert('서버통신에러');
    yield put({
      type: getUserInfoRequestError,
      payload: error,
    });
  }
}

function* watchLogin(): Generator {
  yield takeLatest(postLoginRequest, postLoginRequestSaga);
  yield takeLatest(postSocialLoginRequest, postSocialLoginRequestSaga);
  yield takeLatest(postLogoutRequest, postLogoutRequestSaga);
  yield takeLatest(getUserInfoRequest, getUserInfo);
}

function* watchProfile(): Generator {
  yield takeLatest(postModifyProfileRequest, postModifyProfileRequestSaga);
}

function* watchWishList(): Generator {
  yield takeLatest(postAddWishListRequest, postAddWishListRequestSaga);
  yield takeLatest(postRemoveWishListRequest, postRemoveWishListRequestSaga);
}

export default function* userSaga(): Generator {
  yield all([
    fork(watchLogin),
    fork(watchProfile),
    fork(watchWishList),
  ]);
}
