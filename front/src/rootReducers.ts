import { combineReducers } from '@reduxjs/toolkit';
import postSlice from './features/postList/postSlice';
import LoginSlice from './features/login/LoginSlice';
import joinSlice from './features/join/joinSlice';

export default combineReducers({
  postSlice, LoginSlice, joinSlice,
});
