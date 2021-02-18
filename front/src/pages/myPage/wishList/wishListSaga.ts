import { all, call, fork, put, takeLatest } from 'redux-saga/effects';
import getWishListAsync from '../../../api/wishListApi';
import {
  getWishListRequest,
  getWishListRequestSuccess,
  getWishListRequestError,
} from './wishListSlice';

function* getWishListRequestSaga(action: { payload: number }) {
  try {
    const result = yield call(getWishListAsync, action.payload);
    const { myWishList, totalPage } = result.data.data;
    console.log(result.data.data, 'asdasds');
    yield put({
      type: getWishListRequestSuccess,
      payload: { myWishList, totalPage },
    });
  } catch (error) {
    yield put({
      type: getWishListRequestError,
      payload: error.message,
    });
  }
}

function* watchWishList(): Generator {
  yield takeLatest(getWishListRequest, getWishListRequestSaga);
}

export default function* wishListSaga(): Generator {
  yield all([
    fork(watchWishList),
  ]);
}
