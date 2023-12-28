import React from "react";

import certificates from '../../static/css/certificates.module.css'

import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import 'font-awesome/css/font-awesome.min.css';
import * as fontawesome from "@fortawesome/fontawesome-svg-core";
import {faSortAsc, faSortDesc, faSort} from "@fortawesome/free-solid-svg-icons";

fontawesome.library.add(faSortAsc, faSortDesc, faSort);

/**
 * SortingHeader is the component which creates JSX element for injecting into other component
 *
 * @param {string}baseName
 * @param {string}headerTitle
 * @param {Function}event
 * @returns {JSX.Element}
 * @constructor
 */
const SortingHeader = ({baseName, headerTitle, event}) => {

    /**
     * Creates JSX element of sorting header element
     */
    return (
        <th id="cell-" className={certificates.sortableHeader}>
            <button id={"sort-" + baseName + "-desc"} className={certificates.sortButton} value="desc"
                    onClick={event} style={{display: "none"}}>
                <FontAwesomeIcon icon="sort-asc"></FontAwesomeIcon>
            </button>
            <button id={"sort-" + baseName} className={certificates.sortButton} value="" onClick={event}>
                <FontAwesomeIcon icon="sort"></FontAwesomeIcon>
            </button>
            <button id={"sort-" + baseName + "-asc"} className={certificates.sortButton} value="asc"
                    onClick={event} style={{display: "none"}}>
                <FontAwesomeIcon icon="sort-desc"></FontAwesomeIcon>
            </button>
            <span className={certificates.header}>{headerTitle}</span>
        </th>
    )

}

export default SortingHeader;