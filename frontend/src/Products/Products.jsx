import {useEffect, useState} from "react";
import {Modal} from 'react-bootstrap';
import Req from "../Tools/Req.js";
import {useForm} from "react-hook-form";
import DefProfileImg from "../Auth/def-profile-img.png";
import {Outlet, useLocation, useNavigate} from "react-router-dom";
import {v4 as uuidv4} from 'uuid';
import qrcode from "qrcode";
import {useLanguage} from "../Tools/LanguageContext";
import languageJson from "../Tools/language.json";

function Products() {
    const [show, setShow] = useState(false);
    const [products, setProducts] = useState([]);
    const [categories, setCategories] = useState([]);
    const [file, setFile] = useState("");
    const [currentFile, setCurrentFile] = useState("");
    const [filterObj, setFilterObj] = useState({
        categoryId: "",
        search: "",
    });
    const {register, handleSubmit, reset, formState: {errors}} = useForm();

    const location = useLocation();
    const navigate = useNavigate();
    const handleClose = () => setShow(false);
    const {language} = useLanguage();
    const [translated, setTranslated] = useState(null);

    useEffect(() => {
        if (language === "UZ") {
            setTranslated(languageJson.product.uz);
        } else if (language === "RU") {
            setTranslated(languageJson.product.ru);
        } else {
            alert("Bunday til topilmadi");
        }
    }, [language]);

    function choosePhoto(file) {
        let reader = new FileReader();
        reader.readAsDataURL(file);
        setFile(file);
        reader.onload = (res) => {
            setCurrentFile(res.srcElement.result);
        };
    }
    
    useEffect(() => {
        if (translated) {
            getProductsByFilter();
        }
    }, [filterObj, translated]);

    useEffect(() => {
        if (translated) {
            getProducts();
            getCategories();
        }
    }, [translated]);

    function getCategories() {
        Req("/category", "GET", null)
            .then((res) => {
                setCategories(res.data);
            })
            .catch((err) => {
                navigate("/login");
            });
    }

    function getProducts() {
        Req("/product/getAllCards", "GET", null)
            .then((res) => {
                setProducts(res.data);
                console.log(res.data);
            })
            .catch((err) => {
                navigate("/login");
            });
    }

    function saveProduct(data) {
        let formData = new FormData();
        formData.append("file", file);
        if (formData==null) {
            alert("rasmni tanlash majburiy")
        } else {
            Req("/avatarImg", "POST", formData)
                .then((imgRes) => {
                    const imgId = imgRes.data;
                    const generatedUuid = uuidv4();
                    const productData = {
                        name: data.name,
                        categoryId: data.categoryId,
                        code: generatedUuid,
                        price: data.price,
                        imgId,
                    };
                    Req("/product", "POST", productData)
                        .then((productRes) => {
                            const incomeProductData = {
                                productId: productRes.data.id,
                                incomePrice: data.incomePrice,
                                count: 0,
                            };
                            Req("/income-product", "POST", incomeProductData)
                                .then((incomeProductRes) => {
                                    const canvas = document.createElement("canvas");
                                    qrcode.toCanvas(canvas, productRes.data.code, {width: 256});
                                    const base64 = canvas.toDataURL();
                                    Req("/product/print", "POST", {base64})
                                        .then((res) => {
                                            localStorage.setItem("currentIncomeProduct", incomeProductRes.data);
                                            getProducts();
                                            setShow(false);
                                            setFile("");
                                            setCurrentFile("");
                                            reset({
                                                name: "",
                                                price: "",
                                                incomePrice: "",
                                                categoryId: "",
                                            });
                                        });
                                });
                        });
                });
        }

    }

    function getProductAbout(item) {
        localStorage.setItem("product", JSON.stringify(item));
        navigate("/products/about/" + item.id);
    }

    function getProductsByFilter() {
        Req("/product/getByFilter", "PATCH", filterObj)
            .then((res) => {
                setProducts(res.data);
            });
    }

    function changeFilter(value, key) {
        const updatedFilterObj = {...filterObj, [key]: value};
        setFilterObj(updatedFilterObj);
    }

    if (!translated) {
        return null;
    }

    return (
        <div className={"d-flex"} /*style={{height: "90vh"}}*/>
            <div className="w-50 p-3">
                <div className="d-flex">
                    <button className="btn btn-danger mx-2" style={{borderRadius: "25%"}} onClick={() => navigate("/")}>
                        {translated.backButton}
                    </button>
                    <button className="btn btn-outline-dark" onClick={() => setShow(true)}>
                        {translated.newProductButton}
                    </button>
                </div>
                <div className="d-flex py-3">
                    <select
                        className={"form-select w-100"}
                        {...register("categoryId")}
                        onChange={(e) => changeFilter(e.target.value, "categoryId")}
                    >
                        <option value="" selected={true} disabled={true}>
                            {translated.category}
                        </option>
                        {categories.map((item) => (
                            <option value={item.id} key={item.id}>
                                {item.name}
                            </option>
                        ))}
                    </select>
                    <input type="text" className="form-control" placeholder={translated.searching} onChange={(e) => {
                        changeFilter(e.target.value, "search");
                    }}/>
                </div>

                <div className={"text-center bg-dark"}
                     style={{height: "550px", overflowY: "auto", display: "flex", gap: "5px", flexWrap: "wrap"}}>
                    {products.map((item) => (
                        <div>
                            <img src={"data:image/jpg;base64," + item.imgUrl.imgUrl} width={"150px"} height={"150px"}
                                 alt="" style={{objectFit: "cover"}}/>
                            <button
                                key={item.id}
                                className="btn btn-outline-light w-75 my-3"
                                style={{height: "50px"}}
                                onClick={() => getProductAbout(item)}
                            >
                                {item.name}
                            </button>
                        </div>
                    ))}
                </div>
                <Modal show={show} onHide={handleClose} centered={true}>
                    <Modal.Header closeButton>
                        <Modal.Title>{translated.newProductButton}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <form id={"product"} className="d-flex flex-column gap-3">
                            {currentFile ? (
                                <label className={"d-flex justify-content-center"}>
                                    <input
                                        type="file"
                                        className={"d-none"}
                                        onChange={(e) => choosePhoto(e.target.files[0])}
                                    />
                                    <img src={currentFile} alt="profile photo" className={"w-25"}/>
                                </label>
                            ) : (
                                <label className={"d-flex justify-content-center"}>
                                    <input
                                        type="file"
                                        className={"d-none"}
                                        onChange={(e) => choosePhoto(e.target.files[0])}
                                    />
                                    <img src={DefProfileImg} alt="profile" className={"profile-img"}/>
                                </label>
                            )}

                            <div className="form-floating">
                                <input
                                    type="text"
                                    className="form-control"
                                    id="name"
                                    placeholder={translated.name}
                                    {...register("name", {required: true})}
                                />
                                <label htmlFor={""}>{translated.name}:</label>
                                {errors.translated?.name && (
                                    <span className="text-danger">{translated.nameError}</span>
                                )}
                            </div>
                            <div className="form-floating">
                                <input
                                    type="number"
                                    className="form-control"
                                    id="price"
                                    placeholder={translated.price}
                                    {...register("price", {required: true})}
                                />
                                <label htmlFor={"price"}>{translated.price}:</label>
                                {errors.translated?.price && (
                                    <span className="text-danger">{translated.priceError}</span>
                                )}
                            </div>
                            <div className="form-floating">
                                <input
                                    type="number"
                                    className="form-control"
                                    id="incomePrice"
                                    placeholder={translated.incomePrice}
                                    {...register("incomePrice", {required: true})}
                                />
                                <label htmlFor={"incomePrice"}>{translated.incomePrice}:</label>
                                {errors.translated?.incomePrice && (
                                    <span className="text-danger">{translated.incomePriceError}</span>
                                )}
                            </div>
                            <div className="form-floating">
                                <select
                                    className={"form-select"}
                                    id={"category"}
                                    {...register("categoryId", {required: true})}
                                >
                                    <option value="" selected={true} disabled={true}>
                                        {translated.category}
                                    </option>
                                    {categories.map((item) => (
                                        <option value={item.id} key={item.id}>
                                            {item.name}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor={"category"}>{translated.categoryLabel}:</label>
                                {errors.categoryId && (
                                    <span className="text-danger">{translated.categoryLabelError}</span>
                                )}
                            </div>
                        </form>
                    </Modal.Body>
                    <Modal.Footer>
                        <button className={"btn btn-secondary"} onClick={handleClose}>
                            Close
                        </button>
                        <button
                            className={"btn btn-secondary"}
                            onClick={handleSubmit(saveProduct)}
                            type={"submit"}
                            form={"product"}
                        >
                            {translated.saveChangesButton}
                        </button>
                    </Modal.Footer>
                </Modal>
            </div>
            <div className="w-50 p-3">
                <Outlet key={location.key}/>
            </div>
        </div>
    );
}

export default Products;
