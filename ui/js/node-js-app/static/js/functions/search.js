/**
 * Holds navigation bar search select html element
 *
 * @type {HTMLElement}
 */
const searchSelect = document.getElementById("nav-search-select");

/**
 * Holds navigation bar search input html element
 *
 * @type {HTMLElement}
 */
const searchInput = document.getElementById("search-input");

/**
 * Starts function (callback) execution every time after defined wait interval
 *
 * @param {function}callback
 * @param {number}wait
 * @returns {(function(...[*]): void)|*}
 */
function debounce(callback, wait) {
    let timeout;
    return (...args) => {
        clearTimeout(timeout);
        timeout = setTimeout(function () {
            callback.apply(this, args);
        }, wait);
    };
}

/**
 * Return filtered certificates based on search conditions
 *
 * @returns {{}[]|*[]|Object[]}
 */
function search() {
    if (searchInput.value.trim().length === 0 && searchSelect.value === "All Categories") {
        return certificates;
    }

    if (searchInput.value.trim().length !== 0 && searchSelect.value === "All Categories") {
        return certificates.filter(function (certificate) {
            return checkInput(certificate, searchInput)
        });
    }

    if (searchInput.value.trim().length === 0 && searchSelect.value !== "All Categories") {
        return certificates.filter(function (certificate) {
            return checkSelect(certificate, searchSelect);
        });
    }

    if (searchInput.value.trim().length !== 0 && searchSelect.value !== "All Categories") {
        return certificates.filter(function (certificate) {
            return checkSelect(certificate, searchSelect) && checkInput(certificate, searchInput);
        });
    }
}

/**
 * Checks whether certificate elements contains value from search input
 *
 * @param {Object}certificate
 * @param {HTMLElement}input
 * @returns {boolean}
 */
function checkInput(certificate, input) {
    return certificate.category.toLowerCase().includes(input.value.toLowerCase())
        || certificate.itemName.toLowerCase().includes(input.value.toLowerCase())
        || certificate.shortDescription.toLowerCase().includes(input.value.toLowerCase())
        || certificate.longDescription.toLowerCase().includes(input.value.toLowerCase());
}

/**
 * Checks whether certificate's category meets search select conditions
 *
 * @param {Object}certificate
 * @param {HTMLElement}select
 * @returns {boolean}
 */
function checkSelect(certificate, select) {
    return certificate.category.toLowerCase() === select.value.toLowerCase();
}

/**
 * Searches certificates by the category name and recreates html elements based on filtering event
 */
function searchBy(catName) {
    searchSelect.value = catName;
    searchInput.value = '';

    reRender(
        certificates.filter(function (certificate) {
            return certificate.category.toLowerCase() === catName.toLowerCase();
        })
    );
}

/**
 * Wipes current certificates containers, memorizes search input and select, recreates certificates html elements
 * based on filtering event
 *
 * @param searchResult
 */
function reRender(searchResult) {
    throttle(() => {
        certificatesContainer.replaceChildren();
        currentPaginationState = initPaginationState(searchResult, 8);

        renderProducts(true);
        createLoader(certificatesContainer, true);
        memorizeInput();

        renderCertificates(getPage(1));

        addInfiniteScroll();
        window.addEventListener("scroll", handleInfiniteScroll);
    }, 1000);
}

/**
 * Memorizes values for search select and input
 */
function memorizeInput() {
    currentPaginationState.category = searchSelect.value;
    currentPaginationState.input = searchInput.value;
}

/**
 * Checks whether search input changed after certain period of time
 */
searchInput.addEventListener("input", debounce(function () {
    reRender(search());
}, 600));


/**
 * Recreates items on the page on search select changes
 */
searchSelect.addEventListener("change", debounce(function () {
    reRender(search());
}, 600));