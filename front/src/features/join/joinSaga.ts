import { all, call, fork, ForkEffect, put, takeLatest, delay } from 'redux-saga/effects';
import { emailCertificationAsync, emailDuplicationAsync, nicknameDuplicationAsync, joinRequestAsync } from '../../api/joinApi';
import {
  emailDuplicationOk,
  emailDuplicationFail,
  emailCertificationRequest,
  emailCertificationCheck,
  emailCertificationOk,
  emailCertificationFail,
  nicknameDuplicateCheck,
  nicknameDuplicationOk,
  nicknameDuplicationFail,
  joinRequest,
  joinRequestOk,
  joinRequestFail,
} from './joinSlice';

function* nicknameDuplicateSaga(action: { payload: { nickname: string } }) {
  try {
    yield delay(500);
    const isDuplicate = yield call(nicknameDuplicationAsync, action.payload.nickname);

    if (action.payload.nickname.length < 4) {
      yield put({
        type: nicknameDuplicationFail,
        payload: '닉네임을 4자 이상 입력해주세요.',
      });
    } else if (!isDuplicate) {
      yield put({
        type: nicknameDuplicationOk,
        payload: '사용가능한 닉네임입니다..',
      });
    }
  } catch (e) {
    yield put({
      type: nicknameDuplicationFail,
      payload: '이미 사용중인 닉네임입니다.',
    });
  }
}

function* emailCertificationRequestSaga(action: { payload: string }) {
  try {
    const isDuplicate = yield call(emailDuplicationAsync, action.payload);

    if (!isDuplicate) {
      yield put({
        type: emailDuplicationOk,
      });
    } else {
      yield put({
        type: emailDuplicationFail,
      });
    }
  } catch {
    yield put({
      type: emailDuplicationFail,
    });
  }
}

function* emailCertificationSaga(action: { payload: { email: string, code: string }}) {
  try {
    const isCertification = yield call(emailCertificationAsync, action.payload.email, action.payload.code);

    if (isCertification) {
      yield put({
        type: emailCertificationOk,
      });
    } else {
      yield put({
        type: emailCertificationFail,
      });
    }
  } catch {
    yield put({
      type: emailCertificationFail,
    });
  }
}

function* joinRequestSaga(action: { payload: {email: string, passwd: string, nickname: string, address: string}}) {
  try {
    const joinResult = yield call(joinRequestAsync, action.payload.email, action.payload.nickname, action.payload.passwd, action.payload.address);

    if (joinResult) {
      yield put({
        type: joinRequestOk,
      });
      alert('회원가입이 완료 되었습니다.');
    } else {
      alert('회원가입중 오류 발생.');
      yield put({
        type: joinRequestFail,
        payload: '회원가입 오류',
      });
    }
  } catch (e) {
    alert('회원가입중 오류 발생.');
    yield put({
      type: joinRequestFail,
      payload: e,
    });
  }
}

function* watchJoin(): Generator<ForkEffect<never>, void, unknown> {
  yield takeLatest(nicknameDuplicateCheck, nicknameDuplicateSaga);
  yield takeLatest(emailCertificationRequest, emailCertificationRequestSaga);
  yield takeLatest(emailCertificationCheck, emailCertificationSaga);
  yield takeLatest(joinRequest, joinRequestSaga);
}

export default function* joinSaga() {
  yield all([fork(watchJoin)]);
}
