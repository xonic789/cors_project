import { createSlice } from '@reduxjs/toolkit';

const loginSlice = createSlice({
  name: 'login',
  initialState: {
    user: null,
    loginLoading: false,
    loginSucceed: false,
    loginError: null,
    logoutLoading: false,
    logoutSucceed: false,
    logoutError: null,
  },
  reducers: {
    postLogin: (state, action) => {
      state.loginLoading = true;
      state.loginSucceed = false;
      state.loginError = null;
    },
    postSocialLogin: (state, action) => {
      state.loginLoading = true;
      state.loginSucceed = false;
      state.loginError = null;
    },
    postLoginSuccess: (state, action) => {
      state.loginLoading = false;
      state.loginSucceed = true;
      state.loginError = null;
    },
    postLoginError: (state, action) => {
      state.loginLoading = false;
      state.loginSucceed = false;
      state.loginError = action.payload;
    },
    postLogout: (state, action) => {
      state.logoutLoading = true;
      state.logoutSucceed = false;
      state.logoutError = null;
    },
    postLogoutSuccess: (state, action) => {
      state.logoutLoading = false;
      state.logoutSucceed = true;
      state.logoutError = null;
    },
    postLogoutError: (state, action) => {
      state.logoutLoading = false;
      state.logoutSucceed = false;
      state.logoutError = action.payload;
    },
  },
});
export const {
  postLogin,
  postSocialLogin,
  postLoginSuccess,
  postLoginError,
  postLogout,
  postLogoutSuccess,
  postLogoutError } = loginSlice.actions;
export default loginSlice.reducer;
