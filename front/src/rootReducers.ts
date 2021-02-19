import { combineReducers } from '@reduxjs/toolkit';
import postSlice from './features/postList/postSlice';
import addPostSlice from './features/addPost/addPostSlice';
import detailViewSlice from './features/detailPostView/detailViewSlice';
import myPageSlice from './pages/myPage/myPageSlice';
import userSlice from './pages/signIn/userSlice';

export default combineReducers({
  postSlice,
  detailViewSlice,
  addPostSlice,
  userSlice,
  myPageSlice,
});
