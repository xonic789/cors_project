import { createSlice } from '@reduxjs/toolkit';
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

    isAddBookPostLoading: false,
    isAddBookPostDone: false,
    isAddBookPostError: null,

    isDeleteBookPostLoading: false,
    isDeleteBookPostDone: false,
    isDeleteBookPostError: null,
  },
  reducers: {
    loadBookPostRequest(state, action) {
      state.isLoadBookPostLoading = true;
      state.isLoadScrollBookPostLoading = false;
      state.hasMorePost = true;
      state.filtering.division = action.payload.filtering.division;
      state.filtering.category = action.payload.filtering.category;
    },
    loadBookPostSuccess(state, action) {
      state.isLoadBookPostLoading = false;
      state.isLoadBookPostDone = true;
      state.bookPost = action.payload.data;
    },
    loadBookPostError(state, action) {
      state.isLoadBookPostLoading = false;
      state.isLoadBookPostError = action.payload;
    },
    loadScrollBookPostRequest(state, action) {
      state.isLoadScrollBookPostLoading = true;
    },
    loadScrollBookPostSuccess(state, action) {
      state.isLoadScrollBookPostLoading = false;
      state.isLoadScrollPostDone = true;
      state.bookPost = state.bookPost.concat(action.payload.data);
      state.hasMorePost = action.payload.data.length === 10;
    },
    loadScrollBookPostError(state, action) {
      state.isLoadScrollBookPostLoading = false;
      state.isLoadScrollPostError = action.payload;
    },
    addBookPostRequest(state, action) {
      state.isAddBookPostLoading = true;
      state.isAddBookPostDone = false;
    },
    addBookPostSuccess(state, action) {
      state.isAddBookPostLoading = false;
      state.isAddBookPostDone = true;
      state.bookPost = action.payload.data.concat(state.bookPost);
    },
    addBookPostError(state, action) {
      state.isAddBookPostLoading = false;
      state.isAddBookPostDone = false;
      state.isAddBookPostError = action.payload.error;
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
  addBookPostRequest,
  addBookPostSuccess,
  addBookPostError,
  deleteBookPostRequest,
  deleteBookPostSuccess,
  deleteBookPostError,
} = postSlice.actions;

export default postSlice.reducer;
