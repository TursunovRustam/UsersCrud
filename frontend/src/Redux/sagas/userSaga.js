// user.saga.js

import Req from "../../Components/Tools/Req";
import { call, put, takeLatest } from "redux-saga/effects";
import {
    GET_USERS,
    GET_ROLES,
    TOGGLE_SHOW_MODAL_USER,
    SAVE_USER
} from "../constans";
import {
    getUsersSuccess,
    getUsersFailure,
    getRolesSuccess,
    getRolesFailure,
    toggleUserModalSuccess,
    toggleUserModalFailure,
    saveUserSuccess,
    saveUserFailure
} from "../store/Admin/User/user.slice";
import {getUsers} from "../store/Admin/User/user.slice";

function* watchGetUsers() {
    try {
        const { data } = yield call([Req, "/user", "GET", null]);
        yield put(getUsersSuccess(data));
    } catch (err) {
        yield put(getUsersFailure(err));
    }
}

function* watchGetRoles() {
    try {
        const { data } = yield call([Req, "/role", "GET", null]);
        yield put(getRolesSuccess(data));
    } catch (err) {
        yield put(getRolesFailure(err));
    }
}

function* watchToggleShowModal(action) {
    try {
        yield put(toggleUserModalSuccess(action.payload));
    } catch (e) {
        yield put(toggleUserModalFailure(e.message));
    }
}

function* watchSaveUser(action) {
    const user = {
        username: action.payload.username,
        password: action.payload.password,
        roleName: action.payload.roleName
    };
    try {
        const { data } = yield call([Req, "/user/register", "POST", user]);
        yield put(saveUserSuccess(data));
        yield put(getUsers());
    } catch (err) {
        yield put(saveUserFailure(err));
    }
}

export function* userSaga() {
    yield takeLatest(GET_USERS, watchGetUsers);
    yield takeLatest(GET_ROLES, watchGetRoles);
    yield takeLatest(TOGGLE_SHOW_MODAL_USER, watchToggleShowModal);
    yield takeLatest(SAVE_USER, watchSaveUser);
}