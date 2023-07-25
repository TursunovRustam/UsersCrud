import React, {useEffect, useState} from 'react';
import {Modal} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {useForm} from "react-hook-form";
import Req from "../../Tools/Req";
import {useLanguage} from "../../Tools/LanguageContext";
import languageJson from "../../Tools/language.json";

function Category(props) {
    const [categories, setCategories] = useState([])
    const [showCategoryModal, setShowCategoryModal] = useState(false);
    const navigate = useNavigate();
    const {
        register,
        handleSubmit,
        reset,
        formState: {errors},
    } = useForm()

    useEffect(()=>{
        getCategories()
    },[])
    const { language } = useLanguage();
    const [translated, setTranslated] = useState(null);

    useEffect(() => {
        if (language === "UZ") {
            setTranslated(languageJson.category.uz);
        } else if (language === "RU") {
            setTranslated(languageJson.category.ru);
        } else {
            alert("Bunday til topilmadi");
        }
    }, [language]);
    function handleClose() {
        setShowCategoryModal(false)
    }

    function getCategories(){
        Req("/category", "GET",null).then(res=>{
            setCategories(res.data)
        }).catch(err=>{
            navigate("/login")
        })
    }

    function saveCategory(data) {
        if (data==""){
            alert("")
        } else {
            Req("/category", "POST", data).then(res=>{
                getCategories()
                setShowCategoryModal(false)

            })
        }

        reset()
    }
    if (!translated){
        return null;
    }
    return (
        <div>
            <div className="p-5">
                <button className="btn btn-danger mx-2" style={{borderRadius:"25%"}} onClick={()=>navigate("/admin")}>{translated.backButton}</button>
                <button className="btn btn-outline-secondary" onClick={()=>setShowCategoryModal(true)}>{translated.newCategory}</button>

                <table className="table">
                    <thead>
                    <tr>
                        <th>{translated.categoryName}</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        categories.map(item=><tr key={item.id}>
                                <td>{item.name}</td>
                            </tr>

                        )
                    }

                    </tbody>
                </table>
                <Modal show={showCategoryModal} onHide={handleClose} centered={true}>
                    <Modal.Header closeButton>
                        <Modal.Title>{translated.newCategory}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <form id={"category"}>
                            <input type="text" className={`form-control my-3 ${
                                errors.password ? "is-invalid" : ""
                            }`}
                                   placeholder={translated.categoryName} {...register("name", {required: true})} autoFocus={true} />
                            {errors.name && (
                                <div className="invalid-feedback">
                                    {"xatolik"}
                                </div>
                            )}
                        </form>
                    </Modal.Body>
                    <Modal.Footer>
                        <button className={"btn btn-secondary"} onClick={handleClose}>
                            {translated.closeButton}
                        </button>
                        <button className={"btn btn-secondary"} onClick={handleSubmit(saveCategory)} type={"submit"}
                                form={"category"}>
                            {translated.saveChangesButton}
                        </button>
                    </Modal.Footer>
                </Modal>
            </div>
        </div>
    );
}

export default Category;