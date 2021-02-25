import { PayloadAction } from '@reduxjs/toolkit';
import { all, takeLatest, put, fork, call } from 'redux-saga/effects';
import { loadMarketAPI, loadMarketDetailAPI, loadMarketPostDetailAPI, addMarketAPI } from '../../api/marketApi';
import { addMarketBookPostAPI } from '../../api/postBookApi';
import { AddBookPostInterface } from '../../interfaces/PostList.interface';
import { addMarketError, addMarketPostError, addMarketPostRequest, addMarketPostSuccess, addMarketRequest, addMarketSuccess, maketDetailLoadError, maketDetailLoadRequest, maketDetailLoadSuccess,
  maketpostLoadError, maketpostLoadRequest, maketpostLoadSuccess, marketLoadError, marketLoadRequest, marketLoadSuccess } from './marketSlice';

interface marketPostActionInterface {
  marketId: number,
  articleId: number,
}

interface addMarketActionInterface {
  market: FormData,
}
interface addBookPostPayloadInterface {
  data: AddBookPostInterface
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
function* addMarketPost(action: PayloadAction<addBookPostPayloadInterface>) {
  try {
    const result = yield call(addMarketBookPostAPI, action.payload.data);
    yield put(addMarketPostSuccess(result.data));
  } catch (error) {
    yield put(addMarketPostError({ error }));
  }
}
function* addMarket(action: PayloadAction<addMarketActionInterface>) {
  try {
    console.log(action.payload, 'payload');
    const result = yield call(addMarketAPI, action.payload.market);
    yield put(addMarketSuccess(result));
  } catch (error) {
    yield put(addMarketError({ error }));
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
function* watchAddMarketPost() {
  yield takeLatest(addMarketPostRequest, addMarketPost);
}
function* watchMarket(): Generator {
  yield takeLatest(addMarketRequest, addMarket);
}
export default function* marketSaga():Generator {
  yield all([
    fork(watchloadMarketList),
    fork(watchloadMarketDetail),
    fork(watchloadMarketPost),
    fork(watchAddMarketPost),
    fork(watchMarket),
  ]);
}
