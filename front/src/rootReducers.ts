import { combineReducers } from '@reduxjs/toolkit';
import postSlice from './features/postList/postSlice';
import addPostSlice from './features/addPost/addPostSlice';
import detailViewSlice from './features/detailPostView/detailViewSlice';
import userSlice from './pages/signIn/userSlice';
import myPageSlice from './pages/myPage/myPageSlice';

export default combineReducers({
  postSlice,
  detailViewSlice,
  addPostSlice,
  userSlice,
  myPageSlice,
});
