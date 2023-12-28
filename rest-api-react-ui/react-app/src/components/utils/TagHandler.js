import {clearErrors, validateTag} from "./ValidationInputHandler";

/**
 * Converts input into tag html element
 * Used in add new and edit modal windows
 *
 * @param {string}rootContainer
 * @param {string}mainInput
 * @param {string}inputArea
 */
function addTag(rootContainer, mainInput, inputArea) {
    clearErrors("Tag");
    const tags = document.getElementById(rootContainer);
    const input = document.getElementById(mainInput);
    const tagWrapper = document.createElement('span');
    const tag = document.createElement('div');
    const cancelButton = document.createElement('button');

    tagWrapper.className = "tagWrapper";
    tag.textContent = input.value;
    tag.className = "tag";
    cancelButton.textContent = "x"

    cancelButton.addEventListener("click", () => {
        tags.removeChild(cancelButton.parentNode);
    })

    if (validateTag(tag.textContent, inputArea)) {
        tagWrapper.appendChild(tag);
        tagWrapper.appendChild(cancelButton);
        tags.appendChild(tagWrapper);
    }
}

/**
 * Converts object to html element and appends it to specified parent html element
 *
 * @param {Object}tagObject
 * @param {string}rootContainer
 */
function appendTag(tagObject, rootContainer) {
    const tags = document.getElementById(rootContainer);
    const tagWrapper = document.createElement('span');
    const tag = document.createElement('div');
    const cancelButton = document.createElement('button');

    tagWrapper.className = "tagWrapper";
    tag.textContent = tagObject.name;
    tag.className = "tag";
    cancelButton.textContent = "x"

    cancelButton.addEventListener("click", () => {
        tags.removeChild(cancelButton.parentNode);
    });

    tagWrapper.appendChild(tag);
    tagWrapper.appendChild(cancelButton);
    tags.appendChild(tagWrapper);
}

/**
 * Adds tags objects to specified certificate
 *
 * @param {Object}certificate
 */
function addTags(certificate) {
    const tagElements = document.getElementsByClassName("tag");
    let tagsArray = [];

    for (let i = 0; i < tagElements.length; i++) {
        let tag = {
            name: tagElements[i].textContent
        }

        if (tagsArray.filter(e => e.name === tag.name).length === 0) {
            tagsArray.push(tag);
        }
    }

    certificate.tags = tagsArray;
}

export {addTag, addTags, appendTag};