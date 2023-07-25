import React, {useEffect, useState} from 'react';
import {Modal} from "react-bootstrap";
import Req from "../../Tools/Req";
import {useForm} from "react-hook-form";
import {useNavigate} from "react-router-dom";
import {useLanguage} from "../../Tools/LanguageContext";
import languageJson from "../../Tools/language.json";

function Till(props) {
    const [tills, setTills] = useState([])
    const [showUserModal, setShowUserModal] = useState(false);
    const navigate = useNavigate();
    const {
        register,
        handleSubmit,
        formState: {errors},
        reset
    } = useForm()

    const {language} = useLanguage();
    const [translated, setTranslated] = useState(null);

    useEffect(() => {
        console.log(languageJson)
        if (language === "UZ") {
            setTranslated(languageJson.till.uz);
        } else if (language === "RU") {
            setTranslated(languageJson.till.ru);
        } else {
            alert("Bunday til topilmadi");
        }
    }, [language]);

    useEffect(() => {
        getTills()
    }, [])

    function handleClose() {
        setShowUserModal(false)
    }

    function getTills() {
        Req("/till", "GET", null).then(res => {
            setTills(res.data)
        }).catch(err => {
            navigate("/login")
        })
    }

    function saveTill(data) {
        Req("/till", "POST", data).then(tillRes => {
            getTills()
            handleClose()
            reset()
        })
    }

    if (!translated) {
        return null;
    }

    return (
        <div className={"p-5"}>
            <button className="btn btn-danger mx-2" style={{borderRadius: "25%"}}
                    onClick={() => navigate("/admin")}>{translated.back}</button>
            <button className="btn btn-outline-secondary"
                    onClick={() => setShowUserModal(true)}>{translated.addTill}</button>

            <table className="table bg-dark text white">
                <thead>
                <tr>
                    <th>{translated.number}</th>
                    <th>{translated.choose}</th>
                </tr>
                </thead>
                <tbody>
                {
                    tills.map(item => <tr key={item.id}>
                            <td>{item.number}</td>
                            <td>{item.selected ? translated.choose : translated.notChoose}</td>
                        </tr>
                    )
                }
                </tbody>
            </table>
            <Modal show={showUserModal} onHide={handleClose} centered={true}>
                <Modal.Header closeButton>
                    <Modal.Title>{translated.addTill}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form id={"till"}>
                        <input type="text" className={"form-control my-3"}
                               placeholder={translated.number} {...register("number", {required: true})}
                               autoFocus={true}/>
                    </form>
                </Modal.Body>
                <Modal.Footer>
                    <button className={"btn btn-secondary"} onClick={handleClose}>
                        {translated.close}
                    </button>
                    <button className={"btn btn-secondary"} onClick={handleSubmit(saveTill)} type={"submit"}
                            form={"till"}>
                        {translated.save}
                    </button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}

export default Till;