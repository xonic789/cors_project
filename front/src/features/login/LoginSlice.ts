import { createSlice } from '@reduxjs/toolkit';

const loginSlice = createSlice({
  name: 'login',
  initialState: {
    logined: false,
    user: {},
  },
  reducers: {
    postLogin: (state, action) => state,
    postLoginSuccess: (state, action) => {
      state.logined = true;
    },
    postLoginError: (state, action) => {
      state.logined = false;
    },
  },
});
export const { postLogin, postLoginSuccess, postLoginError } = loginSlice.actions;
export default loginSlice.reducer;
