import React, { useEffect, useState } from "react";
import { Modal } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { useLanguage } from "../../Tools/LanguageContext";
import languageJson from "../../Tools/language.json";
import {  useDispatch, useSelector } from "react-redux";
import {getCategories, toggleShowModal, saveCategory, setText} from '../../../Redux/store/Admin/Category/category.slice'


function Category(props) {
  const dispatch = useDispatch();
  const categories = useSelector((state) => state.category.categories);
  const showCategoryModal = useSelector((state) => state.category.categoryModal);
  const categoryName = useSelector((state) => state.category.categoryName);
  const navigate = useNavigate();

  useEffect(() => {
    dispatch(getCategories());
  }, [dispatch]);

  const { language } = useLanguage();
  const [translated, setTranslated] = useState(null);

  useEffect(() => {
    if (language === "UZ") {
      setTranslated(languageJson.category.uz);
    } else if (language === "RU") {
      setTranslated(languageJson.category.ru);
    } else {
      alert("Bunday til topilmadi");
    }
  }, [language]);

  if (!translated) {
    return null;
  }

  return (
    <div>
      <div className="p-5">
        <button
          className="btn btn-danger mx-2"
          style={{ borderRadius: "25%" }}
          onClick={() => navigate("/admin")}
        >
          {translated.backButton}
        </button>
        <button
          className="btn btn-outline-secondary"
          onClick={() => dispatch(toggleShowModal(true))}
        >
          {translated.newCategory}
        </button>

        <table className="table">
          <thead>
            <tr>
              <th>{translated.categoryName}</th>
            </tr>
          </thead>
          <tbody>
       

           {categories.map((item) => (
              <tr key={item.id}>
                <td>{item.name}</td>
              </tr>
            ))}
          </tbody>
        </table>
        <Modal show={showCategoryModal} onHide={()=>dispatch(toggleShowModal(false))} centered={true}>
          <Modal.Header closeButton>
            <Modal.Title>{translated.newCategory}</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <form id={"category"}>
              <input
                type="text"
                className={`form-control my-3`}
                placeholder={translated.categoryName}
                value={categoryName}
                onChange={(e)=>dispatch(setText(e.target.value))}
                autoFocus={true}
              />
            </form>
          </Modal.Body>
          <Modal.Footer>
            <button className={"btn btn-secondary"} onClick={()=>dispatch(toggleShowModal(false))}>
              {translated.closeButton}
            </button>
            <button
              className={"btn btn-secondary"}
              onClick={()=>dispatch(saveCategory(categoryName))}
              type={"submit"}
              form={"category"}
            >
              {translated.saveChangesButton}
            </button>
          </Modal.Footer>
        </Modal>
      </div>
    </div>
  );
}

export default Category;
