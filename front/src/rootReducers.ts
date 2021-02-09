import { combineReducers } from '@reduxjs/toolkit';
import postSlice from './features/postList/postSlice';
import userSlice from './features/login/userSlice';

export default combineReducers({
  postSlice, userSlice,
});
