import certificates from '../../static/css/certificates.module.css'

import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import 'font-awesome/css/font-awesome.min.css';
import * as fontawesome from "@fortawesome/fontawesome-svg-core";
import {
    faCircleXmark, faXmark
} from "@fortawesome/free-solid-svg-icons";

fontawesome.library.add(
    faCircleXmark, faXmark
);

/**
 * ErrorLogHandler is the component, used for handling error log events and creating JSX element of error log
 *
 * @param {[{}]}errorLog
 * @param {Function}updateLog
 * @returns {JSX.Element}
 * @constructor
 */
const ErrorLogHandler = ({errorLog, updateLog}) => {

    /**
     * Removes error message from log and as consequence dismisses it from the screen
     *
     * @param {Object}e
     */
    const dismiss = (e) => {
        const id = e.currentTarget.id.replace("error-button-", '');
        updateLog(errorLog.filter(err => err.id !== parseInt(id)));
    }

    /**
     * Creates JSX element for error log object
     */
    return (
        <div className={certificates.errorLog}>
            {
                errorLog?.map(e => {
                        return (
                            <span key={e.id} id={"error-wrapper-" + e.id} className={certificates.errorMessageWrapper}>
                                <div className={certificates.errorMessage}>
                                    <span className={certificates.errorMessageIcon}>
                                        <FontAwesomeIcon icon={faCircleXmark}></FontAwesomeIcon>
                                    </span>
                                    <span className={certificates.errorMessageBody}>
                                        Error occurred with status ({e.status}) and message ({e.message})
                                    </span>
                                    <button id={"error-button-" + e.id}
                                            onClick={dismiss}
                                            className={certificates.errorMessageButton}>
                                        <FontAwesomeIcon icon={faXmark}></FontAwesomeIcon>
                                    </button>
                                </div>
                            </span>
                        )
                    }
                )
            }
        </div>
    )
}

export default ErrorLogHandler;