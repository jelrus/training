import React from "react";
import certificates from "../../static/css/certificates.module.css";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

/**
 * Paging is the component which handles all functionality related with paging
 *
 * @param {Number}totalPages
 * @param {Number}currentPage
 * @param {Function}event
 * @returns {JSX.Element}
 * @constructor
 */
const Paging = ({totalPages, currentPage, event}) => {

    /**
     * Represents start of page's array
     *
     * @type {number}
     */
    const start = currentPage - 3;

    /**
     * Represents end of page's array
     *
     * @type {number}
     */
    const end = currentPage + 3;

    /**
     * Creates array with numbers which represents pages for button's rendering
     *
     * @param start
     * @param stop
     * @param step
     * @returns {unknown[]}
     */
    const pagesArray = (start, stop, step) =>
        Array.from(
            {length: (stop - start) / step + 1},
            (value, index) => start + index * step
        );

    /**
     * Creates JSX element of paging element
     */
    return (
        <div className={certificates.pagesWrapper}>
            <button id="first-page" className={certificates.firstPage} onClick={() => event(1)}
                    disabled={currentPage === 1}>
                <FontAwesomeIcon icon="angle-double-left"></FontAwesomeIcon>
            </button>
            <button id="prev-page" onClick={() => event(currentPage - 1)}
                    disabled={currentPage === 1} className={certificates.prevPage}>
                <FontAwesomeIcon icon="angle-left"></FontAwesomeIcon>
            </button>

            {
                pagesArray(start, end, 1).map(page => {
                    if (page > 0 && page <= totalPages) {
                        if (page !== currentPage) {
                            return (
                                <button key={Number(page)} onClick={() => event(page)}
                                        className={certificates.pageButton}>
                                    {page}
                                </button>
                            )
                        } else {
                            return (
                                <button key={Number(page)} onClick={() => event(page)}
                                        className={certificates.currentPageButton}>
                                    {page}
                                </button>
                            )
                        }
                    }
                })
            }

            <button id="next-page" className={certificates.nextPage}
                    onClick={() => event(currentPage + 1)} disabled={currentPage === totalPages}>
                <FontAwesomeIcon icon="angle-right"></FontAwesomeIcon>
            </button>
            <button id="last-page" className={certificates.lastPage} onClick={() => event(totalPages)}
                    disabled={currentPage === totalPages}>
                <FontAwesomeIcon icon="angle-double-right"></FontAwesomeIcon>
            </button>
        </div>
    )
}

export default Paging;