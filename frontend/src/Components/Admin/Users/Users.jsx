import React, { useEffect, useState } from "react";
import { Modal } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { useLanguage } from "../../Tools/LanguageContext";
import languageJson from "../../Tools/language.json";
import { useDispatch, useSelector } from "react-redux";
import {
    getUsers,
    toggleShowModal,
    setUsername,
    setPassword,
    setRoleName,
    saveUser,
    getRoles
} from "../../../Redux/store/Admin/User/user.slice";

function Users(props) {
    const dispatch = useDispatch();
    const users = useSelector((state) => state.user.users);
    const roles = useSelector((state) => state.user.roles);
    const showUserModal = useSelector((state) => state.user.userModal);
    const username = useSelector((state) => state.user.username);
    const password = useSelector((state) => state.user.password);
    const roleName = useSelector((state) => state.user.roleName);
    const navigate = useNavigate();

    useEffect(() => {
        dispatch(getUsers());
        dispatch(getRoles());
    }, [dispatch]);

    const { language } = useLanguage();
    const [translated, setTranslated] = useState(null);

    useEffect(() => {
        if (language === "UZ") {
            setTranslated(languageJson.users.uz);
        } else if (language === "RU") {
            setTranslated(languageJson.users.ru);
        } else {
            alert("Bunday til topilmadi");
        }
    }, [language]);

    if (!translated) {
        return null;
    }

    return (
        <div className={"p-5"}>
            <button className="btn btn-danger mx-2" style={{borderRadius: "25%"}}
                    onClick={() => navigate("/admin")}>{translated.closeButton}
            </button>
            <button className="btn btn-outline-secondary" onClick={() => dispatch(toggleShowModal(true))}>{translated.newUser}</button>

            <table className="table bg-dark text white">
                <thead>
                <tr>
                    <th>{translated.username}</th>
                    <th>{translated.userRole}</th>
                </tr>
                </thead>
                <tbody>
                {
                    users.map(item => <tr key={item.id}>
                            <td>{item.username}</td>
                            <td>{item.role_Name}</td>
                        </tr>
                    )
                }

                </tbody>
            </table>
            <Modal show={showUserModal} onHide={() => dispatch(toggleShowModal(false))} centered={true}>
                <Modal.Header closeButton>
                    <Modal.Title>{translated.newUser}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form id={"user"}>
                        <input type="text" className={"form-control my-3"}
                               placeholder={translated.username}
                               value={username}
                               onChange={(e)=>dispatch(setUsername(e.target.value))}
                               autoFocus={true}/>
                        <input type="text" className={"form-control my-3"}
                               placeholder={translated.password}
                               value={password}
                               onChange={(e)=>dispatch(setPassword(e.target.value))}/>
                        <select className={"form-select"}
                                value={roleName}
                                onChange={(e)=>dispatch(setRoleName(e.target.value))}>
                            <option value="" selected={true} disabled={true}>Rolni tanlang</option>
                            {
                                roles.map(item=> <option value={item.name}>{item.name}</option>)
                            }
                        </select>
                    </form>
                </Modal.Body>
                <Modal.Footer>
                    <button className={"btn btn-secondary"} onClick={() => dispatch(toggleShowModal(false))}>
                        {translated.closeButton}
                    </button>
                    <button className={"btn btn-secondary"} onClick={() => dispatch(saveUser({username, password, roleName}))} type={"submit"}
                            form={"user"}>
                        {translated.saveChangesButton}
                    </button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}

export default Users;