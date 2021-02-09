import { createSlice } from '@reduxjs/toolkit';

const addPostSlice = createSlice({
  name: 'addPost',
  initialState: {
    isAddBookPostLoading: false,
    isAddBookPostDone: false,
    isAddBookPostError: null,
  },
  reducers: {
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
  addBookPostRequest,
  addBookPostSuccess,
  addBookPostError,
} = addPostSlice.actions;

export default addPostSlice.reducer;
