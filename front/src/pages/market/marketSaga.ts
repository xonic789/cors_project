import { PayloadAction } from '@reduxjs/toolkit';
import { act } from 'react-dom/test-utils';
import { all, takeLatest, put, fork, call } from 'redux-saga/effects';
import { loadMarketAPI, loadMarketDetailAPI, loadMarketPostDetailAPI, addMarketPostAPI } from '../../api/marketApi';
import { maketDetailLoadError, maketDetailLoadRequest, maketDetailLoadSuccess,
  maketpostLoadError, maketpostLoadSuccess, marketLoadError, marketLoadRequest, marketLoadSuccess,
  addMarketPostSuccess, addMarketPostError, addMarketPostRequest } from './marketSlice';

interface marketPostActionInterface {
  marketId: number,
  articleId: number,
}

interface addMarketActionInterface {
  market: FormData,
}

function* loadMarketList() {
  try {
    const result = yield call(loadMarketAPI);
    yield put(marketLoadSuccess(result.data));
  } catch (error) {
    yield put(marketLoadError({ error }));
  }
}

function* loadMarketDetail(action: PayloadAction<number>) {
  try {
    const result = yield call(loadMarketDetailAPI, action.payload);
    yield put(maketDetailLoadSuccess(result.data));
  } catch (error) {
    yield put(maketDetailLoadError({ error }));
  }
}

function* loadMarketPost(action: PayloadAction<marketPostActionInterface>) {
  try {
    const result = yield call(loadMarketPostDetailAPI, action.payload.marketId, action.payload.articleId);
    yield put(maketpostLoadSuccess);
  } catch (error) {
    yield put(maketpostLoadError({ error }));
  }
}

function* addMarketPost(action: PayloadAction<addMarketActionInterface>) {
  try {
    console.log(action.payload, 'payload');
    const result = yield call(addMarketPostAPI, action.payload.market);
    console.log(result);
    // yield put(addMarketPostSuccess(result));
  } catch (error) {
    yield put(addMarketPostError({ error }));
  }
}

function* watchMarket(): Generator {
  yield takeLatest(addMarketPostRequest, addMarketPost);
}

export default function* noticeSaga(): Generator {
  yield all([
    fork(watchMarket),
  ]);
}
