import { createSlice } from '@reduxjs/toolkit';

const postSlice = createSlice({
  name: 'posts',
  initialState: {
    value: 0,
  },
  reducers: {
    addBookPost: () => {},
  },
});
export const { addBookPost } = postSlice.actions;
export default postSlice.reducer;
