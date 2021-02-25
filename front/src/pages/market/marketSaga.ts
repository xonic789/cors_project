import { PayloadAction } from '@reduxjs/toolkit';
import { all, takeLatest, put, fork, call } from 'redux-saga/effects';

import { loadMarketAPI, loadMarketDetailAPI, loadMarketPostDetailAPI, addMarketPostAPI } from '../../api/marketApi';
import { maketDetailLoadError, maketDetailLoadRequest, maketDetailLoadSuccess, maketpostLoadRequest,
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
    console.log(result);
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
    yield put(maketpostLoadSuccess(result.data));
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
function* watchloadMarketList() {
  yield takeLatest(marketLoadRequest, loadMarketList);
}
function* watchloadMarketDetail() {
  yield takeLatest(maketDetailLoadRequest, loadMarketDetail);
}
function* watchloadMarketPost() {
  yield takeLatest(maketpostLoadRequest, loadMarketPost);
}
function* watchMarket(): Generator {
  yield takeLatest(addMarketPostRequest, addMarketPost);
}
export default function* marketSaga():Generator {
  yield all([
    fork(watchloadMarketList),
    fork(watchloadMarketDetail),
    fork(watchloadMarketPost),
    fork(watchMarket),
  ]);
}
