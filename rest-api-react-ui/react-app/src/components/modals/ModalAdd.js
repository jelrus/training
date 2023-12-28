import React, {useState} from "react";
import api from "../../api/axiosConfig";

import {
    validateCertificate,
    clearErrors,
    catchErrors
} from "../utils/ValidationInputHandler";
import {addTag, addTags} from "../utils/TagHandler";
import {tooltip} from "../utils/TooltipHandler";

import certificates from '../../static/css/certificates.module.css'

import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import 'font-awesome/css/font-awesome.min.css';
import * as fontawesome from "@fortawesome/fontawesome-svg-core";
import {faPlus} from "@fortawesome/free-solid-svg-icons";

fontawesome.library.add(faPlus);

/**
 * ModalAdd is the component, used for creating and handling add new item modal screen
 *
 * @param {Object}errorLog
 * @param updateLog
 * @returns {JSX.Element}
 * @constructor
 */
const ModalAdd = ({errorLog, updateLog}) => {

    /**
     * Backend url, represents backend create endpoint
     *
     * @type {string}
     */
    const createUrl = "http://localhost:8080/gift/certificates/create"

    /**
     * State hook for certificate
     */
    const [certificate, setCertificate] = useState({
            name: '',
            description: '',
            duration: '',
            price: '',
            tags: []
        }
    );

    /**
     * Clears errors, checks if input is correct and tries to perform post request on backend, if response is valid
     * returns created object from backend, otherwise returns error which is caught to error log
     *
     * @param {Object}event
     * @returns {Promise<void>}
     */
    const handleAddCertificateRequest = async (event) => {
        event.preventDefault();
        clearErrors("Title", "Description", "Duration", "Price", "Tag", "");
        if (validateCertificate(certificate, "") > 0) return
        addTags(certificate);

        await api.post(
            createUrl, JSON.stringify(certificate),
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("user")).token
                }
            }
        ).then(() => {
            window.location.href = '/certificates';
        }).catch((e) => {
            const error = {
                id: errorLog.length + 1,
                status: e.response.status,
                message: e.response.data.errorMessage
            }

            catchErrors(e, "add-window-head");
            errorLog.push(error);
            updateLog(errorLog.filter(err => err));
        });
    }

    /**
     * Mouse event function which changes certificate object fields if input has been changed
     *
     * @param {Object}e
     */
    const handleChange = (e) => {
        const {name, value} = e.target;
        setCertificate({
            ...certificate,
            [name]: value,
        });
    };

    /**
     * Hides add new modal window, has pseudo close functionality
     */
    function hideAddNewWindow() {
        const windowWrapper = document.getElementById("addWindowWrapper");
        windowWrapper.style.display = "none";
        clearInput();
        clearErrors("Title", "Description", "Duration", "Price", "Tag", "");
    }

    /**
     * Clears all add new modal window input
     */
    function clearInput() {
        const titleInput = document.getElementById("title");
        const descriptionInput = document.getElementById("description");
        const durationInput = document.getElementById("duration");
        const priceInput = document.getElementById("price");
        const tagsInput = document.getElementById("tag-input");
        const tagsArea = document.getElementById("added-tags");

        titleInput.value = '';
        descriptionInput.value = '';
        durationInput.value = '';
        priceInput.value = '';
        tagsInput.value = '';

        certificate.name = '';
        certificate.description = '';
        certificate.duration = '';
        certificate.price = '';
        certificate.tags = [];

        tagsArea.replaceChildren();
    }

    /**
     * Creates JSX element of modal add new window
     */
    return (
        <div id="addWindowWrapper" className={certificates.addWindowWrapper}>
            <div className={certificates.addWindow}>
                <div id="add-window-head" className={certificates.addWindowHeader}>
                    <span>Add New Certificate</span>
                </div>

                <div className={certificates.addWindowFormWrapper}>
                    <div className={certificates.addWindowForm}>
                        <div id="title-area" className={certificates.fieldArea}>
                            <span className={certificates.fieldHead}>Title</span>

                            <label className={certificates.inputArea} data-tooltip-id="title-tooltip">
                                <input id="title" name="name" placeholder="Title..."
                                       type="text" onChange={handleChange}></input>
                            </label>

                            {tooltip(
                                "errorMessageTitle",
                                "title-tooltip",
                                "Title must be greater than 6 symbols or less than 30 symbols and shouldn't be blank"
                            )}
                        </div>

                        <div id="error-title" className="errorTitle"></div>

                        <div id="description-area" className={certificates.fieldArea}>
                            <span className={certificates.fieldHead}>Description</span>

                            <label className={certificates.inputArea} data-tooltip-id="description-tooltip">
                                <textarea id="description" name="description"
                                          placeholder="Description..." onChange={handleChange}></textarea>
                            </label>

                            {tooltip(
                                "errorMessageDescription",
                                "description-tooltip",
                                "Description must be greater than 12 symbols or less than 1000 symbols and shouldn't be blank"
                            )}
                        </div>

                        <div id="error-description" className="errorDescription"></div>

                        <div id="duration-area" className={certificates.fieldArea}>
                            <span className={certificates.fieldHead}>Duration</span>

                            <label className={certificates.inputArea} data-tooltip-id="duration-tooltip">
                                <input id="duration" name="duration" placeholder="Duration..."
                                       type="text" onChange={handleChange}></input>
                            </label>

                            {tooltip(
                                "errorMessageDuration",
                                "duration-tooltip",
                                "Duration must be greater than 0, be float and shouldn't be blank"
                            )}
                        </div>

                        <div id="error-duration" className="errorDuration"></div>

                        <div id="price-area" className={certificates.fieldArea}>
                            <span className={certificates.fieldHead}>Price</span>

                            <label className={certificates.inputArea} data-tooltip-id="price-tooltip">
                                <input id="price" name="price"
                                       placeholder="Price..." type="text" onChange={handleChange}></input>
                            </label>

                            {tooltip(
                                "errorMessagePrice",
                                "price-tooltip",
                                "Price must be greater than 0, be integer and shouldn't be blank"
                            )}
                        </div>

                        <div id="error-price" className="errorPrice"></div>

                        <div id="tags-area" className={certificates.tagArea}>
                            <div className={certificates.fieldArea}>
                                <span className={certificates.fieldHead}>Tags</span>

                                <label className={certificates.inputArea}>
                                    <input id="tag-input" placeholder="Enter tag name..."></input>
                                    <button
                                        onClick={
                                            () => addTag("added-tags",
                                                "tag-input",
                                                "tags-area")
                                        }>
                                        <FontAwesomeIcon icon="plus"></FontAwesomeIcon>
                                    </button>
                                </label>
                            </div>
                            <div id="added-tags" className={certificates.addedTags}></div>
                        </div>
                    </div>
                </div>

                <div className={certificates.addWindowButtonsWrapper}>
                    <div className={certificates.addWindowButtons}>
                        <button onClick={handleAddCertificateRequest}>Save</button>
                        <button onClick={hideAddNewWindow}>Cancel</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ModalAdd;