import React, {useEffect, useState} from 'react';
import {Modal} from "react-bootstrap";
import DefProfileImg from "../../Auth/def-profile-img.png";
import Req from "../../Tools/Req";
import {useForm} from "react-hook-form";
import {useNavigate} from "react-router-dom";
import {useLanguage} from "../../Tools/LanguageContext";
import languageJson from "../../Tools/language.json";

function Users(props) {
    const [users, setUsers] = useState([])
    const [showUserModal, setShowUserModal] = useState(false);
    const [roles, setRoles] = useState([])
    const navigate = useNavigate();
    const {
        register,
        handleSubmit,
        formState: {errors}
    } = useForm()

    useEffect(()=>{
        getUsers()
        getRoles()
    },[])
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
    function handleClose() {
        setShowUserModal(false)
    }

    function getUsers(){
        Req("/user", "GET", null).then(res=>{
            setUsers(res.data)
        }).catch(err=>{
            navigate("/login")
        })
    }

    function getRoles(){
        Req("/role", "GET", null).then(res=>{
            setRoles(res.data)
        })
    }

    function saveUser(data) {
        Req("/user/register", "POST", data).then(userRes => {
            getUsers()
            handleClose()
        })
    }
    if (!translated){
        return null;
    }
    return (
        <div className={"p-5"}>
            <button className="btn btn-danger mx-2" style={{borderRadius: "25%"}}
                    onClick={() => navigate("/admin")}>{translated.closeButton}
            </button>
            <button className="btn btn-outline-secondary" onClick={() => setShowUserModal(true)}>{translated.newUser}</button>

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
            <Modal show={showUserModal} onHide={handleClose} centered={true}>
                <Modal.Header closeButton>
                    <Modal.Title>{translated.newUser}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form id={"user"}>
                        <input type="text" className={"form-control my-3"}
                               placeholder={translated.username} {...register("username", {required: true})} autoFocus={true}/>
                        <input type="text" className={"form-control my-3"}
                               placeholder={translated.password} {...register("password", {required: true})}/>
                        <select className={"form-select"} {...register("roleName", {required: true})}>
                            <option value="" selected={true} disabled={true}>Rolni tanlang</option>
                            {
                                roles.map(item=> <option value={item.name}>{item.name}</option>)
                            }
                        </select>
                    </form>
                </Modal.Body>
                <Modal.Footer>
                    <button className={"btn btn-secondary"} onClick={handleClose}>
                        {translated.closeButton}
                    </button>
                    <button className={"btn btn-secondary"} onClick={handleSubmit(saveUser)} type={"submit"}
                            form={"user"}>
                        {translated.saveChangesButton}
                    </button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}

export default Users;