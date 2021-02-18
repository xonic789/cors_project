import { createSlice } from '@reduxjs/toolkit';
import { marketListInterface, marketDetailInterface } from '../../interfaces/MarketInterface';

const marketSlice = createSlice({
  name: 'market',
  initialState: {
    marketList: [],
    marketDetail: {},

    isMaketDetailLoadLoading: false,
    isMaketDetailLoadDone: false,
    isMaketDetailLoadError: null,

    isMarketLoadLoading: false,
    isMarketLoadDone: false,
    isMarketLoadError: null,

    addMarketPostLoading: false,
    addMarketPostDone: false,
    addMarketPostError: null,

    deleteMarketPostLoading: false,
    deleteMarketPostDone: false,
    deleteMarketPostError: null,
  },
  reducers: {
    marketLoadRequest(state, action) {
      state.isMarketLoadLoading = true;
      state.isMarketLoadDone = false;
      state.marketList = action.payload.data;
    },
    marketLoadSuccess(state, action) {
      state.isMarketLoadLoading = false;
      state.isMarketLoadDone = true;
    },
    marketLoadError(state, action) {
      state.isMarketLoadLoading = false;
      state.isMarketLoadDone = false;
      state.isMarketLoadError = action.payload.error;
    },

    maketDetailLoadRequest(state, action) {
      state.isMaketDetailLoadLoading = true;
      state.isMaketDetailLoadDone = false;
      state.marketDetail = action.payload.data;
    },
    maketDetailLoadSuccess(state, action) {
      state.isMaketDetailLoadLoading = false;
      state.isMaketDetailLoadDone = true;
    },
    maketDetailLoadError(state, action) {
      state.isMaketDetailLoadLoading = false;
      state.isMaketDetailLoadDone = false;
      state.isMaketDetailLoadError = action.payload.error;
    },

    addMarketPostRequest(state, action) {
      state.addMarketPostLoading = true;
      state.addMarketPostDone = false;
      state.marketList.concat(action.payload.data);
    },
    addMarketPostSuccess(state, action) {
      state.addMarketPostLoading = false;
      state.addMarketPostDone = true;
    },
    addMarketPostError(state, action) {
      state.addMarketPostLoading = false;
      state.addMarketPostDone = false;
      state.addMarketPostError = action.payload.error;
    },

    deleteMarketPostRequest(state, action) {
      state.deleteMarketPostLoading = true;
      state.deleteMarketPostDone = false;
      state.marketList.filter((data: marketDetailInterface) => data.marketId !== action.payload);
    },
    deleteMarketPostSuccess(state, action) {
      state.deleteMarketPostLoading = false;
      state.deleteMarketPostDone = true;
    },
    deleteMarketPostError(state, action) {
      state.deleteMarketPostLoading = false;
      state.deleteMarketPostDone = false;
      state.deleteMarketPostError = action.payload.error;
    },
  },
});

export const {
  marketLoadRequest,
  marketLoadSuccess,
  marketLoadError,
  maketDetailLoadRequest,
  maketDetailLoadSuccess,
  maketDetailLoadError,
} = marketSlice.actions;

export default marketSlice.reducer;
