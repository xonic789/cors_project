import { createSlice } from '@reduxjs/toolkit';

const detailViewSlice = createSlice({
  name: 'detailView',
  initialState: {
    detailBookPost: {},
    isLoadDetailBookPostLoading: false,
    isLoadDetailBookPostDone: false,
    isLoadDetailBookPostError: null,
  },
  reducers: {
    loadDetailBookPostRequest(state, action) {
      state.isLoadDetailBookPostLoading = true;
    },
    loadDetailBookPostSuccess(state, action) {
      state.isLoadDetailBookPostLoading = false;
      state.isLoadDetailBookPostDone = true;
      state.detailBookPost = action.payload;
    },
    loadDetailBookPostError(state, action) {
      state.isLoadDetailBookPostLoading = false;
      state.isLoadDetailBookPostError = action.payload.error;
    },
  },
});

export const {
  loadDetailBookPostRequest,
  loadDetailBookPostSuccess,
  loadDetailBookPostError,
} = detailViewSlice.actions;

export default detailViewSlice.reducer;
