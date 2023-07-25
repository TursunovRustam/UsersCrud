import { createBrowserRouter, Route, Routes } from "react-router-dom";
import Home from "./Home/Home.jsx";
import Login from "./Auth/Login/Login.jsx";
import Products from "./Products/Products.jsx";
import ProductAbout from "./Products/ProductAbout.jsx";
import SellingProduct from "./Selling/SellingProduct";
import Admin from "./Admin/Admin";
import Users from "./Admin/Users/Users";
import Category from "./Admin/Category/Category";
import SelectTill from "./Selling/SelectTill";
import Till from "./Admin/Till/Till";
import ErrorPage from "./Error/404";
import { useEffect } from "react";
import Navbar from "./Navbar/Navbar";
import { LanguageProvider } from "./Tools/LanguageContext";

function App() {
    useEffect(() => {
        !localStorage.getItem("lan") && localStorage.setItem("lan", "UZ");
    }, []);

    return (
        <LanguageProvider>
            <div className={"overflow-hidden 0"}>
                <Navbar />
                <Routes>
                    <Route path={"/"} element={<Home />} />
                    <Route path={"/404"} element={<ErrorPage />} />
                    <Route path={"/login"} element={<Login />} />
                    <Route path={"/products"} element={<Products />}>
                        <Route
                            path={"/products/about/:productId"}
                            element={<ProductAbout />}
                        />
                    </Route>
                    <Route path={"/product-selling"} element={<SellingProduct />} />
                    <Route path={"/product-selling/tills"} element={<SelectTill />} />
                    <Route path={"/admin"} element={<Admin />} />
                    <Route path={"/admin/users"} element={<Users />} />
                    <Route path={"/admin/categories"} element={<Category />} />
                    <Route path={"/admin/tills"} element={<Till />} />
                </Routes>
            </div>
        </LanguageProvider>
    );
}

export default App;
