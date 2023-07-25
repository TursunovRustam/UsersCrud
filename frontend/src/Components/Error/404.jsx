import React from 'react';
import ErrorGif from './404-error-page-3959253-3299952.webp'

function ErrorPage(props)
{
    return (
        <div className={"d-flex justify-content-center align-items-center"}>
            <img src={ErrorGif} alt="error gif" style={{width:"100vw", height:"100vh"}}/>
        </div>
    );
}

export default ErrorPage;