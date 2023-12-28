import api from "../../api/axiosConfig";

import {clearErrors} from "../utils/ValidationInputHandler";
import certificates from "../../static/css/certificates.module.css";

/**
 * ModalDelete is the component, used for creating and handling delete item modal screen
 *
 * @param {Number}id
 * @param {Object}errorLog
 * @returns {JSX.Element}
 * @constructor
 */
const ModalDelete = ({id, errorLog, updateLog}) => {

    /**
     * Backend url, represents backend delete endpoint
     *
     * @type {string}
     */
    const deleteUrl = "http://localhost:8080/gift/certificates/" + id + "/delete";

    /**
     * Tries to perform delete request on backend, if response is valid returns created object from backend,
     * otherwise returns error which is caught to error log
     *
     * @returns {Promise<void>}
     */
    const handleDeleteCertificateRequest = async () => {
        await api.delete(
            deleteUrl,
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("user")).token
                }
            }
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
                }
            }
        );
    }

    /**
     * Hides delete modal window, has pseudo close functionality
     */
    function hideDeleteWindow() {
        const windowWrapper = document.getElementById("deleteWindowWrapper");
        windowWrapper.style.display = "none";
        clearErrors("Title", "Description", "Duration", "Price", "Tag", "");
    }

    /**
     * Creates JSX element of modal delete window
     */
    return (
        <div id="deleteWindowWrapper" className={certificates.deleteWindowWrapper}>
            <div className={certificates.deleteWindow}>
                <div id="delete-window-head" className={certificates.deleteWindowHeader}>
                    <span>
                        Delete Confirmation (ID: {id})
                    </span>
                </div>

                <div className={certificates.deleteWindowFormWrapper}>
                    <div className={certificates.deleteWindowForm}>
                        Do you really want to delete certificate with
                        <span>id = {id}?</span>
                    </div>
                </div>

                <div className={certificates.addWindowButtonsWrapper}>
                    <div className={certificates.addWindowButtons}>
                        <button onClick={handleDeleteCertificateRequest}>Delete</button>
                        <button onClick={hideDeleteWindow}>Cancel</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ModalDelete;