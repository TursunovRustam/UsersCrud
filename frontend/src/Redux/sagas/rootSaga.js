import { all, fork } from "redux-saga/effects";
import { categorySaga } from "./categorySaga";
import {userSaga} from "./userSaga";
import {productSaga} from "./productSaga";

export default function* rootSaga() {
  yield all([fork(categorySaga)]);
  yield all([fork(userSaga)]);
  yield all([fork(productSaga)]);
}
