import layout from "../../static/css/layout.module.css";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import React from "react";

/**
 * NavbarAuthorization is the component which handles component's rendering for authorization based on JWT token
 * storage in local storage
 *
 * @returns {JSX.Element}
 * @constructor
 */
const NavbarAuthorization = () => {

    /**
     * Mouse event on click function, clears local storage and redirects to login page
     */
    const logout = () => {
        localStorage.clear();
        document.location.href = '/login';
    }

    /**
     * Renders parts of the navigation bar based on user stored previously in local storage:
     * if user is logged it will render buttons with profile link and logout link,
     * otherwise will render login and sign up buttons
     */
    if (localStorage.getItem("user") === null) {
        return (
            <ul className={layout.navBar}>
                <li className={layout.navSideMenu}>
                    <a href="/">Admin UI</a>
                </li>

                <li id="login" className={layout.navLogin}>
                    <a href="/login">Login</a>
                </li>

                <li id="signup" className={layout.navSignup}>
                    <a href="/signup">Sign Up</a>
                </li>
            </ul>
        )
    } else {
        const username = JSON.parse(localStorage.getItem("user")).username;

        return (
            <ul className={layout.navBar}>
                <li className={layout.navSideMenu}>
                    <a href="/">Admin UI</a>
                </li>

                <li id="login" className={layout.navLogin}>
                    <a href="#">
                        <FontAwesomeIcon icon="user"></FontAwesomeIcon>
                        <span>{username}</span>
                    </a>
                </li>

                <li id="signout" className={layout.navSignup}>
                    <a href="/login" onClick={logout}>Log Out</a>
                </li>
            </ul>
        );
    }
}

export default NavbarAuthorization;