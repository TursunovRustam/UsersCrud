import React, { useState, useRef, useEffect } from 'react';

const EditableField = ({ initialValue, saveFunction, type,name, ...inputProps }) => {
    const [value, setValue] = useState(initialValue);
    const [isEditing, setIsEditing] = useState(false);
    const inputRef = useRef(null)
    const handleDoubleClick = () => {
        setIsEditing(true);
    };

    const handleBlur = () => {
        setIsEditing(false);
        saveToDatabase(value, type);
    };

    const handleChange = (e) => {
        setValue(e.target.value);
    };

    const saveToDatabase = (updatedValue, type) => {
        saveFunction(updatedValue, type);
    };

    useEffect(() => {
        const handleClickOutside = (event) => {
            if (inputRef.current && !inputRef.current.contains(event.target)) {
                setIsEditing(false);
                saveToDatabase(value, type);
            }
        };

        document.addEventListener('mousedown', handleClickOutside);

        return () => {
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }, [value]);

    return (
        <div>
            {isEditing ? (
                <form onSubmit={handleBlur}>
                    <input
                        className="form-control"
                        type="text"
                        value={value}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        autoFocus
                        ref={inputRef}
                        {...inputProps}
                    />
                </form>
            ) : (
                <h1 onDoubleClick={handleDoubleClick} className={"text"}>{name}: {value}</h1>
            )}
        </div>
    );
};

export default EditableField;