import { combineReducers } from '@reduxjs/toolkit';
import postSlice from './features/postList/postSlice';
import addPostSlice from './features/addPost/addPostSlice';
import detailViewSlice from './features/detailPostView/detailViewSlice';
import userSlice from './features/login/userSlice';
import noticeSlice from './features/notice/noticeSlice';

export default combineReducers({
  postSlice, detailViewSlice, addPostSlice, userSlice, noticeSlice,
});
