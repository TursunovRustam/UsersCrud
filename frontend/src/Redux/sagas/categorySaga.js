import Req from "../../Components/Tools/Req";
import { call, put, takeEvery, takeLatest } from "redux-saga/effects";
import { GET_CATEGORIES, TOGGLE_SHOW_MODAL, SAVE_CATEGORY } from "../constans";
import {
  getCategoriesSuccess,
  getCategoriesFailure,
  toggleCategoryModalSuccess,
  toggleCategoryModalFailure,
  saveCategorySuccess,
  saveCategoryFailure
} from "../store/Admin/Category/category.slice";

function* watchGetCategories(action) {
  try {
    const { data } = yield Req("/category", "GET");
    yield put(getCategoriesSuccess(data));
  } catch (e) {
    yield put(getCategoriesFailure(e));
  }
}

function* watchSetShowModal(action) {
  try {
    yield put(toggleCategoryModalSuccess(action.payload));
  } catch (e) {}
  yield put(toggleCategoryModalFailure(action.payload));
}

function* watchSaveCategory(action) {
  const category = {
    name:action.payload
  }
  if (category.name!==""){
    try {
      const { data } = yield Req("/category", "POST", category);
      yield put(saveCategorySuccess(data));
    } catch (error) {
      yield put(saveCategoryFailure(error));
    }
  } else {
    alert("Please enter a category");
  }
}

export function* categorySaga() {
  yield takeEvery(GET_CATEGORIES, watchGetCategories);
  yield takeEvery(TOGGLE_SHOW_MODAL, watchSetShowModal);
  yield takeEvery(SAVE_CATEGORY, watchSaveCategory);
}
