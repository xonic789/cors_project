import { createSelector, createSlice, PayloadAction } from '@reduxjs/toolkit';
import PostList, { dummyBookPost } from './mockdata';
import { articleInterface } from '../../interfaces/PostList.interface';

const postSlice = createSlice({
  name: 'posts',
  initialState: {
    bookPost: PostList.data,
    isLoadBookPostLoading: false,
    isLoadBookPostDone: false,
    isLoadBookPostError: false,
    isAddBookPostLoading: false,
    isAddBookPostDone: false,
    isAddBookPostError: null,
  },
  reducers: {
    loadBookPostRequest(state, action) {
      state.isLoadBookPostLoading = true;
    },
    addBookPostRequest(state, action) {
      state.isAddBookPostLoading = true;
      state.isAddBookPostDone = false;
    },
    addBookPostError(state, action) {
      state.isAddBookPostLoading = false;
      state.isAddBookPostDone = false;
      state.isAddBookPostError = action.payload.error;
    },
    addBookPostSuccess(state, action) {
      state.isAddBookPostLoading = false;
      state.isAddBookPostDone = true;
      state.bookPost.unshift(dummyBookPost(action.payload));
    },
  },
});

export const {
  loadBookPostRequest,
  addBookPostRequest,
  addBookPostError,
  addBookPostSuccess,
} = postSlice.actions;

export default postSlice.reducer;
