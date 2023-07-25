import {Link} from "react-router-dom";
import Flag from "react-world-flags";
import {useLanguage} from "../Tools/LanguageContext";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import languageJson from "../Tools/language.json"
import Req from "../Tools/Req.js";
import React from "react";

function Navbar() {
    const [auth, setAuth] = useState({});
    const [role, setRole] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        check()
    }, [role])

    const {language, setLanguage} = useLanguage();
    const [translated, setTranslated] = useState([]);

    useEffect(() => {
        if (language === "UZ") {
            setTranslated(languageJson.auth.uz);
        } else if (language === "RU") {
            setTranslated(languageJson.auth.ru);
        } else {
            alert("bunday til topilmadi");
        }
    }, [language]);

    function changeLang(value) {
        setLanguage(value)
        localStorage.setItem("lan", value)
    }

    function check() {
        Req("/user/getRole", "GET", null).then(res => {
            setRole(res.data[0].name)
        }).catch(err => {
            navigate("/login")
        })
    }

    return (
        <div>
            <div className="bg-dark text-white d-flex justify-content-center align-items-center"
                 style={{height: "70px"}}>
                <div className="container d-flex justify-content-between">
                    <div className="d-flex gap-2">
                        <button
                            className={language === "UZ" ? "btn btn-info" : "btn"}
                            onClick={() => changeLang("UZ")}
                            value={"UZ"}
                        >
                            <Flag code="UZ" height="16px"></Flag>
                        </button>
                        <button
                            className={language === "RU" ? "btn btn-info" : "btn"}
                            onClick={() => changeLang("RU")}
                        >
                            <Flag code="RU" height="16px"></Flag>
                        </button>
                    </div>

                    <div className={"d-flex justify-content-center  w-100"}
                    >
                        <div className="btn-group">
                            {role === "ROLE_ADMIN" || role === "ROLE_SUPER_ADMIN" ?
                                <button className="btn btn-outline-light"
                                        onClick={() => navigate("/products")}>{translated.productsPage}</button> : ""}
                            {role === "ROLE_ADMIN" || role === "ROLE_SUPER_ADMIN" || role === "ROLE_CASHIER" ?
                                <button className="btn btn-outline-light"
                                        onClick={() => navigate("/product-selling")}>{translated.sellingPage}</button> : ""}
                            {role === "ROLE_SUPER_ADMIN" ? <button className="btn btn-outline-light"
                                                                   onClick={() => navigate("/admin")}>{translated.adminPage}</button> : ""}

                        </div>
                    </div>

                    <Link to={"/login"}>
                        <button className={"btn btn-outline-light"}>{translated.login}</button>
                    </Link>
                </div>
            </div>
        </div>
    )
}

export default Navbar;