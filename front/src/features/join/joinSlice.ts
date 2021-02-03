import { createSlice } from '@reduxjs/toolkit';

interface testInterface {
  payload: {
    name: 'email' | 'certification' | 'nickname' | 'passwd' | 'passwdCheck',
    value: string
  };
  type: string;
}

const joinSlice = createSlice({
  name: 'join',
  initialState: {
    email: {
      value: '',
      status: 'none',
      message: '',
      color: 'red',
    },
    certification: {
      value: '',
      status: 'false',
      message: '',
    },
    nickname: {
      value: '',
      status: 'none',
      message: '',
      color: 'red',
    },
    passwd: {
      value: '',
      status: 'none',
      message: '',
      color: 'red',
    },
    passwdCheck: {
      value: '',
      status: 'none',
      message: '',
      color: 'red',
    },
    emailDuplication: false,
    emailCertification: false,
    nicknameDuplication: false,
    address: {
      zipCode: '',
      addressBase: '',
      addressDetail: '',
    },
    agreement: {
      all: false,
      agree1: false,
      agree2: false,
      agree3: false,
    },
    joinLoading: false,
    joinSuccess: false,
    joinError: null,
  },
  reducers: {
    onChangeText(state, action: testInterface) {
      state[action.payload.name].value = action.payload.value;
    },
    emailDuplicateCheck(state, action) {
      return { ...state };
    },
    emailDuplicationOk(state, action) {
      state.emailDuplication = true;
      state.email.message = '';
    },
    emailTypeCheckNone(state, action) {
      state.email.status = 'none';
      state.email.message = '';
    },
    emailTypeCheckOk(state, action) {
      state.email.status = 'check';
      state.email.message = '';
    },
    emailTypeCheckFail(state, action) {
      state.email.status = 'fail';
      state.email.message = '이메일 형식이 아닙니다.';
    },
    emailDuplicationFail(state, action) {
      state.emailDuplication = false;
      state.email.message = '이미 등록된 이메일입니다.';
    },
    emailCertificationRequest(state, action) {
      return { ...state };
    },
    emailCertificationCheck(state, action) {
      return { ...state };
    },
    emailCertificationOk(state, action) {
      state.email.message = '인증이 완료되었습니다.';
      state.email.color = 'blue';
      state.emailCertification = true;
    },
    emailCertificationFail(state, action) {
      state.email.message = '인증번호를 확인해주세요.';
      state.email.color = 'red';
      state.emailCertification = false;
    },
    nicknameDuplicateCheck(state, action) {
      return { ...state };
    },
    nicknameDuplicationOk(state, action) {
      state.nicknameDuplication = true;
      state.nickname.status = 'check';
      state.nickname.color = 'blue';
      state.nickname.message = action.payload;
    },
    nicknameDuplicationFail(state, action) {
      state.nicknameDuplication = false;
      state.nickname.status = 'fail';
      state.nickname.color = 'red';
      state.nickname.message = action.payload;
    },
    passwdCheckOk(state, action) {
      state.passwd.status = 'check';
      state.passwd.message = '';
    },
    passwdCheckFail(state, action) {
      state.passwd.status = 'fail';
      state.passwd.message = '8~20자의 영문 대소문자, 숫자, 특수문자 조합으로 설정해주세요.';
    },
    passwdSameOk(state, action) {
      state.passwdCheck.status = 'check';
      state.passwdCheck.message = '';
    },
    passwdSameFail(state, action) {
      state.passwdCheck.status = 'fail';
      state.passwdCheck.message = '비밀번호가 일치하지 않습니다.';
    },
    setAddress(state, action) {
      state.address.zipCode = action.payload.zipCode;
      state.address.addressBase = action.payload.address;
    },
    setAddressDetail(state, action) {
      state.address.addressDetail = action.payload;
    },
    setAllAgreeClick(state, action) {
      state.agreement.all = action.payload;
      state.agreement.agree1 = action.payload;
      state.agreement.agree2 = action.payload;
      state.agreement.agree3 = action.payload;
    },
    setAllAgree(state, action) {
      state.agreement.all = action.payload;
    },
    setAgree(state, action: {payload: {name: 'agree1' | 'agree2' | 'agree3', check: boolean}}) {
      state.agreement[action.payload.name] = action.payload.check;
    },
    joinRequest(state, action) {
      state.joinLoading = true;
      state.joinSuccess = false;
      state.joinError = null;
    },
    joinRequestOk(state, action) {
      state.joinLoading = false;
      state.joinSuccess = true;
      state.joinError = null;
    },
    joinRequestFail(state, action) {
      state.joinLoading = false;
      state.joinSuccess = false;
      state.joinError = action.payload;
    },
  },
});

export const {
  onChangeText,
  emailTypeCheckNone,
  emailTypeCheckOk,
  emailTypeCheckFail,
  emailDuplicateCheck,
  emailDuplicationOk,
  emailDuplicationFail,
  emailCertificationRequest,
  emailCertificationCheck,
  emailCertificationOk,
  emailCertificationFail,
  nicknameDuplicateCheck,
  nicknameDuplicationOk,
  nicknameDuplicationFail,
  passwdCheckOk,
  passwdCheckFail,
  passwdSameOk,
  passwdSameFail,
  setAddress,
  setAddressDetail,
  setAllAgreeClick,
  setAllAgree,
  setAgree,
  joinRequest,
  joinRequestOk,
  joinRequestFail,
} = joinSlice.actions;

export default joinSlice.reducer;
