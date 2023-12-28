import {Outlet} from "react-router-dom";

import React from "react";
import layout from '../../static/css/layout.module.css'

import 'font-awesome/css/font-awesome.min.css';
import * as fontawesome from "@fortawesome/fontawesome-svg-core";
import {faUser} from "@fortawesome/free-solid-svg-icons";
import NavbarAuthorization from "../utils/NavbarAuthorization";

fontawesome.library.add(faUser);

/**
 * ListLayout is the layout component, used for creating layout for pages with listing
 *
 * @returns {JSX.Element}
 * @constructor
 */
const ListLayout = () => {

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
     * Creates JSX element of list layout
     */
    return (
        <div className={layout.body}>
            <NavbarAuthorization></NavbarAuthorization>

            <div className={layout.screenList}>
                <Outlet/>
            </div>

            <div className={layout.footer}></div>
        </div>
    );
}

export default ListLayout;