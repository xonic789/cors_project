import { all, call, fork, put, takeLatest } from 'redux-saga/effects';
import getMyMarketListAsync from '../../../api/myMarketApi';
import {
  getMyMarketListRequest,
  getMyMarketListRequestSuccess,
  getMyMarketListRequestError,
} from './myMarketSlice';

function* getMyMarketListRequestSaga(action: { payload: number }) {
  try {
    const result = yield call(getMyMarketListAsync, action.payload);
    console.log(result);
    const { myMarketInfoList, totalPage } = result.data.data;

    yield put({
      type: getMyMarketListRequestSuccess,
      payload: { myMarketInfoList, totalPage },
    });
  } catch (error) {
    yield put({
      type: getMyMarketListRequestError,
      payload: error,
    });
  }
}

function* watchMyMarketList(): Generator {
  yield takeLatest(getMyMarketListRequest, getMyMarketListRequestSaga);
}

export default function* mySaleArticleSaga(): Generator {
  yield all([
    fork(watchMyMarketList),
  ]);
}
