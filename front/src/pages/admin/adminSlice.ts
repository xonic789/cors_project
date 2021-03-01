import { createSlice } from '@reduxjs/toolkit';

interface noticeInterface {
  noticeId: string,
  title: string,
  content: string,
  writeDate: string,
}
interface marketRequestInterface {
  marketId: string,
  marketName: string,
  marketStatus: string,
}
interface reportInterface {
  reportId: string,
  title: string,
  content: string,
}

const noticeList: noticeInterface[] = [];
const marketRequestList: marketRequestInterface[] = [];
const reportList: reportInterface[] = [];

const adminSlice = createSlice({
  name: 'admin',
  initialState: {
    noticeList,
    marketRequestList,
    reportList,
    isAdminLoginLoading: false,
    isAdminLoginSuccess: false,
    isAdminLoginError: null,
    isGetNoticeLoading: false,
    isGetNoticeSuccess: false,
    isGetNoticeError: null,
  },
  reducers: {
    adminLoginRequest: (state, action) => {
      state.isAdminLoginLoading = true;
      state.isAdminLoginSuccess = false;
      state.isAdminLoginError = null;
    },
    adminLoginRequestSuccess: (state, action) => {
      state.isAdminLoginLoading = false;
      state.isAdminLoginSuccess = true;
      state.isAdminLoginError = null;
    },
    adminLoginRequestError: (state, action) => {
      state.isAdminLoginLoading = false;
      state.isAdminLoginSuccess = false;
      state.isAdminLoginError = action.payload;
    },
    getNoticeRequest: (state, action) => {
      state.isGetNoticeLoading = true;
      state.isGetNoticeSuccess = false;
      state.isGetNoticeError = null;
    },
    getNoticeRequestSuccess: (state, action) => {
      state.isGetNoticeLoading = false;
      state.isGetNoticeSuccess = true;
      state.isGetNoticeError = null;
    },
    getNoticeRequestError: (state, action) => {
      state.isGetNoticeLoading = false;
      state.isGetNoticeSuccess = false;
      state.isGetNoticeError = action.payload;
    },
  },
});

export const {
  adminLoginRequest,
  adminLoginRequestSuccess,
  adminLoginRequestError,
} = adminSlice.actions;

export default adminSlice.reducer;
