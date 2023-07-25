// user.slice.js

import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    users: [],
    roles: [],
    userModal: false,
    error: null,
    username: "",
    password: "",
    roleName: ""
};

const userSlice = createSlice({
    name: "user",
    initialState,
    reducers: {
        getUsersSuccess: (state, action) => {
            state.users = action.payload;
        },
        getUsersFailure: (state, action) => {
            state.error = action.payload;
        },
        getUsers: (state) => {},
        getRolesSuccess: (state, action) => {
            state.roles = action.payload;
        },
        getRolesFailure: (state, action) => {
            state.error = action.payload;
        },
        getRoles: (state) => {},
        toggleUserModalSuccess: (state, action) => {
            state.userModal = action.payload;
        },
        toggleUserModalFailure: (state, action) => {
            state.error = action.payload;
        },
        toggleShowModal: (state) => {},
        setUsername: (state, action) => {
            state.username = action.payload;
        },
        setPassword: (state, action) => {
            state.password = action.payload;
        },
        setRoleName: (state, action) => {
            state.roleName = action.payload;
        },
        saveUserSuccess: (state, action) => {
            state.users.push(action.payload);
        },
        saveUserFailure: (state, action) => {
            state.error = action.payload;
        },
        saveUser: (state, action) => {},
    },
});

export const {
    getUsersSuccess,
    getUsers,
    getUsersFailure,
    getRolesSuccess,
    getRoles,
    getRolesFailure,
    toggleUserModalSuccess,
    toggleUserModalFailure,
    toggleShowModal,
    setUsername,
    setPassword,
    setRoleName,
    saveUserSuccess,
    saveUserFailure,
    saveUser
} = userSlice.actions;

export default userSlice.reducer;