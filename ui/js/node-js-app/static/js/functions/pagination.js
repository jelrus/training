/**
 * Holds current page state
 */
let currentPaginationState;

/**
 * Initializes page state from defined array and given page size
 *
 * @param {{}[]}array
 * @param {number}pageSize
 * @returns {{input: string, pages: number, size, pageSize, currentPage: number, category: string, items}}
 */
function initPaginationState(array, pageSize) {
    currentPaginationState = {
        items: array,
        size: array.length,
        pageSize: pageSize,
        pages: Math.ceil(array.length / pageSize),
        currentPage: 1,
        category: 'All Categories',
        input: ''
    }

    return currentPaginationState;
}

/**
 * Retrieves items from the page by page number
 *
 * @param {number}index
 * @returns {{}[]}
 */
function getPage(index) {
    let start = (index - 1) * currentPaginationState.pageSize;
    let end =
        index === currentPaginationState.pages
            ? currentPaginationState.size
            : currentPaginationState.pageSize * currentPaginationState.currentPage;
    return currentPaginationState.items.slice(start, end);
}

/**
 * Retrieves items from range of pages, ('from' and 'to' params are inclusive)
 *
 * @param {number}from
 * @param {number}to
 * @returns {{}[]}
 */
function getPages(from, to) {
    let start = (from - 1) * currentPaginationState.pageSize;
    let end =
        to === currentPaginationState.pages
            ? currentPaginationState.size
            : currentPaginationState.pageSize * currentPaginationState.currentPage;
    return currentPaginationState.items.slice(start, end);
}

/**
 * Checks if current is the first page
 *
 * @returns {boolean}
 */
function isFirstPage() {
    return currentPaginationState.currentPage === 1;
}

/**
 * Checks if current is the last page
 *
 * @returns {boolean}
 */
function isLastPage() {
    return currentPaginationState.currentPage === currentPaginationState.pages;
}

function nextPageExists() {
    return currentPaginationState.currentPage + 1 <= currentPaginationState.pages;
}