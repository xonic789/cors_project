import { createSelector, createSlice, PayloadAction } from '@reduxjs/toolkit';

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
    isLoadScrollPostError: false,
    isAddBookPostLoading: false,
    isAddBookPostDone: false,
    isAddBookPostError: null,
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
      state.filtering.division = action.payload.division;
      state.filtering.category = action.payload.category;
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
  loadScrollBookPostRequest,
  loadScrollBookPostSuccess,
  loadScrollBookPostError,
  addBookPostRequest,
  addBookPostError,
  addBookPostSuccess,
} = postSlice.actions;

export default postSlice.reducer;
