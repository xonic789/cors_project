import { createSlice } from '@reduxjs/toolkit';

const loginSlice = createSlice({
  name: 'login',
  initialState: {
    user: null,
    loginLoading: false,
    loginSucceed: false,
    loginError: null,
  },
  reducers: {
    postLogin: (state, action) => ({
      ...state,
      loginLoading: true,
      loginSucceed: false,
      loginError: null,
    }),
    postSocialLogin: (state, action) => ({
      ...state,
      loginLoading: true,
      loginSucceed: false,
      loginError: null,
    }),
    postLoginSuccess: (state, action) => {
<<<<<<< HEAD
      state.logined = true;
    },
    postLoginError: (state, action) => {
      state.logined = false;
    },
=======
      console.log(action.payload);
      return {
        ...state,
        loginLoading: false,
        loginSucceed: true,
        loginError: null,
        user: action.payload,
      };
    },
    postLoginError: (state, action) => ({
      ...state,
      loginLoading: false,
      loginSucceed: false,
      loginError: action.payload,
    }),
>>>>>>> e81864fa6fa2a2b3e0e5463822257c93a9ed12f9
  },
});
export const {
  postLogin,
  postSocialLogin,
  postLoginSuccess,
  postLoginError } = loginSlice.actions;
export default loginSlice.reducer;
