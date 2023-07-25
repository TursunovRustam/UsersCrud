import React, { useEffect, useRef, useState } from "react";
import Req from "../Tools/Req.js";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { QrReader } from "react-qr-reader";
import { wait } from "@testing-library/user-event/dist/utils";
import SockJsClient from "react-stomp";
import { useLanguage } from "../Tools/LanguageContext";
import languageJson from "../Tools/language.json";
import { v4 as uuidv4 } from 'uuid';

function SellingProduct() {
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [filterObj, setFilterObj] = useState({
    categoryId: "",
    search: "",
  });
  const [selectedTill, setSelectedTill] = useState({});
  const [orders, setOrders] = useState([]);
  const [currentOrder, setCurrentOrder] = useState({});
  const [translated, setTranslated] = useState(null);
  const [selected, setSelected] = useState({})
  const { register } = useForm();
  const currentOrderRef = useRef();
  const navigate = useNavigate();
  const { language } = useLanguage();

  useEffect(() => {
    disableRefresh();
    getCategories();
    checkTill();
    getProducts();
    getOrders();
  }, []);

  useEffect(() => {
    if (translated) {
      getProductsByFilter();
    }
  }, [filterObj]);

  useEffect(() => {
    if (language === "UZ") {
      setTranslated(languageJson.sellingProduct.uz);
    } else if (language === "RU") {
      setTranslated(languageJson.sellingProduct.ru);
    } else {
      alert("language is not present :(");
    }
  }, [language]);

  const handleScan = (data) => {
    if (data) {
      Req("/qrcode/" + data.text, "GET", null)
        .then((res) => {
          addToBasket(res.data); // Pass currentOrder as a parameter
        })
        .catch((err) => console.error(err.toString()));
    }
  };

  const handleError = (error) => {
    console.error(error);
  };

  function getOrders() {
    if (localStorage.getItem("orders")) {
      setOrders(JSON.parse(localStorage.getItem("orders")));
    } else {
      localStorage.setItem("orders", "[]");
    }
  }

  function getCategories() {
    Req("/category", "GET", null)
      .then((res) => {
        setCategories(res.data);
      })
      .catch((err) => {
        navigate("/login");
      });
  }

  function checkTill() {
    Req("/till/" + localStorage.getItem("till"), "GET", null)
      .then((res) => {
        if (res.data === null) {
          navigate("/product-selling/tills");
        } else {
          setSelectedTill(res.data);
        }
      })
      .catch((err) => {
        navigate("/product-selling/tills");
      });
  }

  function disableRefresh() {
    const handleBeforeUnload = (event) => {
      event.preventDefault();
      event.returnValue = "";
    };

    window.addEventListener("beforeunload", handleBeforeUnload);

    return () => {
      window.removeEventListener("beforeunload", handleBeforeUnload);
    };
  }

  function getProducts() {
    Req("/product/getAllCards", "GET", null).then((res) => {
      console.log(res.data)
      setProducts(res.data);
    });
  }

  function addToBasket(item) {
    if (currentOrderRef.current !== undefined) {
      wait(1500);
      Req(`/avatarImg/${item.imgUrl.id}`, "GET").then((imgRes) => {
        setCurrentOrder((prevOrder) => {
          const updatedOrder = { ...prevOrder };
          updatedOrder.products = updatedOrder.products || []; // Initialize products array if undefined
          const itemIndex = updatedOrder.products.findIndex(
            (product) => product.id === item.id
          );
          if (
            itemIndex !== -1 &&
            parseInt(updatedOrder.products[itemIndex].count) <
              updatedOrder.products[itemIndex].balance
          ) {
            updatedOrder.products[itemIndex].count =
              parseInt(updatedOrder.products[itemIndex].count) + 1;
          } else if (item.balance > 0 && itemIndex === -1) {
            const product = {
              ...item,
              imgUrl: imgRes.data.imgUrl,
              count: 1,
            };
            updatedOrder.products.push(product);
          } else {
            alert(translated.productIsNotPresent);
          }
          return updatedOrder;
        });
      });
    } else {
      alert(translated.chooseUser);
    }
  }

  function getSellingProducts(order) {
   setSelected({
     orderId:order.id,
     selected:true
   })
    setCurrentOrder({ ...order });
    currentOrderRef.current = order;
  }

  function addCheck() {
    const id = uuidv4();
    orders.push({
      id,
      products: [],
    });
    setOrders([...orders]);
    localStorage.setItem("orders", JSON.stringify(orders));
  }

  function changeCount(count, index) {
    if (count > 0) {
      if (currentOrder.products[index].balance >= count) {
        currentOrder.products[index].count = count;
        setCurrentOrder({ ...currentOrder });
      }
    }
  }

  function saveOrder() {
    let expenseProducts = [];
    console.log(currentOrder)
    currentOrder.products.map((item) => {
      if (item.count > 0) {
        expenseProducts.push({
          id: null,
          product: {
            id: item.id,
            name: item.name,
            category: {
              id: item.categoryId,
              name: item.categoryName,
            },
            code: item.code,
            price: item.price,
            imgUrl: {
              id: item.id,
              imgUrl: item.imgUrl,
            },
          },
          count: item.count,
          createdAt: null,
        });
      }
    });

    Req("/expenseProduct", "POST", expenseProducts).then((res) => {
      console.log(orders.indexOf(currentOrder));
      console.log(currentOrder);

      const index = orders.findIndex(order => order.id === currentOrder.id);

      if (index !== -1) {
        orders.splice(index, 1);

        setOrders([...orders]);

        localStorage.setItem("orders", JSON.stringify(orders)); // Save the updated orders to local storage
      }

      setCurrentOrder({});
      getProducts();
    });
  }

  function changeFilter(value, key) {
    const updatedFilterObj = { ...filterObj, [key]: value };
    setFilterObj(updatedFilterObj);
  }

  function removeFromBasket(item) {
    currentOrder.products.splice(currentOrder.products.indexOf(item), 1);
    setCurrentOrder(currentOrder);
    setOrders((prevOrders) => {
      return prevOrders.map((order) =>
        order.id === currentOrder.id ? currentOrder : order
      );
    });
    const updatedOrders = orders.map((order) =>
      order.id === currentOrder.id ? currentOrder : order
    );
    localStorage.setItem("orders", JSON.stringify(updatedOrders));
  }

  function removeOrder(item) {
    orders.splice(orders.indexOf(item), 1);
    setOrders(orders);
    localStorage.setItem("orders", JSON.stringify(orders));
    setCurrentOrder({});
    getOrders();
  }

  function previousPage() {
    Req("/till", "POST", { ...selectedTill, selected: false }).then((res) => {
      localStorage.removeItem("till");
      navigate("/");
    });
  }

  function getProductsByFilter() {
    Req("/product/getByFilter", "PATCH", filterObj)
        .then((res) => {
          setProducts(res.data);
        });
  }

  function changeFilter(value, key) {
    console.log(value)
    const updatedFilterObj = {...filterObj, [key]: value};
    setFilterObj(updatedFilterObj);
  }

  if (!translated) {
    return null;
  }

  return (
    <div className={"d-flex"}>
      <div className="w-50 p-3">
        <div className="d-flex">
          <button
            className="btn btn-danger mx-2"
            style={{ borderRadius: "25%", height: "40px" }}
            onClick={() => navigate("/")}
          >
            {translated.back}
          </button>
          <div className="w-25 h-25 border border-black">
            <QrReader
              delay={300}
              onError={handleError}
              onResult={handleScan}
              facingMode="user"
              style={{ width: "100%", height: "100%" }}
            />
          </div>
        </div>
        <div className="d-flex py-3">
          <select
            className={"form-select w-100"}
            {...register("categoryId")}
            onChange={(e) => changeFilter(e.target.value, "categoryId")}
          >
            <option value="" selected={true} disabled={true}>
              {translated.chooseCategory}
            </option>
            {categories.map((item) => (
              <option value={item.id} key={item.id}>
                {item.name}
              </option>
            ))}
          </select>
          <input
            type="text"
            className="form-control"
            placeholder={translated.productName}
            onChange={(e) => {
              changeFilter(e.target.value, "search");
            }}
          />
        </div>

        <div
          className={"text-center bg-dark"}
          style={{ height: "450px", overflowY: "auto" }}
        >
          {products.map((item) => (
            <button
              key={item.id}
              className="btn btn-outline-light w-75 my-3"
              style={{ height: "50px" }}
              onClick={() => addToBasket(item)}
              disabled={
                currentOrder.products &&
                currentOrder.products.some((product) => product.id === item.id)
              }
            >
              {item.name}
            </button>
          ))}
        </div>
      </div>
      <div className="w-50 p-3 bg-secondary h-100">

        <div className="d-flex justify-content-end " style={{maxHeight: "200px"}}>
          <div>
            <button className="btn btn-success" style={{width:"140px", height:"40px"}} onClick={addCheck}>
              {translated.newQueue}
            </button>
          </div>
          <div className="btn-group overflow-auto">
            {orders.map((item, index) => (
              <div className={"btn-group mx-1"}>
                <button
                    style={{width:"130px", height:"40px"}}
                  className={
                    selected.orderId===item.id
                      ? "btn btn-primary"
                      : "btn btn-dark"
                  }
                  disabled={currentOrder.id && currentOrder.id === item.id}
                  onClick={() => getSellingProducts(item)}
                >
                  {translated.user} {index + 1}
                </button>
                <button
                    style={{height:"40px"}}
                  className={"btn btn-danger"}
                  onClick={() => removeOrder(item)}
                >
                  x
                </button>
              </div>
            ))}
          </div>
        </div>

        <div
          className="w-100 bg-secondary"
          style={{ height: "600px", overflowY: "auto" }}
        >
          {currentOrder !== {} &&
            currentOrder.products &&
            currentOrder.products.map((item, index) => (
              <div className="d-flex align-items-center">
                <img
                  src={"data:image/jpg;base64," + item.imgUrl}
                  alt="img"
                  className="w-25 m-2"
                />
                <div className="my-3">
                  <h3>
                    {translated.productName}: {item.name}
                  </h3>
                  <h5>
                    {translated.productPrice}: {item.price * item.count}
                  </h5>
                  <input
                    type="number"
                    className={"form-control w-50"}
                    value={item.count}
                    onChange={(e) => changeCount(e.target.value, index)}
                    placeholder={translated.productCount}
                  />
                  <button
                    className="btn btn-danger my-2"
                    onClick={() => removeFromBasket(item)}
                  >
                    {translated.remove}
                  </button>
                </div>
              </div>
            ))}
        </div>

        <div className="btn-group">
          <button className="btn btn-outline-dark" onClick={saveOrder}>
            {translated.finish}
          </button>
        </div>
      </div>
    </div>
  );
}

export default SellingProduct;
