import { configureStore, combineReducers } from "@reduxjs/toolkit";
import createSagaMiddleware from "redux-saga";
import  categorySlice  from "./Admin/Category/category.slice";
import  userSlice  from "./Admin/User/user.slice";
import rootSaga from "../sagas/rootSaga";
import productSlice from "./Product/product.slice";

const sagaMiddleware = createSagaMiddleware(rootSaga);

const store = configureStore({
  reducer: { category: categorySlice , user:userSlice, product: productSlice},
  middleware: [sagaMiddleware],
});
sagaMiddleware.run(rootSaga);

export default store;
