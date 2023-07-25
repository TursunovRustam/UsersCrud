import { call, put, takeLatest } from "redux-saga/effects";
import Req from "../../Components/Tools/Req";
import {
    GET_PRODUCTS,
    GET_CATEGORIES,
    TOGGLE_SHOW_MODAL_PRODUCT,
    SAVE_PRODUCT
} from "../constans";
import {
    getProductsSuccess,
    getProductsFailure,
    getCategoriesSuccess,
    getCategoriesFailure,
    toggleProductModalSuccess,
    toggleProductModalFailure,
    toggleShowModal,
    saveProductSuccess,
    saveProductFailure,
    getProducts, // Import the getProducts action
} from "../store/Product/product.slice";
import qrcode from "qrcode";

function* watchGetProducts() {
    try {
        const { data } = yield call(Req, "/product/getAllCards", "GET", null);
        console.log(data)
        yield put(getProductsSuccess(data));
    } catch (err) {
        yield put(getProductsFailure(err));
    }
}

function* watchGetCategories() {
    try {
        const { data } = yield call(Req, "/category", "GET", null);
        yield put(getCategoriesSuccess(data));
    } catch (err) {
        yield put(getCategoriesFailure(err));
    }
}

function* watchToggleShowModal(action) {
    try {
        yield put(toggleProductModalSuccess(action.payload));
    } catch (e) {
        yield put(toggleProductModalFailure(e.message));
    }
}

function* watchSaveProduct(action) {
    const product = action.payload;
    const formData = action.payload.file;
    try {
        const imgRes = yield Req("/avatarImg", "POST", formData);
        const newProduct = {
            name: product.name,
            categoryId: product.categoryId,
            code: product.code,
            price: product.price,
            imgId: imgRes.data
        }
        const productRes = yield Req("/product", "POST", newProduct);
        const incomeProductData = {
            productId: productRes.data.id,
            incomePrice: product.incomePrice,
            count: 0,
        };
        const incomeProductRes = Req("/income-product", "POST", incomeProductData);
        const canvas = document.createElement("canvas");
        qrcode.toCanvas(canvas, productRes.data.code, { width: 256 });
        const base64 = canvas.toDataURL();
        const qrCodeRes = yield Req("/product/print", "POST", { base64 });
        const { data } = yield call(Req, "/product/getAllCards", "GET", null);
        yield put(getProductsSuccess(data))
        yield put(toggleShowModal(false));
    } catch (err) {
        console.log(err);
        yield put(saveProductFailure(err));
    }
}

export function* productSaga() {
    yield takeLatest(GET_PRODUCTS, watchGetProducts);
    yield takeLatest(GET_CATEGORIES, watchGetCategories);
    yield takeLatest(TOGGLE_SHOW_MODAL_PRODUCT, watchToggleShowModal);
    yield takeLatest(SAVE_PRODUCT, watchSaveProduct);
}
