// product.slice.js
import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    products: [],
    categories: [],
    productModal: false,
    file: "",
    currentFile: "",
    filterObj: {
        categoryId: "",
        search: "",
    },
    error: null,
};

const productSlice = createSlice({
    name: "product",
    initialState,
    reducers: {
        getProductsSuccess: (state, action) => {
            state.products = action.payload;
        },
        getProductsFailure: (state, action) => {
            state.error = action.payload;
        },
        getProducts: () => {},
        getCategoriesSuccess: (state, action) => {
            state.categories = action.payload;
        },
        getCategoriesFailure: (state, action) => {
            state.error = action.payload;
        },
        toggleProductModalSuccess: (state, action) => {
            state.productModal = action.payload;
        },
        toggleProductModalFailure: (state, action) => {
            state.error = action.payload;
        },
        toggleShowModal: (state, action) => {
            state.productModal = action.payload;
        },
        setFile: (state, action) => {
            state.file = action.payload;
        },
        setCurrentFile: (state, action) => {
            state.currentFile = action.payload;
        },
        setFilterObj: (state, action) => {
            state.filterObj = action.payload;
        },
        saveProductSuccess: (state, action) => {
            return {
                ...state,
                products: [...state.products, action.payload], // Create a new array with the updated product data
            };
        },

        saveProductFailure: (state, action) => {
            state.error = action.payload;
        },
        saveProduct: product => ({ type: 'SAVE_PRODUCT', payload: product }),
    },
});

export const {
    getProductsSuccess,
    getProducts,
    getProductsFailure,
    getCategoriesSuccess,
    getCategoriesFailure,
    toggleProductModalSuccess,
    toggleProductModalFailure,
    toggleShowModal,
    setFile,
    setCurrentFile,
    setFilterObj,
    saveProductSuccess,
    saveProductFailure,
    saveProduct
} = productSlice.actions;

export default productSlice.reducer;