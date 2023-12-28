import React, {useState, useEffect} from "react";
import { useNavigate} from 'react-router-dom';
import api from '../../api/axiosConfig';

import certificates from '../../static/css/certificates.module.css'

import SortingHeader from "../pagination/SortingHeader";
import TableView from "../pagination/TableView";
import ModalAdd from "../modals/ModalAdd";
import ModalView from "../modals/ModalView";
import ModalEdit from "../modals/ModalEdit";
import ModalDelete from "../modals/ModalDelete";
import Paging from "../pagination/Paging";
import Sizing from "../pagination/Sizing";

import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import 'font-awesome/css/font-awesome.min.css';
import * as fontawesome from "@fortawesome/fontawesome-svg-core";
import {
    faPlus, faExclamationCircle,
    faEye, faPenToSquare, faTrash,
    faAngleDoubleLeft, faAngleLeft, faAngleRight, faAngleDoubleRight
} from "@fortawesome/free-solid-svg-icons";
import ErrorLogHandler from "../exception/ErrorLogHandler";

fontawesome.library.add(
    faPlus, faExclamationCircle,
    faEye, faPenToSquare, faTrash,
    faAngleDoubleLeft, faAngleLeft, faAngleRight, faAngleDoubleRight
);

/**
 * Certificates is the component which handles all functionality for certificates product listing page
 *
 * Covers functionality:
 * - Pagination
 * - Sizing
 * - Sorting
 * - Searching
 * - Add new item
 * - View item
 * - Edit new item
 * - Delete item
 *
 * @returns {JSX.Element}
 * @constructor
 */
function Certificates() {

    /**
     * Backend url, represents backend find all endpoint
     *
     * @type {string}
     */
    let url = '/gift/certificates/all';

    /**
     * Holds page's search param from url
     *
     * @type {string}
     */
    const prevPage = new URLSearchParams(window.location.search).get("page");

    /**
     * Holds size's search param from url
     *
     * @type {string}
     */
    const prevSize = new URLSearchParams(window.location.search).get("size");

    /**
     * Holds sort's search param from url
     *
     * @type {string}
     */
    const prevSort = searchForKeyedParamsUrl("s:");

    /**
     * Holds search's search param from url
     *
     * @type {string}
     */
    const prevSearch = searchForKeyedParamsUrl("f:");

    /**
     * Navigation hook for url redirection
     */
    const nav = useNavigate();

    /**
     * State hook for all certificates
     */
    const [certificatesArray, setCertificatesArray] = useState();

    /**
     * State hook for current page
     */
    const [currentPage, setCurrentPage] = useState(prevPage !== null ? parseInt(prevPage) : 1);

    /**
     * State hook for size
     */
    const [pageSize, setPageSize] = useState(prevSize !== null ? parseInt(prevSize) : 10);

    /**
     * State hook for sort
     */
    const [sort, setSort] = useState(prevSort !== '' ? prevSort : "s:gc.create=asc");

    /**
     * State hook for search
     */
    const [search, setSearch] = useState(prevSearch !== '' ? prevSearch : '');

    /**
     * State hook for total pages
     */
    const [totalPages, setTotalPages] = useState(10);

    /**
     * State hook for view id (used for injecting into view modal window)
     */
    const [viewId, setViewId] = useState(0);

    /**
     * State hook for edit id (used for injecting into edit modal window)
     */
    const [editId, setEditId] = useState(0);

    /**
     * State hook for view id (used for injecting into delete modal window)
     */
    const [deleteId, setDeleteId] = useState(0);

    /**
     * State hook for error log
     */
    const [errorLog, setErrorLog] = useState([]);

    /**
     * Searches for url search params with specified key
     * Will return url string with all found search params which starts on the specified key
     *
     * @param {string}key
     * @returns {string}
     */
    function searchForKeyedParamsUrl(key) {
        const urlParams = window.location.search;
        const urlParamsSplit = urlParams.replace("?", '').split('&');
        let urlSort = '';

        for (let i = 0; i < urlParamsSplit.length; i++) {
            if (urlParamsSplit[i].startsWith(key)) {
                if (i < urlParamsSplit.length - 1) {
                    urlSort += urlParamsSplit[i] + "&";
                }
            }
        }

        return urlSort;
    }

    /**
     * Event listener, used for parsing search url params into search input at window loading
     */
    window.addEventListener("load", () => {
        const searchInput = document.getElementById("search");
        let searchParams = search.split("&");
        let searchString = '';

        searchParams = searchParams.filter((item) => item.startsWith("f:"));

        for (let i = 0; i < searchParams.length; i++) {
            let param = searchParams[i].replace("f:", '');
            const split = param.split("=");

            if (param.startsWith("gc.")) {
                searchString += split[1] + " ";
            } else {
                searchString += "#(" + split[1] + ") ";
            }
        }

        searchInput.value = searchString;
    });

    /**
     * Event listener, used for parsing sort url params into sort buttons' state at window loading
     */
    window.addEventListener("load", () => {
        const baseName = sort.replace('?', '')
                                     .replace('&', '')
                                     .replace("s:gc.", '')
                                     .split("=");
        const allButtons = document.getElementsByClassName(certificates.sortButton);

        let button;

        if (baseName[1] === "" || baseName[1] === "asc") {
            button = document.getElementById("sort-" + baseName[0] + "-desc");
        }

        if (baseName[1] === "desc") {
            button = document.getElementById("sort-" + baseName[0] + "-asc");
        }

        for (let i = 0; i < allButtons.length; i++) {
            const defaultSort = allButtons[i].value === "" && !allButtons[i].id.includes(baseName[0]);
            const currentSort = button.id === allButtons[i].id;

            if (defaultSort || currentSort) {
                allButtons[i].style.display = "inline";
            } else {
                allButtons[i].style.display = "none";
            }
        }
    });

    /**
     * Fetches url based on current paging variables state
     *
     * @returns {string}
     */
    function fetchUrl() {
        return "?page=" + currentPage + "&size=" + pageSize + "&" + sort + "&" + search;
    }

    /**
     * Tries to perform get request on backend, if response is valid returns objects for view from backend,
     * otherwise returns error which is caught to error log
     *
     * @returns {Promise<void>}
     */
    const handleFindAllCertificateRequest = async () => {
        const response = await api.get(url + fetchUrl())
            .catch((e) => {
                if (e.response !== null && e.response !== undefined) {
                    const error = {
                        id: errorLog.length + 1,
                        status: e.response.status,
                        message: e.response.data.errorMessage
                    }

                    errorLog.push(error);
                }
            });
        setCertificatesArray(response.data.items);
        setTotalPages(response.data.totalPages);
    }

    /**
     * Effect hook for find all certificates operation
     */
    useEffect(() => {
        handleFindAllCertificateRequest().catch((e) => {
            if (e.response !== null && e.response !== undefined) {
                const error = {
                    id: errorLog.length + 1,
                    status: e.response.status,
                    message: e.response.data.errorMessage
                }

                errorLog.push(error);
                setErrorLog(errorLog.filter(err => err));
            }
        });
        nav(fetchUrl());
    }, [currentPage, pageSize, sort, search, errorLog]);

    /**
     * Toggles error log, pseudo open window functionality
     */
    const toggleErrorLog = () => {
        const errorLogElement = document.getElementById("error-log-container");

        if (errorLogElement.style.display === "none") {
            errorLogElement.style.display = "flex";
        } else {
            errorLogElement.style.display = "none";
        }
    }

    /**
     * Style variable, changes style based on empty/not empty error log, injects into <style> tag of error log element
     *
     * @type {{color: (string)}}
     */
    const logStyles = {
        color: errorLog.length === 0 ? "goldenrod" : "#c51818"
    }

    /**
     * Toggles add new modal window, pseudo open window functionality
     */
    function toggleAddNewWindow() {
        const windowWrapper = document.getElementById("addWindowWrapper");
        windowWrapper.style.display = "flex";
    }

    /**
     * Toggles view modal window, pseudo open window functionality
     */
    const toggleViewWindow = (e) => {
        setViewId(e.currentTarget.id);

        const windowWrapper = document.getElementById("viewWindowWrapper");
        windowWrapper.style.display = "flex";
    }

    /**
     * Toggles edit modal window, pseudo open window functionality
     */
    const toggleEditWindow = (e) => {
        setEditId(e.currentTarget.id);

        const windowWrapper = document.getElementById("editWindowWrapper");
        windowWrapper.style.display = "flex";
    }

    /**
     * Toggles delete modal window, pseudo open window functionality
     */
    const toggleDeleteWindow = (e) => {
        setDeleteId(e.currentTarget.id);

        const windowWrapper = document.getElementById("deleteWindowWrapper");
        windowWrapper.style.display = "flex";
    }

    /**
     * Updates error log
     */
    const updateErrorLog = (newErrorLog) => {
        setErrorLog(newErrorLog);
    }

    /**
     * Mouse event function, applies on onclick event, used for changing listing based on search terms
     */
    const changeSearch = () => {
        const searchInput = document.getElementById("search");
        let arrayNameDescription = [];
        let arrayTags = [];
        let searchUrl = '';

        if (searchInput !== undefined && searchInput !== null &&
            (searchInput.value !== null && searchInput.value.trim().length !== 0)) {
            const inputRequest = searchInput.value.trim();
            let inputArray = inputRequest.split(" ")

            for (let i = 0; i < inputArray.length; i++) {
                if (/#\(\w*\)/.test(inputArray[i])) {
                    let proxy = inputArray[i].replace("#(", '');
                    proxy = proxy.replace(")", '');
                    arrayTags.push("f:t.name=" + proxy + "&");
                } else {
                    arrayNameDescription.push("f:gc.name=" + inputArray[i] + "&");
                    arrayNameDescription.push("f:gc.description=" + inputArray[i] + "&");
                }
            }

            for (let i = 0; i < arrayNameDescription.length; i++) {
                searchUrl += arrayNameDescription[i];
            }

            for (let i = 0; i < arrayTags.length; i++) {
                searchUrl += arrayTags[i];
            }
        }

        setSearch(searchUrl);
        setCurrentPage(1)
        setPageSize(10);
    }

    /**
     * Mouse event function, applies on onclick event, used for changing listing based on sort term
     *
     * @param {Object}e
     */
    const changeSorting = (e) => {
        const current = document.getElementById(e.currentTarget.id);
        const baseName = e.currentTarget.id.replace("sort-", "").replace("-asc", "").replace("-desc", "");
        const sort = "s:gc." + baseName + "=";
        const order = e.currentTarget.value;

        if (e.currentTarget.value === "asc" || e.currentTarget.value === "") {
            current.style.display = "none";
            const desc = document.getElementById("sort-" + baseName + "-desc");
            desc.style.display = "inline";
        }

        if (e.currentTarget.value === "desc") {
            current.style.display = "none";
            const asc = document.getElementById("sort-" + baseName + "-asc");
            asc.style.display = "inline";
        }

        resetHeaders(baseName);

        setSort(sort + order);
    }

    /**
     * Mouse event function, applies on onclick event, used for changing listing based on page term
     *
     * @param {Number}page
     */
    const changePage = (page) => {
        setCurrentPage(page);
    }

    /**
     * Mouse event function, applies on onclick event, used for changing listing based on size term
     *
     * @param {Object}e
     */
    const changeSize = (e) => {
        setCurrentPage(1);
        setPageSize(Number(e.target.value));
    }

    /**
     * Resets sorting headers to the default state (when only default sorting buttons appears on the screen)
     *
     * @param baseName
     */
    function resetHeaders(baseName) {
        const allButtons = document.getElementsByClassName(certificates.sortButton);

        for (let i = 0; i < allButtons.length; i++) {
            if (!allButtons[i].id.includes(baseName)) {
                if (allButtons[i].value === "asc" || allButtons[i].value === "desc") {
                    allButtons[i].style.display = "none";
                } else {
                    allButtons[i].style.display = "inline";
                }
            }
        }
    }

    /**
     * Creates JSX element of certificate's product list page window
     */
    return (
        <div className={certificates.pageContainerWrapper}>
            <ModalAdd errorLog={errorLog} updateLog={updateErrorLog}></ModalAdd>
            <ModalView id={viewId} errorLog={errorLog} updateLog={updateErrorLog}/>
            <ModalEdit id={editId} errorLog={errorLog} updateLog={updateErrorLog}/>
            <ModalDelete id={deleteId} errorLog={errorLog} updateLog={updateErrorLog}/>

            <div className={certificates.pageContainer}>
                <div id="error-log-container" className={certificates.errorLogWrapper}>
                    <ErrorLogHandler errorLog={errorLog} updateLog={updateErrorLog}/>
                </div>

                <div className={certificates.searchBar}>
                    <label className={certificates.searchArea}>
                        <input id="search" className={certificates.searchInput} placeholder="Search..."></input>
                    </label>
                    <div className={certificates.searchBtnWrapper}>
                        <button className={certificates.searchButton} onClick={changeSearch}>Search</button>
                    </div>
                    <div className={certificates.addNewButtonWrapper}>
                        <button className={certificates.addNewButton} onClick={toggleAddNewWindow}>
                            <FontAwesomeIcon icon="plus"></FontAwesomeIcon>
                        </button>
                    </div>
                    <div className={certificates.errorsButtonWrapper}>
                        <button id="error-log" className={certificates.errorsButton}
                                onClick={toggleErrorLog} style={logStyles}>
                            <FontAwesomeIcon icon="exclamation-circle"></FontAwesomeIcon>
                        </button>
                        <span id={"error-counter"} className={certificates.errorLogSize}>{errorLog.length} errors</span>
                    </div>
                </div>

                <table className={certificates.items}>
                    <tbody className={certificates.itemsHead}>
                    <tr>
                        <SortingHeader baseName={"create"} headerTitle={"Datetime"} event={changeSorting}/>
                        <SortingHeader baseName={"name"} headerTitle={"Title"} event={changeSorting}/>
                        <th className={certificates.itemsCell}>
                            <span className={certificates.header}>Tags</span>
                        </th>
                        <SortingHeader baseName={"description"} headerTitle={"Description"} event={changeSorting}/>
                        <SortingHeader baseName={"price"} headerTitle={"Price"} event={changeSorting}/>
                        <th className={certificates.itemsCell}>
                            <span className={certificates.header}>Actions</span>
                        </th>
                    </tr>
                    </tbody>
                    <tbody className={certificates.itemRow}>
                    <TableView data={certificatesArray}
                               viewEvent={toggleViewWindow}
                               editEvent={toggleEditWindow}
                               deleteEvent={toggleDeleteWindow}/>
                    </tbody>
                </table>

                <div className={certificates.paginationContainer}>
                    <Paging currentPage={currentPage} totalPages={totalPages} event={changePage}></Paging>
                    <Sizing pageSize={pageSize} event={changeSize} sizes={[10, 20, 50]}/>
                </div>
            </div>
        </div>
    );
}

export default Certificates;