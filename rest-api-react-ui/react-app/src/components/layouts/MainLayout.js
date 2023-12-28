import {Outlet} from "react-router-dom";

import React from "react";
import layout from '../../static/css/layout.module.css'
import NavbarAuthorization from "../utils/NavbarAuthorization";

/**
 * MainLayout is the layout component, used for creating base layout for pages
 *
 * @returns {JSX.Element}
 * @constructor
 */
const MainLayout = () => {

    /**
     * Render body wrapper call
     */
    renderBodyWrapper();

    /**
     * Renders body wrapper element
     */
    function renderBodyWrapper() {
        const root = document.getElementById("root");
        root.className = "bodyWrapper";
        root.style.width = "100%";
        root.style.height = "100%";

    }

    /**
     * Creates JSX element of main layout
     */
    return (
        <div className={layout.body}>
            <NavbarAuthorization></NavbarAuthorization>

            <div className={layout.screenForm}>
                <Outlet/>
            </div>

            <div className={layout.footer}></div>
        </div>
    );
}

export default MainLayout;