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
      console.log(action.payload);
    },
    postLoginError: (state, action) => {
      console.log(action.payload);
    },
  },
});
export const { postLogin, postLoginSuccess, postLoginError } = loginSlice.actions;
export default loginSlice.reducer;
