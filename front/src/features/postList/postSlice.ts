import { createSelector, createSlice, PayloadAction } from '@reduxjs/toolkit';
import PostList, { dummyBookPost } from './mockdata';
import { articleInterface } from '../../interfaces/PostList.interface';

const postSlice = createSlice({
  name: 'posts',
  initialState: {
    bookPost: [],
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
    loadBookPostSuccess(state, action) {
      state.isLoadBookPostLoading = false;
      state.isLoadBookPostDone = true;
      state.bookPost = action.payload.data;
    },
    loadBookPostError(state, action) {
      state.isLoadBookPostLoading = false;
      state.isLoadBookPostError = action.payload.error;
    },
    addBookPostRequest(state, action) {
      state.isAddBookPostLoading = true;
      state.isAddBookPostDone = false;
    },
    addBookPostSuccess(state, action) {
      state.isAddBookPostLoading = false;
      state.isAddBookPostDone = true;
      // state.bookPost.unshift(dummyBookPost(action.payload));
    },
    addBookPostError(state, action) {
      state.isAddBookPostLoading = false;
      state.isAddBookPostDone = false;
      state.isAddBookPostError = action.payload.error;
    },
  },
});

export const {
  loadBookPostRequest,
  loadBookPostSuccess,
  loadBookPostError,
  addBookPostRequest,
  addBookPostError,
  addBookPostSuccess,
} = postSlice.actions;

export default postSlice.reducer;
