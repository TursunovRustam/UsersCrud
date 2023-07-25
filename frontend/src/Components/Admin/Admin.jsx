import React, {useEffect, useState} from 'react';
import {useNavigate} from "react-router-dom";
import Req from "../Tools/Req";
import {useLanguage} from "../Tools/LanguageContext";
import languageJson from "../Tools/language.json";

function Admin(props) {
    const navigate = useNavigate();
    useEffect(()=>{
        check()
    },[])
    const { language } = useLanguage();
    const [translated, setTranslated] = useState([]);
    useEffect(() => {
        if (language === "UZ") {
            setTranslated(languageJson.admin.uz);
        } else if (language === "RU") {
            setTranslated(languageJson.admin.ru);
        } else {
            alert("bunday til topilmadi");
        }
    }, [language]);

    function check(){
        Req("/user/getRole", "GET", null).then(res=>{
            if(res.data[0].name!=="ROLE_SUPER_ADMIN"){

                navigate("/login")
            }
        }).catch(err=>{
            navigate("/login")
        })
    }

    return (
        <div className={"d-flex justify-content-center align-items-center pt-2 pb-2 bg-light w-100"} style={{height:"93.2vh"}}>
            <div className="btn-group">
                <button className="btn btn-danger mx-2" style={{borderRadius:"25%"}} onClick={()=>navigate("/")}>{translated.backButton}</button>
                <button className="btn btn-outline-dark" onClick={()=>navigate("/admin/users")}>{translated.usersButton}</button>
                <button className="btn btn-outline-dark" onClick={()=>navigate("/admin/categories")}>{translated.categoriesButton}</button>
                <button className="btn btn-outline-dark" onClick={()=>navigate("/admin/tills")}>{translated.tillsButton}</button>
            </div>
        </div>
    )
}

export default Admin;