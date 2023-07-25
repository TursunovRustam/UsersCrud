import {useEffect, useState} from "react";
import Req from "../Tools/Req.js";
import {useNavigate} from "react-router-dom";
import {useLanguage} from "../Tools/LanguageContext";
import languageJson from "../Tools/language.json";

function Home() {
    const navigate = useNavigate();

    const [role, setRole] = useState("");
    useEffect(()=>{
        check()
    },[role])
    const { language } = useLanguage();
    const [translated, setTranslated] = useState([]);
    useEffect(() => {
        if (language === "UZ") {
            setTranslated(languageJson.home.uz);
        } else if (language === "RU") {
            setTranslated(languageJson.home.ru);
        } else {
            alert("bunday til topilmadi");
        }
    }, [language]);
    function check(){
        Req("/user/getRole", "GET", null).then(res=>{
            setRole(res.data[0].name)
            switch (res.data[0].name){
                case "ROLE_CASHIER":
                    navigate("/product-selling")
                    break;
                case "ROLE_ADMIN":
                    navigate("/products")
                    break;
                case "ROLE_SUPER_ADMIN":
                    navigate("/admin")
                    break;
                default:
                    navigate("/login");
                    break
            }
        }).catch(err=>{
            navigate("/login")
        })
    }
    return (
        <div className={"d-flex justify-content-center align-items-center pt-2 pb-2 bg-light w-100"} style={{height:"93.2vh"}}>
            <div className="btn-group">
                {role==="ROLE_ADMIN" || role==="ROLE_SUPER_ADMIN" ? <button className="btn btn-outline-dark" onClick={()=>navigate("/products")}>{translated.productsPage}</button>:""}
                {role==="ROLE_ADMIN" || role ==="ROLE_SUPER_ADMIN" || role === "ROLE_CASHIER" ? <button className="btn btn-outline-dark" onClick={()=>navigate("/product-selling")}>{translated.sellingPage}</button>: ""}
                {role === "ROLE_SUPER_ADMIN" ? <button className="btn btn-outline-dark" onClick={()=>navigate("/admin")}>{translated.adminPage}</button> : ""}
            </div>
        </div>
    );
}

export default Home;