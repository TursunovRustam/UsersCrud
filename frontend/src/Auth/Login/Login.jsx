import {useForm} from "react-hook-form";
import Req from "../../Tools/Req.js";
import {Link, useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import languageJson from "../../Tools/language.json";
import {useLanguage} from "../../Tools/LanguageContext";

function Login() {
    const navigate = useNavigate();
    const {language} = useLanguage();
    const [translated, setTranslated] = useState([]);
    const [showPassword, setShowPassword] = useState(false)

    const {
        register,
        handleSubmit,
        formState: {errors}
    } = useForm();

    const loginUser = (data) => {
        Req("/user/login", "POST", data).then((res) => {
            localStorage.setItem("token", res.data);
            navigate("/");
        }).catch(err => {
            navigate("/404")
        })
    };

    useEffect(() => {
        if (language === "UZ") {
            setTranslated(languageJson.login.uz);
        } else if (language === "RU") {
            setTranslated(languageJson.login.ru);
        } else {
            alert("bunday til topilmadi");
        }
    }, [language]);

    return (
        <div className={"w-100 h-100"}>
            <div className="offset-4 mt-5">

                <div className="card w-50 h-50">
                    <div className="card-header bg-dark text-center text-light">
                        <h1>{translated.signIn}</h1>
                    </div>
                    <form name={"login"} onSubmit={handleSubmit(loginUser)}>
                        <div className="card-body">
                            <div className="form-group">
                                <label htmlFor="username">{translated.username}:</label>

                                <input
                                    type="text"
                                    className={`form-control ${
                                        errors.username ? "is-invalid" : ""
                                    }`}
                                    id="username"
                                    placeholder="Username..."
                                    {...register("username", {required: true})}
                                />

                                {errors.username && (
                                    <div className="invalid-feedback">
                                        {translated.userNameError}
                                    </div>
                                )}
                            </div>
                            <div className="form-group">
                                <label htmlFor="password">{translated.password}:</label>
                                <div className="input-group">
                                    <input
                                        type={showPassword ? "text" : "password"}
                                        className={`form-control ${
                                            errors.password ? "is-invalid" : ""
                                        }`}
                                        id="password"
                                        placeholder="Password..."
                                        {...register("password", {required: true})}
                                    />
                                    <div className="input-group-append">
                                        <button
                                            className="btn btn-outline-dark"
                                            type="button"
                                            onClick={() => setShowPassword(!showPassword)}
                                        >
                                            {showPassword
                                                ?
                                                <div>
                                                    <svg
                                                        xmlns="http://www.w3.org/2000/svg"
                                                        width="16"
                                                        height="16"
                                                        fill="currentColor"
                                                        className="bi bi-eye"
                                                        viewBox="0 0 16 16"
                                                    >
                                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
                                                             fill="currentColor" className="bi bi-eye-slash"
                                                             viewBox="0 0 16 16">
                                                            <path
                                                                d="M13.359 11.238C15.06 9.72 16 8 16 8s-3-5.5-8-5.5a7.028 7.028 0 0 0-2.79.588l.77.771A5.944 5.944 0 0 1 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.134 13.134 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755-.165.165-.337.328-.517.486l.708.709z"/>
                                                            <path
                                                                d="M11.297 9.176a3.5 3.5 0 0 0-4.474-4.474l.823.823a2.5 2.5 0 0 1 2.829 2.829l.822.822zm-2.943 1.299.822.822a3.5 3.5 0 0 1-4.474-4.474l.823.823a2.5 2.5 0 0 0 2.829 2.829z"/>
                                                            <path
                                                                d="M3.35 5.47c-.18.16-.353.322-.518.487A13.134 13.134 0 0 0 1.172 8l.195.288c.335.48.83 1.12 1.465 1.755C4.121 11.332 5.881 12.5 8 12.5c.716 0 1.39-.133 2.02-.36l.77.772A7.029 7.029 0 0 1 8 13.5C3 13.5 0 8 0 8s.939-1.721 2.641-3.238l.708.709zm10.296 8.884-12-12 .708-.708 12 12-.708.708z"/>
                                                        </svg>
                                                    </svg>
                                                </div>
                                                :
                                                <div>
                                                <svg
                                                xmlns="http://www.w3.org/2000/svg"
                                                width="16"
                                                height="16"
                                                fill="currentColor"
                                                className="bi bi-eye"
                                                viewBox="0 0 16 16"
                                                >
                                                <path
                                                d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"
                                                />
                                                <path
                                                d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"
                                                />
                                                </svg>
                                                </div>

                                            }
                                        </button>
                                    </div>
                                </div>
                                {errors.password && (
                                    <div className="invalid-feedback">
                                        {translated.passwordError}
                                    </div>
                                )}
                            </div>
                        </div>
                        <div className="card-footer">
                            <button className="btn btn-outline-dark" type="submit">
                                {translated.signIn}
                            </button>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    );
}

export default Login;
