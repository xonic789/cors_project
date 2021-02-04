import { createSlice } from '@reduxjs/toolkit';

interface onChangeInterface {
  payload: {
    name: 'email' | 'certification' | 'nickname' | 'passwd' | 'passwdCheck',
    value: string,
  };
}

interface inputCheckInterface {
  payload: {
    name: 'email' | 'certification' | 'nickname' | 'passwd' | 'passwdCheck',
    message: string,
  };
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
    onChangeText(state, action: onChangeInterface) {
      state[action.payload.name].value = action.payload.value;
    },
    inputCheckOk(state, action: inputCheckInterface) {
      state[action.payload.name].status = 'check';
      state[action.payload.name].message = action.payload.message;
    },
    inputCheckFail(state, action: inputCheckInterface) {
      state[action.payload.name].status = 'fail';
      state[action.payload.name].message = action.payload.message;
    },
    inputCheckNone(state, action: inputCheckInterface) {
      state[action.payload.name].status = 'none';
      state[action.payload.name].message = action.payload.message;
    },
    emailDuplicateCheck(state, action) {
      return { ...state };
    },
    emailDuplicationOk(state, action) {
      state.emailDuplication = true;
      state.email.message = '';
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
  inputCheckOk,
  inputCheckFail,
  inputCheckNone,
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
