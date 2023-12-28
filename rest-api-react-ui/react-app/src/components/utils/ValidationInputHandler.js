/**
 * Validates login's input values
 *
 * @param {Object}inputValues
 */
const validateLogin = (inputValues) => {
    if (inputValues.username === null
        || inputValues.username.trim().length <= 3
        || inputValues.username.trim().length >= 30) {
        renderError(document.getElementById("usernameArea"), "Username", "start", "is invalid");
    }

    if (inputValues.password === null
        || inputValues.password.trim().length <= 4
        || inputValues.password.trim().length >= 30) {
        renderError(document.getElementById("passwordArea"), "Password", "start", "is invalid");
    }
};

/**
 * Validates certificate's input values, returns number of errors appeared on input page
 *
 * @param {Object}inputValues
 * @param {string}elementPrefix
 * @returns {number}
 */
const validateCertificate = (inputValues, elementPrefix) => {
    let errors = 0;

    if (inputValues.name === null
        || inputValues.name.trim().length < 6
        || inputValues.name.trim().length > 30) {
        renderError(document.getElementById(elementPrefix + "error-title"),
            "Title", "center", "is invalid");
        errors++;
    }

    if (inputValues.description === null
        || inputValues.description.trim().length < 12
        || inputValues.description.trim().length > 1000) {
        renderError(document.getElementById(elementPrefix + "error-description"),
            "Description", "center", "is invalid");
        errors++;
    }

    if (inputValues.duration === null
        || inputValues.description.trim().length === 0
        || !/^\d+$/.test(inputValues.duration)
        || parseInt(inputValues.duration) < 0) {

        renderError(document.getElementById(elementPrefix + "error-duration"),
            "Duration", "center", "is invalid");
        errors++;
    }

    if (inputValues.price === null
        || inputValues.description.trim().length === 0
        || isNaN(parseFloat(inputValues.price))
        || parseFloat(inputValues.price) <= 0) {
        renderError(document.getElementById(elementPrefix + "error-price"),
            "Price", "center", "is invalid");
        errors++;
    }

    return errors;
}

/**
 * Validates tag's input values
 *
 * @param {Object}inputValue
 * @param {string}inputArea
 * @returns {boolean}
 */
const validateTag = (inputValue, inputArea) => {
    if (inputValue === null || inputValue.trim().length < 3 || inputValue.trim().length > 30) {
        renderError(document.getElementById(inputArea),
            "Tag", "start", "is invalid");
        return false;
    }

    return true;
}

/**
 * Renders error with specified parent html element, field, position and message
 *
 * @param {HTMLElement}parent
 * @param {string}field
 * @param {string}position
 * @param {string}message
 * @returns {HTMLDivElement}
 */
function renderError(parent, field, position, message) {
    const error = document.createElement("div");
    error.id = "errorMessage" + field;
    error.style.textAlign = position
    error.style.fontSize = "0.65vw";
    error.style.padding = "1% 2%"
    error.style.color = "red";
    error.textContent = field + " " + message;
    parent.appendChild(error);
    return error;
}

/**
 * Renders errors based on response status on input page
 *
 * @param {Object}error
 * @param {string}windowHead
 */
function catchErrors(error, windowHead) {
    if (error.response.status === 400) {
        const head = document.getElementById(windowHead);
        renderError(head, "", "center",
            "Some fields are invalid or certificate title already exists");
    }

    if (error.response.status === 401) {
        const head = document.getElementById(windowHead);
        renderError(head, "", "center",
            "Token corrupted or you don't have permissions, please re-login");
    }

    if (error.response.status === 404) {
        const head = document.getElementById(windowHead);
        renderError(head, "", "center",
            "Certificate wasn't found");
    }

    if (error.response.status === 409) {
        const head = document.getElementById(windowHead);
        renderError(head, "", "center",
            "Certificate with this title already exists");
    }
}

/**
 * Clears error messages from input fields
 *
 * @param {array}fields
 */
function clearErrors(...fields) {
    fields.forEach((f) => {
        if (document.getElementById("errorMessage" + f) !== null) {
            document.getElementById("errorMessage" + f).remove();
        }
    })
}

export {validateLogin, validateCertificate, validateTag, renderError, catchErrors, clearErrors}