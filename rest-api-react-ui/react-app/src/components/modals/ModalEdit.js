import React, {useEffect, useState} from "react";
import api from "../../api/axiosConfig";

import {
    validateCertificate,
    clearErrors, catchErrors
} from "../utils/ValidationInputHandler";
import {addTag, addTags, appendTag} from "../utils/TagHandler";
import {tooltip} from "../utils/TooltipHandler";

import certificates from '../../static/css/certificates.module.css'

import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import 'font-awesome/css/font-awesome.min.css';
import * as fontawesome from "@fortawesome/fontawesome-svg-core";
import {faPlus, faCircleDown, faCircleXmark} from "@fortawesome/free-solid-svg-icons";
import {Tooltip} from "react-tooltip";

fontawesome.library.add(faPlus, faCircleDown, faCircleXmark);

/**
 * ModalEdit is the component, used for creating and handling edit item modal screen
 *
 * @param {Number}id
 * @param {Object}errorLog
 * @returns {JSX.Element}
 * @constructor
 */
const ModalEdit = ({id, errorLog, updateLog}) => {

    /**
     * Backend url, represents backend update endpoint
     *
     * @type {string}
     */
    const editUrl = "http://localhost:8080/gift/certificates/" + id + "/update";

    /**
     * State hook for pre updated certificate state
     */
    const [prevCert, setPrevCert] = useState('');

    /**
     * State hook for to update certificate state
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
     * Tries to perform get request on backend, if response is valid returns pre updated object from backend,
     * otherwise returns error which is caught to error log
     *
     * @returns {Promise<void>}
     */
    const findCertificate = async () => {
        try {
            const response = await api.get('/gift/certificates/' + id);
            setPrevCert(response.data);
        } catch (e) {
            if (e.response !== null && e.response !== undefined) {
                const error = {
                    id: errorLog.length + 1,
                    status: e.response.status,
                    message: e.response.data.errorMessage
                }

                errorLog.push(error);
                updateLog(errorLog.filter(err => err));
            }
        }
    }

    /**
     * Effect hook for pre updated certificate
     */
    useEffect(() => {
        findCertificate().catch((e) => {
            if (e.response !== null && e.response !== undefined) {
                const error = {
                    id: errorLog.length + 1,
                    status: e.response.status,
                    message: e.response.data.errorMessage
                }

                errorLog.push(error);
            }
        })
    }, [id])

    /**
     * Imports pre updated certificate's fields values into edit modal window input elements
     */
    const importPrevState = () => {
        const titleInput = document.getElementById("edit-title");
        const descriptionInput = document.getElementById("edit-description")
        const durationInput = document.getElementById("edit-duration")
        const priceInput = document.getElementById("edit-price")

        titleInput.value = prevCert.name;
        descriptionInput.value = prevCert.description;
        durationInput.value = prevCert.duration;
        priceInput.value = prevCert.price;

        prevCert.tags?.map(t => {
            appendTag(t, "edited-tags");
        })

        copyCertFromInput();
    }

    /**
     * Copies certificate fields from input
     */
    function copyCertFromInput() {
        certificate.name = document.getElementById("edit-title").value;
        certificate.description = document.getElementById("edit-description").value;
        certificate.duration = document.getElementById("edit-duration").value;
        certificate.price = document.getElementById("edit-price").value;
    }

    /**
     * Clears edit modal window fields
     */
    const clearWindow = () => {
        const titleInput = document.getElementById("edit-title");
        const descriptionInput = document.getElementById("edit-description");
        const durationInput = document.getElementById("edit-duration");
        const priceInput = document.getElementById("edit-price");
        const tags = document.getElementById("edited-tags");

        titleInput.value = '';
        descriptionInput.value = '';
        durationInput.value = '';
        priceInput.value = '';
        tags.replaceChildren();

        certificate.name = '';
        certificate.description = '';
        certificate.duration = '';
        certificate.price = '';
        certificate.tags = [];
    }

    /**
     * Hides edit modal window, has pseudo close functionality
     */
    function hideEditNewWindow() {
        const windowWrapper = document.getElementById("editWindowWrapper");
        windowWrapper.style.display = "none";
        clearErrors("Title", "Description", "Duration", "Price", "Tag", "");
        clearWindow();
    }

    /**
     * Tries to perform put request on backend, if response is valid returns updated object from backend,
     * otherwise returns error which is caught to error log
     *
     * @param {Object}event
     * @returns {Promise<void>}
     */
    const handleEditCertificateRequest = async (event) => {
        event.preventDefault();
        clearErrors("Title", "Description", "Duration", "Price", "Tag", "");

        if (validateCertificate(certificate, "edit-") > 0) return;

        addTags(certificate);

        await api.put(
            editUrl, JSON.stringify(certificate),
            {headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("user")).token
                }}
        ).then(() => {
            window.location.href = '/certificates'
        }).catch((e) => {
            if (e.response !== null && e.response !== undefined) {
                const error = {
                    id: errorLog.length + 1,
                    status: e.response.status,
                    message: e.response.data.errorMessage
                }

                errorLog.push(error);
                updateLog(errorLog.filter(err => err));
                catchErrors(e, "edit-window-head");
            }
        });
    }

    /**
     * Mouse event function which changes to update certificate object fields if input has been changed
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
     * Creates JSX element of edit modal window
     */
    return (
        <div id="editWindowWrapper" className={certificates.addWindowWrapper}>
            <div className={certificates.addWindow}>
                <div id="edit-window-head" className={certificates.editWindowHeader}>
                    <span>
                        <div className={certificates.editWindowTitle}>Edit Certificate (ID: {id})</div>

                        <button className={certificates.importButton} data-tooltip-id="import-state"
                                onClick={importPrevState}>
                            <label>
                                 <FontAwesomeIcon icon={faCircleDown}></FontAwesomeIcon>
                            </label>
                        </button>

                        <button className={certificates.clearButton} data-tooltip-id="clear-form"
                                onClick={clearWindow}>
                            <label>
                                 <FontAwesomeIcon icon={faCircleXmark}></FontAwesomeIcon>
                            </label>
                        </button>

                        <Tooltip id="import-state">Import</Tooltip>

                        <Tooltip id="clear-form">Clear form</Tooltip>
                    </span>
                </div>

                <div className={certificates.addWindowFormWrapper}>
                    <div className={certificates.addWindowForm}>
                        <div id="edit-title-area" className={certificates.fieldArea}>
                            <span className={certificates.fieldHead}>
                                Title
                            </span>

                            <label className={certificates.inputArea} data-tooltip-id="edit-title-tooltip">
                                <input id="edit-title" name="name" placeholder="Title..."
                                       type="text" onChange={handleChange}></input>
                            </label>

                            {tooltip(
                                "errorMessageTitle",
                                "edit-title-tooltip",
                                "Title must be greater than 6 symbols or less than 30 symbols and shouldn't be blank"
                            )}
                        </div>

                        <div id="edit-error-title" className="errorTitle"></div>

                        <div id="edit-description-area" className={certificates.fieldArea}>
                            <span className={certificates.fieldHead}>Description</span>

                            <label className={certificates.inputArea} data-tooltip-id="edit-description-tooltip">
                                <textarea id="edit-description" name="description"
                                          placeholder="Description..." onChange={handleChange}>
                                </textarea>
                            </label>

                            {tooltip(
                                "errorMessageDescription",
                                "edit-description-tooltip",
                                "Description must be greater than 12 symbols or less than 1000 symbols and shouldn't be blank"
                            )}
                        </div>

                        <div id="edit-error-description" className="errorDescription"></div>

                        <div id="edit-duration-area" className={certificates.fieldArea}>
                            <span className={certificates.fieldHead}>Duration</span>

                            <label className={certificates.inputArea} data-tooltip-id="edit-duration-tooltip">
                                <input id="edit-duration" name="duration"  placeholder="Duration..."
                                       type="text" onChange={handleChange}></input>
                            </label>

                            {tooltip(
                                "errorMessageDuration",
                                "edit-duration-tooltip",
                                "Duration must be greater than 0, be integer and shouldn't be blank"
                            )}
                        </div>

                        <div id="edit-error-duration" className="errorDuration"></div>

                        <div id="edit-price-area" className={certificates.fieldArea}>
                            <span className={certificates.fieldHead}>Price</span>

                            <label className={certificates.inputArea} data-tooltip-id="edit-price-tooltip">
                                <input id="edit-price" name="price" placeholder="Price..."
                                       type="text" onChange={handleChange}></input>
                            </label>

                            {tooltip(
                                "errorMessagePrice",
                                "edit-price-tooltip",
                                "Price must be greater than 0, be float  and shouldn't be blank"
                            )}
                        </div>

                        <div id="edit-error-price" className="errorPrice"></div>

                        <div id="edit-tags-area" className={certificates.tagArea}>
                            <div className={certificates.fieldArea}>
                                <span className={certificates.fieldHead}>Tags</span>

                                <label className={certificates.inputArea}>
                                    <input id="edit-tag-input" placeholder="Enter tag name..."></input>
                                    <button
                                        onClick={
                                        () => addTag("edited-tags",
                                                             "edit-tag-input",
                                                             "edit-tags-area")}>
                                        <FontAwesomeIcon icon="plus"></FontAwesomeIcon>
                                    </button>
                                </label>
                            </div>
                            <div id="edited-tags" className={certificates.addedTags}></div>
                        </div>
                    </div>
                </div>

                <div className={certificates.addWindowButtonsWrapper}>
                    <div className={certificates.addWindowButtons}>
                        <button onClick={handleEditCertificateRequest}>Save</button>
                        <button onClick={hideEditNewWindow}>Cancel</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ModalEdit;