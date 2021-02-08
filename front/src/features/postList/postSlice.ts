import { createSelector, createSlice, PayloadAction } from '@reduxjs/toolkit';
import { articleInterface } from '../../interfaces/PostList.interface';

const postSlice = createSlice({
  name: 'posts',
  initialState: {
    bookPost: [],
    hasMorePost: true,
    filtering: { division: 'sales', category: '' },

    isLoadBookPostLoading: false,
    isLoadBookPostDone: false,
    isLoadBookPostError: false,

    isLoadScrollBookPostLoading: false,
    isLoadScrollPostDone: false,
    isLoadScrollPostError: null,

    isDeleteBookPostLoading: false,
    isDeleteBookPostDone: false,
    isDeleteBookPostError: null,
  },
  reducers: {
    loadBookPostRequest(state, action) {
      state.isLoadBookPostLoading = true;
      state.isLoadScrollBookPostLoading = false;
      state.hasMorePost = true;
      state.filtering.division = action.payload.division;
      state.filtering.category = action.payload.category;
    },
    loadBookPostSuccess(state, action) {
      state.isLoadBookPostLoading = false;
      state.isLoadBookPostDone = true;
      state.bookPost = action.payload;
    },
    loadBookPostError(state, action) {
      state.isLoadBookPostLoading = false;
      state.isLoadBookPostError = action.payload.error;
    },
    loadScrollBookPostRequest(state, action) {
      state.isLoadScrollBookPostLoading = true;
    },
    loadScrollBookPostSuccess(state, action) {
      state.isLoadScrollBookPostLoading = false;
      state.isLoadScrollPostDone = true;
      state.bookPost = action.payload.concat(state.bookPost);
      state.hasMorePost = state.bookPost.length < 150;
    },
    loadScrollBookPostError(state, action) {
      state.isLoadScrollBookPostLoading = false;
      state.isLoadScrollPostError = action.payload.error;
    },
    deleteBookPostRequest(state, action) {
      state.isDeleteBookPostLoading = true;
    },
    deleteBookPostSuccess(state, action) {
      state.isDeleteBookPostLoading = false;
      state.isDeleteBookPostDone = true;
      state.bookPost.filter((post:articleInterface) => post.articleId !== action.payload);
    },
    deleteBookPostError(state, action) {
      state.isDeleteBookPostLoading = false;
      state.isDeleteBookPostError = action.payload.error;
    },
  },
});

export const {
  loadBookPostRequest,
  loadBookPostSuccess,
  loadBookPostError,
  loadScrollBookPostRequest,
  loadScrollBookPostSuccess,
  loadScrollBookPostError,
  deleteBookPostRequest,
  deleteBookPostSuccess,
  deleteBookPostError,
} = postSlice.actions;

export default postSlice.reducer;
