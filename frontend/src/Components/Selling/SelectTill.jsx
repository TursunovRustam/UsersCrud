import React, {useEffect, useState} from 'react';
import Req from "../Tools/Req";
import {useNavigate} from "react-router-dom";

function SelectTill(props) {
    const [tills, setTills] = useState([]);
    const navigate = useNavigate();
    useEffect(res=>{
        Req("/till", "GET", null).then(res=>{
            setTills(res.data)
        })
    },[])

    function selectTill(item) {
        const newData = {...item, selected:true}
        Req("/till", "POST", newData).then(res=>{
            localStorage.setItem("till", res.data.id)
            navigate("/product-selling");
        })
    }
    

    return (
        <div className={"d-flex justify-content-center align-items-center flex-column"}>
            <h2>Kassani Tanlang</h2>
            {
                tills.map(item=>
                    !item.selected&&<button className={"btn btn-dark"} onClick={()=>selectTill(item)}>
                        {item.number}
                    </button>
                )
            }
        </div>
    );
}

export default SelectTill;