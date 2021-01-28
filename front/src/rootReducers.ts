import { combineReducers } from '@reduxjs/toolkit';
import postSlice from './features/postList/postSlice';

export default combineReducers({
  postSlice,
});
