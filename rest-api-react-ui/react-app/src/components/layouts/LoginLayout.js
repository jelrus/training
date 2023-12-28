import {Outlet} from "react-router-dom";

import React from "react";
import layout from '../../static/css/layout.module.css'

/**
 * LoginLayout is the layout component, used for creating layout for pages with login form
 *
 * @returns {JSX.Element}
 * @constructor
 */
const LoginLayout = () => {

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
     * Creates JSX element of login layout
     */
    return (
        <div className={layout.body}>
            <ul className={layout.navBar}>
                <li className={layout.navSideMenu}>
                    <a href="/">Admin UI</a>
                </li>
            </ul>

            <div className={layout.screenForm}>
                <Outlet/>
            </div>

            <div className={layout.footer}></div>
        </div>
    );
}

export default LoginLayout;