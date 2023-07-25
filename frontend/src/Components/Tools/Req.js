import axios from "axios";

function Req(url, method, data){
    return axios({
        baseURL:"http://localhost:8080/api",
        url,
        method,
        data,
        headers:{
            token: localStorage.getItem("token")
        }
    })
}

export default Req;