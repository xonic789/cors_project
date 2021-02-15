import { all, call, fork, put, takeLatest } from 'redux-saga/effects';
import getWishListAsync from '../../api/wishListApi';
import {
  getWishListRequest,
  getWishListRequestSuccess,
  getWishListRequestError,
} from './wishListSlice';

function* getWishListRequestSaga(action: { payload: number }) {
  try {
    const result = yield call(getWishListAsync, action.payload);
    const { myAricleList, totalPage } = result.data.data;
    console.log(result.data.data, 'asdasds');
    yield put({
      type: getWishListRequestSuccess,
      payload: { myAricleList, totalPage },
    });
  } catch (error) {
    yield put({
      type: getWishListRequestError,
      payload: error,
    });
  }
}

function* watchWishList(): Generator {
  yield takeLatest(getWishListRequest, getWishListRequestSaga);
}

export default function* loginSaga(): Generator {
  yield all([
    fork(watchWishList),
  ]);
}
