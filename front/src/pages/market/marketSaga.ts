import { PayloadAction } from '@reduxjs/toolkit';
import { all, takeLatest, put, fork, call } from 'redux-saga/effects';
import { loadMarketAPI, loadMarketDetailAPI, loadMarketPostDetailAPI } from '../../api/marketApi';
import { maketDetailLoadError, maketDetailLoadRequest, maketDetailLoadSuccess,
  maketpostLoadError, maketpostLoadRequest, maketpostLoadSuccess, marketLoadError, marketLoadRequest, marketLoadSuccess } from './marketSlice';

interface marketPostActionInterface {
  marketId: number,
  articleId: number,
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

function* watchloadMarketList() {
  yield takeLatest(marketLoadRequest, loadMarketList);
}

function* watchloadMarketDetail() {
  yield takeLatest(maketDetailLoadRequest, loadMarketDetail);
}
function* watchloadMarketPost() {
  yield takeLatest(maketpostLoadRequest, loadMarketPost);
}
export default function* marketSaga():Generator {
  yield all([
    fork(watchloadMarketList),
    fork(watchloadMarketDetail),
    fork(watchloadMarketPost),
  ]);
}
