import React, {useState} from "react";
import api from '../../api/axiosConfig';
import {jwtDecode} from 'jwt-decode'
import {tooltip} from "../utils/TooltipHandler";
import {renderError, clearErrors, validateLogin} from '../utils/ValidationInputHandler'

import login from '../../static/css/login.module.css'
import image from '../../static/resources/logo-small.svg'

import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import 'font-awesome/css/font-awesome.min.css';
import * as fontawesome from "@fortawesome/fontawesome-svg-core";
import {faUser, faLock} from "@fortawesome/free-solid-svg-icons";

fontawesome.library.add(faUser, faLock);

/**
 * LoginPage is the component which handles all functionality for login page
 *
 * @returns {JSX.Element}
 * @constructor
 */
const LoginPage = () => {

    /**
     * Backend url, represents backend login endpoint
     *
     * @type {string}
     */
    const loginUrl = "http://localhost:8080/login"

    /**
     * State hook for user
     */
    const [user, setUser] = useState(
        {
            username: document.getElementById("username"),
            password: document.getElementById("password")
        }
    );

    /**
     * Tries to perform post request on backend, if response is valid returns object with JWT token from backend,
     * token is stored into local storage, otherwise renders error
     *
     * @param event
     * @returns {Promise<void>}
     */
    const handleLoginRequest = async (event) => {
        event.preventDefault();
        clearErrors("Username", "Password", "");
        validateLogin(user);

        await api.post(
            loginUrl, JSON.stringify(user), {headers: {'Content-Type': 'application/json'}}
        ).then(res => {
                const token = res.data.token;
                const tokenDecoded = jwtDecode(res.data.token);
                const proxyUser = {
                    username: tokenDecoded.username,
                    token: token
                }

                localStorage.setItem("user", JSON.stringify(proxyUser))
                window.location.href = '/certificates';
            }
        ).catch(function (error) {
            if (error.response.status === 404) {
                const head = document.getElementById("formHead");
                renderError(head, "", "center", "Login or password is not found");
            }
        });
    }

    /**
     * Mouse event function which changes user object fields if input has been changed
     *
     * @param {Object}e
     */
    const handleChange = (e) => {
        const {name, value} = e.target;
        setUser({
            ...user,
            [name]: value,
        });
    };

    /**
     * Creates JSX element of login window
     */
    return (
        <div className={login.loginFormWrapper}>
            <div className={login.loginForm}>
                <div className={login.formLogo}>
                    <img src={image} alt=""></img>
                </div>

                <div id="formHead" className={login.formHead}>
                    <span>Login</span>
                </div>

                <div className={login.loginArea}>
                    <div className={login.formInputHeadLogin}>
                        <FontAwesomeIcon icon="user" className={login.icon}/>
                        <span>Login</span>
                    </div>

                    <label id="usernameArea" className={login.formInputAreaLogin} data-tooltip-id="username-tooltip">
                        <input id="username" className={login.formInputAreaInput} name="username"
                               type="text" placeholder="Type your login"
                               minLength={3} maxLength={30}
                               onChange={handleChange}>
                        </input>
                    </label>

                    {tooltip(
                        "errorMessageUsername",
                        "username-tooltip",
                        "Username must be greater than 3 symbols or less than 30 symbols and shouldn't be blank"
                    )}
                </div>

                <div className={login.passwordArea}>
                    <div className={login.formInputHeadPassword}>
                        <FontAwesomeIcon icon="lock" className={login.icon}/>
                        <span>Password</span>
                    </div>

                    <label id="passwordArea" className={login.formInputAreaPassword} data-tooltip-id="password-tooltip">
                        <input id="password" className={login.formInputAreaInput} name="password"
                               type="password" placeholder="Type your password"
                               minLength={4} maxLength={30}
                               onChange={handleChange}>
                        </input>
                    </label>

                    <div className={login.formPasswordRecover}>
                        <a href="#">Forgot password?</a>
                    </div>

                    {tooltip(
                        "errorMessagePassword",
                        "password-tooltip",
                        "Password must be greater than 4 symbols or less than 30 symbols and shouldn't be blank"
                    )}
                </div>

                <div className={login.formButtonArea}>
                    <button className={login.formLoginBtn} onClick={handleLoginRequest}>LOGIN</button>
                </div>

                <div className={login.formSignUp}>
                    <span>or</span>
                    <a href="/signup">SIGN UP</a>
                </div>
            </div>
        </div>
    );
}
export default LoginPage;