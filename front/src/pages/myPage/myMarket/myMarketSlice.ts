import { createSlice } from '@reduxjs/toolkit';

const myMarketSlice = createSlice({
  name: 'myMarket',
  initialState: {
    myMarketList: [],
    totalPage: 0,
    isGetMyMarketListLoading: false,
    isGetMyMarketListSuccess: false,
    isGetMyMarketListError: null,
  },
  reducers: {
    getMyMarketListRequest: (state, action) => {
      state.isGetMyMarketListLoading = true;
    },
    getMyMarketListRequestSuccess: (state, action) => {
      state.isGetMyMarketListLoading = false;
      state.isGetMyMarketListSuccess = true;
      state.myMarketList = action.payload.myMarketInfoList;
      state.totalPage = action.payload.totalPage;
    },
    getMyMarketListRequestError: (state, action) => {
      state.isGetMyMarketListLoading = false;
      state.isGetMyMarketListError = action.payload;
    },
  },
});

export const {
  getMyMarketListRequest,
  getMyMarketListRequestSuccess,
  getMyMarketListRequestError,
} = myMarketSlice.actions;

export default myMarketSlice.reducer;
