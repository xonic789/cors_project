import { combineReducers } from '@reduxjs/toolkit';
import postSlice from './features/postList/postSlice';
import LoginSlice from './features/login/LoginSlice';

export default combineReducers({
  postSlice, LoginSlice,
});
