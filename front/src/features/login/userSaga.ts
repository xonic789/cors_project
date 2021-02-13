import { all, call, fork, put, takeLatest, getContext } from 'redux-saga/effects';
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
  getWishListRequest,
  postAddWishListRequest,
  postRemoveWishListRequest,
  getMySaleArticleRequest,
  getMyPurchaseArticleRequest,
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

function* postModifyProfileRequestSaga(action: {payload: {modifyProfile: modifyProfileInterface, modifyInputs: any, setModifInputs: any}}) {
  try {
    const result = yield call(modifyProfileAsync, action.payload.modifyProfile);
    console.log(result);
    if (result) {
      yield put({
        type: postModifyProfileRequestSuccess,
        payload: action.payload.modifyProfile.nickname,
      });
    } else {
      yield put({
        type: postModifyProfileRequestError,
        payload: '비밀번호 불일치',
      });
      action.payload.setModifInputs({
        ...action.payload.modifyInputs,
        passwd: {
          ...action.payload.modifyInputs.passwd,
          message: '비밀번호가 일치하지 않습니다.',
          state: 'fail',
          color: 'red',
        },
      });
    }
  } catch (error) {
    yield put({
      type: postModifyProfileRequestError,
      payload: error,
    });
    alert('서버 통신 에러');
  }
}

function* getWishListRequestSaga() {
  yield console.log('찜목록 불러오기');
}

function* postAddWishListRequestSaga() {
  yield console.log('찜하기');
}

function* postRemoveWishListRequestSaga() {
  yield console.log('찜 해제하기');
}

function* getMySaleArticleRequestSaga() {
  yield console.log('내 판매글 불러오기');
}

function* getMyPurchaseArticleRequestSaga() {
  yield console.log('내 구매글 불러오기');
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
  yield takeLatest(getWishListRequest, getWishListRequestSaga);
  yield takeLatest(postAddWishListRequest, postAddWishListRequestSaga);
  yield takeLatest(postRemoveWishListRequest, postRemoveWishListRequestSaga);
}

function* watchMyArticles(): Generator {
  yield takeLatest(getMySaleArticleRequest, getMySaleArticleRequestSaga);
  yield takeLatest(getMyPurchaseArticleRequest, getMyPurchaseArticleRequestSaga);
}

export default function* loginSaga(): Generator {
  yield all([
    fork(watchLogin),
    fork(watchProfile),
    fork(watchWishList),
    fork(watchMyArticles),
  ]);
}
