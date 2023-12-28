import React, {useEffect, useState} from "react";
import api from '../../api/axiosConfig';

import certificates from '../../static/css/certificates.module.css'

import 'font-awesome/css/font-awesome.min.css';
import * as fontawesome from "@fortawesome/fontawesome-svg-core";
import {faPlus} from "@fortawesome/free-solid-svg-icons";

fontawesome.library.add(faPlus);

/**
 * ModalView is the component, used for viewing and handling view item modal screen
 *
 * @param {Number}id
 * @param {Object}errorLog
 * @param updateLog
 * @returns {JSX.Element}
 * @constructor
 */
const ModalView = ({id, errorLog, updateLog}) => {

    /**
     * State hook for viewed certificate state
     */
    const [certificate, setCertificate] = useState('');

    /**
     * Tries to perform get request on backend, if response is valid returns object for view from backend,
     * otherwise returns error which is caught to error log
     *
     * @returns {Promise<void>}
     */
    const findCertificate = async () => {
        try {
            const response = await api.get('/gift/certificates/' + id);
            setCertificate(response.data);
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
     * Effect hook for viewed certificate
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
     * Hides view modal window, has pseudo close functionality
     */
    function hideViewWindow() {
        const windowWrapper = document.getElementById("viewWindowWrapper");
        windowWrapper.style.display = "none";
    }

    /**
     * Creates JSX element of view modal window
     */
    return (
        <div id="viewWindowWrapper" className={certificates.addWindowWrapper}>
            <div className={certificates.addWindow}>
                <div className={certificates.addWindowHeader}>
                    <span>Certificate Information (ID: {id})</span>
                </div>

                <div className={certificates.addWindowFormWrapper}>
                    <div className={certificates.addWindowForm}>
                        <div className={certificates.fieldArea}>
                            <span className={certificates.fieldHead}>Title</span>

                            <label className={certificates.inputArea}>
                                <span>{certificate.name}</span>
                            </label>
                        </div>

                        <div className={certificates.fieldArea}>
                            <span className={certificates.fieldHead}>Description</span>
                            <label className={certificates.inputArea}>
                                <span>{certificate.description}</span>
                            </label>
                        </div>

                        <div className={certificates.fieldArea}>
                            <span className={certificates.fieldHead}>Duration</span>

                            <label className={certificates.inputArea}>
                                <span>{certificate.duration}</span>
                            </label>
                        </div>

                        <div className={certificates.fieldArea}>
                            <span className={certificates.fieldHead}>Price</span>

                            <label className={certificates.inputArea}>
                                <span>{certificate.price}</span>
                            </label>
                        </div>

                        <div className={certificates.tagArea}>
                            <div className={certificates.fieldArea}>
                                <span className={certificates.fieldHead}>Tags</span>
                                <div id="added-tags" className={certificates.viewTags}>
                                    {certificate.tags?.map(t => {
                                        return (
                                            <span key={t.id}>#{t.name}</span>
                                        )
                                    })}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div className={certificates.addWindowButtonsWrapper}>
                    <div className={certificates.addWindowButtons}>
                        <button onClick={hideViewWindow}>Close</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ModalView;