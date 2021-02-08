import { combineReducers } from '@reduxjs/toolkit';
import postSlice from './features/postList/postSlice';
import detailViewSlice from './features/detailPostView/detailViewSlice';
import LoginSlice from './features/login/LoginSlice';

export default combineReducers({
  postSlice, detailViewSlice, LoginSlice,
});
