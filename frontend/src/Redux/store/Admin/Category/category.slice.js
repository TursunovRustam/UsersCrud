import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  categories: [],
  categoryModal: false,
  error: null,
  categoryName:""
};

const categorySlice = createSlice({
  name: "category",
  initialState,
  reducers: {
    getCategoriesSuccess: (state, action) => {
      state.categories = action.payload;
    },
    getCategoriesFailure: (state, action) => {
      state.error = action.payload;
    },
    getCategories: (state, action) => {},
    toggleCategoryModalSuccess: (state, action) => {
      state.categoryModal = action.payload;
    },
    toggleCategoryModalFailure: (state, action) => {
      state.error = action.payload;
    },
    toggleShowModal: (state, action) => {},
    setText: (state, action) =>{
      state.categoryName = action.payload;
    },
    saveCategorySuccess: (state, action) => {
      state.categories.push(action.payload);
    },
    saveCategoryFailure: (state, action) => {
      state.error = action.payload;
    },
    saveCategory: (state, action) => {},
  },
});

export const {
  getCategoriesSuccess,
  getCategories,
  getCategoriesFailure,
  toggleCategoryModalSuccess,
  toggleCategoryModalFailure,
  toggleShowModal,
  setText,
  saveCategorySuccess,
  saveCategoryFailure,
  saveCategory
} = categorySlice.actions;
export default categorySlice.reducer;
