import React, { useEffect, useState } from "react";
import Req from "../Tools/Req";
import EditableField from "../Tools/EditableField";
import QRCode from "react-qr-code";
import { useLanguage } from "../Tools/LanguageContext";
import languageJson from "../Tools/language.json";

function ProductAbout() {
    const currentProduct = JSON.parse(localStorage.getItem("product"));
    const [postCardData, setPostCardData] = useState({
        img: "",
        name: "",
        code: "",
        price: "",
        incomePrice: "",
        balance: "",
    });
    const { language } = useLanguage();
    const [translated, setTranslated] = useState(null);

    useEffect(() => {
        if (language === "UZ") {
            setTranslated(languageJson.productAbout.uz);
        } else if (language === "RU") {
            setTranslated(languageJson.productAbout.ru);
        } else {
            alert("Bunday til topilmadi");
        }
    }, [language]);

    useEffect(() => {
        if (translated) {
            getProductCard();
        }
    }, [translated]);

    const saveToDatabase = (updatedValue, type) => {
        console.log(updatedValue)
        console.log(type)
        const updatedProduct = { ...postCardData, [type]: updatedValue };
        Req("/product", "PATCH", updatedProduct)
            .then((res) => {
                console.log("Product updated:", updatedProduct);
                getProductCard();
            })
            .catch((error) => {
                console.error("Failed to update product:", error);
            });
    };

    function getProductCard() {
        Req(`/product/${currentProduct.id}`, "GET")
            .then((res) => {
                const product = res.data;
                console.log(product)
                Req(`/avatarImg/${product.img_id}`, "GET")
                    .then((imgRes) => {
                        setPostCardData({
                            img: imgRes.data.imgUrl,
                            name: product.name,
                            code: product.code,
                            price: product.price,
                            incomePrice: product.income_price,
                            balance: product.balance,
                        });
                    })
                    .catch((error) => {
                        console.error("Failed to fetch product image:", error);
                    });
            });
    }

    function changePhoto(file) {
        const product = JSON.parse(localStorage.getItem("product"));
        let reader = new FileReader();
        reader.readAsDataURL(file);
        let formData = new FormData();
        formData.append("file", file);

        Req(`/avatarImg/${product.imgUrl.id}`, "PUT", formData)
            .then((res) => {
                setPostCardData({ ...postCardData, img: res.data });
                getProductCard();
            });
    }

    if (!translated) {
        return null; 
    }

    return (
        <div className="bg-secondary">
            <div className="p-5">
                <div className="d-flex justify-content-between">
                    <div>
                        <h1 className={"text-center"}>{translated.image}</h1>
                        <label>
                            <img src={`data:image/jpg;base64,${postCardData.img}`} alt="img" style={{ width: "200px", height:"200px", objectFit:"cover" }} />
                            <input type="file" className={"d-none"} onChange={(e) => changePhoto(e.target.files[0])} />
                        </label>
                    </div>
                    <div>
                        <h1 className={"text-center"}>QR code</h1>
                        <QRCode
                            size={256}
                            style={{ height: "auto", width: "200px" }}
                            value={postCardData.code}
                            viewBox={`0 0 256 256`}
                        />
                    </div>
                </div>
                <div className="py-3">
                    {postCardData.name !== "" && (
                        <EditableField
                            initialValue={postCardData.name}
                            saveFunction={saveToDatabase}
                            type="name"
                            name={translated.name}
                            placeholder={translated.name}
                        />
                    )}
                </div>
                <div className="py-3">
                    {postCardData.price !== "" && (
                        <EditableField
                            initialValue={postCardData.price}
                            saveFunction={saveToDatabase}
                            type="price"
                            name={translated.price}
                            placeholder={translated.price}
                        />
                    )}
                </div>
                <div className="py-3">
                    {postCardData.incomePrice !== "" && (
                        <EditableField
                            initialValue={postCardData.incomePrice}
                            saveFunction={saveToDatabase}
                            type="incomePrice"
                            name={translated.incomePrice}
                            placeholder={translated.incomePrice}
                        />
                    )}
                </div>
                <div className="py-3 input-group">
                    {postCardData.balance !== "" && (
                        <EditableField
                            initialValue={postCardData.balance}
                            saveFunction={saveToDatabase}
                            type="balance"
                            name={translated.balance}
                            placeholder={translated.balance}
                        />
                    )}
                </div>
            </div>
        </div>
    );
}

export default ProductAbout;
