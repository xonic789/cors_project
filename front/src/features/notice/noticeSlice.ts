import { createSlice } from '@reduxjs/toolkit';
import { noticeInterface } from '../../interfaces/NoticeInterface';

const initialNoticeList: noticeInterface[] = [];

const userSlice = createSlice({
  name: 'user',
  initialState: {
    noticeList: initialNoticeList,
    isGetNoticeLoading: false, // 로그인
    isGetNoticeSucceed: false,
    isGetNoticeError: null,
  },
  reducers: {
    getNoticeRequest: (state, action) => {
      state.isGetNoticeLoading = true;
    },
    getNoticeRequestSuccess: (state, action) => {
      state.isGetNoticeLoading = false;
      state.isGetNoticeSucceed = true;
      state.noticeList = action.payload;
    },
    getNoticeRequestError: (state, action) => {
      state.isGetNoticeLoading = false;
      state.isGetNoticeError = action.payload;
    },
    toggleActiveNotice: (state, action) => {
      console.log(action.payload);
      return {
        ...state,
        noticeList: state.noticeList.map((item) => (item.noticeId === action.payload ? { ...item, active: !item.active } : item)),
      };
      console.log();
    },
  },
});

export const {
  getNoticeRequest,
  getNoticeRequestSuccess,
  getNoticeRequestError,
  toggleActiveNotice,
} = userSlice.actions;

export default userSlice.reducer;
