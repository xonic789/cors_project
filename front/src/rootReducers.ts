import { combineReducers } from '@reduxjs/toolkit';
import postSlice from './features/postList/postListSlice';

export default combineReducers({
  postSlice,
});
