/**
 * Holds navigation bar search select html element
 *
 * @type {HTMLElement}
 */
const searchCategoriesContainer = document.getElementById("nav-search-select");

/**
 * Holds content html element
 *
 * @type {HTMLElement}
 */
const content = document.getElementById("content");

/**
 * Holds categories container html element
 *
 * @type {HTMLElement}
 */
const categoriesContainer = document.getElementById("categories");

/**
 * Holds certificates container html element
 *
 * @type {HTMLElement}
 */
const certificatesContainer = document.getElementById("products");

/**
 * Holds page navigation container html element
 *
 * @type {HTMLElement}
 */
const pageNavContainer = document.getElementById("page-nav-container");

/**
 * Holds scroll to top button html element
 *
 * @type {HTMLElement}
 */
const scrollToTopButton = document.getElementById("scroll-top-btn");

/**
 * Holds scroll to bottom button html element
 *
 * @type {HTMLElement}
 */
const scrollToBotButton = document.getElementById("scroll-bot-btn");

/**
 * Holds initial pagination state
 */
let paginationState;

/**
 * Holds throttle timer value
 */
let throttleTimer;

/**
 * Creates HTML elements for categories selection in navigation bar
 */
function renderSearchCategories() {
    const optionAll = document.createElement('option');
    optionAll.value = "All Categories";
    optionAll.selected = true;
    optionAll.innerText = "All Categories";
    searchCategoriesContainer.appendChild(optionAll);

    categories.forEach(cat => {
        const option = document.createElement('option');
        option.value = `${cat.name}`;
        option.innerText = `${cat.name}`;
        searchCategoriesContainer.appendChild(option);
    })
}

/**
 * Creates HTML elements for categories section
 */
function renderCategories() {
    let fadeDuration = 1000;

    categories.forEach(cat => {
        const categoryWrapper = document.createElement('div');
        categoryWrapper.className = "category-wrapper";
        categoriesContainer.appendChild(categoryWrapper);

        const category = document.createElement('a');
        category.href = "#"
        category.className = "category";
        categoryWrapper.appendChild(category);

        const categoryCover = document.createElement('div');
        categoryCover.className = "category-cover";
        category.appendChild(categoryCover);

        const categoryImage = document.createElement('img');
        categoryImage.src = `${cat.image}`;
        categoryCover.appendChild(categoryImage);

        const categoryTitle = document.createElement('div');
        categoryTitle.className = "category-title";
        categoryTitle.innerText = `${cat.name}`;
        categoryCover.appendChild(categoryTitle);

        category.onclick = function () {
            searchBy(categoryTitle.innerText);
        };

        fadeIn(categoryWrapper, fadeDuration)
        fadeDuration += 500;
    })
}

/**
 * Changes products element style based on empty flag
 *
 * @param {boolean}empty
 */
function renderProducts(empty) {
    if (empty) {
        certificatesContainer.style.display = "flex";
    } else {
        certificatesContainer.style.display = "grid";
    }
}

/**
 * Creates HTML elements for certificate
 *
 * @param {Object}certificate
 * @param {number}fadeDuration
 */
function renderCertificate(certificate, fadeDuration) {
    const productWrapper = document.createElement('div');
    productWrapper.className = "product-wrapper";
    certificatesContainer.appendChild(productWrapper);

    const product = document.createElement('div');
    product.className = "product";
    productWrapper.appendChild(product);

    const productCover = document.createElement('a');
    productCover.className = "product-cover";
    productCover.href = "#"
    product.appendChild(productCover);

    const image = document.createElement('img');
    image.src = `${certificate.images}`;
    productCover.appendChild(image);
    const productInfoCard = document.createElement('div');

    productInfoCard.className = "product-info-card";
    product.appendChild(productInfoCard);
    const productInfoCardHead = document.createElement('div');

    productInfoCardHead.className = "product-info-card-head";
    productInfoCard.appendChild(productInfoCardHead);
    const productTitle = document.createElement('a');

    productTitle.className = "product-title";
    productTitle.href = "#"
    productInfoCardHead.appendChild(productTitle)
    const titleSpan = document.createElement('span');

    titleSpan.innerText = `${certificate.itemName}`;
    productTitle.appendChild(titleSpan);
    const favButton = document.createElement('button');

    favButton.className = "product-fav";
    productInfoCardHead.appendChild(favButton);
    const favIcon = document.createElement('span');

    favIcon.className = "material-icons";
    favIcon.innerText = "favorite_border";
    favButton.appendChild(favIcon);
    const productInfoCardMid = document.createElement('div');

    productInfoCardMid.className = "product-info-card-mid";
    productInfoCard.appendChild(productInfoCardMid);
    const productDescription = document.createElement('a');

    productDescription.className = "product-description";
    productDescription.href = "#"
    productInfoCardMid.appendChild(productDescription);
    const descriptionSpan = document.createElement('span');

    descriptionSpan.innerText = `${certificate.shortDescription}`;
    productDescription.appendChild(descriptionSpan);
    const productExpiration = document.createElement('a');

    productExpiration.className = "product-expiration";
    productExpiration.href = "#"
    productInfoCardMid.appendChild(productExpiration);
    const expirationSpan = document.createElement('span');

    let ed = Math.round((new Date(certificate.validTo).getTime() - new Date().getTime()) / (1000 * 3600 * 24));
    expirationSpan.innerText = `Expires in ${ed} days`;
    productExpiration.appendChild(expirationSpan);
    const productInfoCardTail = document.createElement('div');

    productInfoCardTail.className = "product-info-card-tail";
    productInfoCard.appendChild(productInfoCardTail);
    const productCost = document.createElement('a');

    productCost.className = "product-cost";
    productCost.href = "#"
    productInfoCardTail.appendChild(productCost);
    const costSpan = document.createElement('span');

    costSpan.innerText = `$${certificate.price}`;
    productCost.appendChild(costSpan);
    const productCart = document.createElement('button');

    productCart.className = "product-cart";
    productCart.innerText = "Add to Cart";
    productInfoCardTail.appendChild(productCart);

    fadeIn(productWrapper, fadeDuration);

    certificatesContainer.style.height = "fit-content";
}

/**
 * Simulates loading on empty screen
 */
function preRenderEmpty() {
    const emptyScreen = document.createElement('div');
    emptyScreen.id = "empty-screen";
    emptyScreen.style.display = 'flex';
    emptyScreen.style.border = "0";
    certificatesContainer.appendChild(emptyScreen);

    createLoader(emptyScreen, true);

    setTimeout(() => {
        emptyScreen.style.display = 'none';
    }, 3100);
}

/**
 * Renders empty screen
 */
function renderEmpty() {
    const emptyScreen = document.createElement('div');
    emptyScreen.id = "empty-screen"
    certificatesContainer.appendChild(emptyScreen);

    const icon = document.createElement("span");
    icon.className = "material-icons";
    icon.innerText = "sentiment_very_dissatisfied";
    emptyScreen.appendChild(icon);

    const message = document.createElement("span");
    message.textContent = "Sorry, no certificates was found by this request...";
    emptyScreen.appendChild(message);

    setTimeout(() => {
        emptyScreen.style.display = 'flex';
    }, 3100);
}

/**
 * Creates loader element to simulate loading data and appends to html element
 *
 * @param {HTMLElement}element
 * @param {boolean}fixed
 */
function createLoader(element, fixed) {
    const divLoader = document.createElement("div");
    divLoader.id = "loader";
    element.appendChild(divLoader);

    const loader = document.createElement("img");
    loader.src = "../resources/loader/loader.gif";
    divLoader.appendChild(loader);

    if (fixed) {
        divLoader.style.position = "fixed";
        divLoader.style.top = "50%";
        divLoader.style.left = "50%";
        divLoader.style.transform = "translate(-50%, -50%)";
    }

    setTimeout(() => {
        divLoader.style.display = 'none';
    }, currentPaginationState.pageSize * 250 + 250);
}

/**
 * Creates fade effect on element
 *
 * @param {HTMLElement}element
 * @param {number}duration
 */
const fadeIn = (element, duration) => {
    (function increment(value = 0) {
        element.style.opacity = String(value);
        if (element.style.opacity !== '1') {
            setTimeout(() => {
                increment(value + 0.1);
            }, duration / 20);
        }
    })();
};

/**
 * Restores pages from pagination state on reload
 */
function preRender() {
    renderProducts(true);
    createLoader(certificatesContainer, true);
    removeInfiniteScroll();

    if (isFirstPage()) {
        renderCertificates(getPage(1));
    } else {
        renderCertificates(getPages(1, currentPaginationState.currentPage));
    }

    addInfiniteScroll();
}

/**
 * Creates certificates as HTML elements
 *
 * @param {{}[]}certs
 */
const renderCertificates = (certs) => {
    removeInfiniteScroll();

    let fadeDuration = 250;
    let timeToShow = 250;

    if (currentPaginationState.items.length === 0) {
        renderProducts(true);
        preRenderEmpty();
        renderEmpty();
    } else {
        renderProducts(false);

        certs.forEach(function (certificate) {
            setTimeout(() => {
                renderCertificate(certificate, fadeDuration);
                fadeDuration += 250;
            }, timeToShow);
            timeToShow += 250;
        });
    }

    setTimeout(() => {addInfiniteScroll();}, 2000);
}


/**
 * Starts function (callback) execution after defined amount of time
 *
 * @param {function}callback
 * @param {number}time
 */
let throttle = (callback, time) => {
    if (throttleTimer) return;
    throttleTimer = true;

    setTimeout(() => {
        callback();
        throttleTimer = false;
    }, time);
};

/**
 * Handles infinite scroll
 * When scroll reaches bottom loads next page after throttle timer, if next page is the last will remove infinite scroll
 */
const handleInfiniteScroll = () => {
    throttle(() => {
        const endOfPage = window.innerHeight + window.scrollY >= document.body.offsetHeight;

        if (endOfPage && nextPageExists()) {
            createLoader(content, false);
            currentPaginationState.currentPage++

            setTimeout(() => {
                renderCertificates(getPage(currentPaginationState.currentPage));
            }, 3000);
        } else {
            removeInfiniteScroll();
        }
    }, 3500);
};

/**
 * Adds infinite scroll handler on scroll event
 */
const addInfiniteScroll = () => {
    window.addEventListener("scroll", handleInfiniteScroll);
}

/**
 * Removes infinite scroll handler from scroll event
 */
const removeInfiniteScroll = () => {
    window.removeEventListener("scroll", handleInfiniteScroll);
};

/**
 * On page loading creates data (categories, certificates), restore pagination state, creates html elements based on
 * data (categories, certificates), restores search input and select values
 */
window.onload = function () {
    createData();
    restoreState();
    renderSearchCategories();
    renderCategories();
    restoreSearch();
};

/**
 * Creates categories and certificates in local storage
 */
function createData() {
    createCategories();
    createCertificates();
}

/**
 * Restore pagination state from local storage
 */
function restoreState() {
    if (localStorage.getItem("lastState") === null) {
        paginationState = initPaginationState(certificates, 8);
        currentPaginationState = paginationState;
        renderCertificates(getPage(1));
    } else {
        paginationState = JSON.parse(localStorage.getItem("lastState"));
        currentPaginationState = paginationState;
        preRender();
    }

    renderTopScrollButton();
    botFunction(currentPaginationState.currentPage * 1500);
}

/**
 * Shows scroll top button and fixes footer overlapping if needed
 */
function renderTopScrollButton() {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        scrollToTopButton.style.display = "block";
    } else {
        scrollToTopButton.style.display = "none";
    }

    positionPageNavContainer();
}

/**
 * Restores search input and select values
 */
function restoreSearch() {
    searchSelect.value = currentPaginationState.category;
    searchInput.value = currentPaginationState.input;
}

/**
 * Removes data (categories, certificates), saves pagination state in local storage on unloading (refreshing) page
 */
window.onunload = function () {
    saveLastState()
    saveBottomScroll();
}

/**
 * Adds scroll function on scroll event
 */
window.onscroll = function () {
    scrollFunction()
};

/**
 * Displays scroll to top button depending on scroll position
 */
function scrollFunction() {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        scrollToTopButton.style.display = "block";
    } else {
        scrollToTopButton.style.display = "none";
    }

    if (window.scrollY === parseInt(localStorage.getItem("scrollBottom"))) {
        scrollToBotButton.style.display = "none";
        localStorage.removeItem("scrollBottom");
    }

    positionPageNavContainer();
}

/**
 * Scrolls to the top of the page
 */
function topFunction() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}

/**
 * Scroll to the last position saved before unloading page
 */
function botFunction(timeout) {
    const scrollY = localStorage.getItem("scrollBottom");

    if (scrollY !== null) {
        throttle(() => {
            window.scrollTo(0, parseInt(scrollY));
        }, timeout);
    }

    if (localStorage.getItem("scrollBottom") === null || parseInt(scrollY) === 0) {
        scrollToBotButton.style.display = "none";
    } else {
        scrollToBotButton.style.display = "block";
    }
}

/**
 * Repositions scroll to the top button if its overlapping footer
 */
function positionPageNavContainer() {
    let footerElement = document.getElementById('footer');
    let footerElementRect = footerElement.getBoundingClientRect();
    let positionBottom = pageNavContainer.offsetTop + pageNavContainer.offsetHeight;
    if (footerElementRect.y < positionBottom) {
        let diffHeight = positionBottom - footerElementRect.y;
        let style = window.getComputedStyle(pageNavContainer);
        let addBottom = parseInt(style.getPropertyValue('bottom')) + diffHeight;
        pageNavContainer.style.bottom = addBottom + 'px';
    } else {
        pageNavContainer.style.bottom = '';
    }
}

/**
 * Saves last pagination state
 */
function saveLastState() {
    paginationState = currentPaginationState;

    if (localStorage.length !== 0) {
        localStorage.setItem("lastState", JSON.stringify(currentPaginationState));
    }
}

/**
 * Saves vertical scroll position to local storage
 */
function saveBottomScroll() {
    localStorage.setItem("scrollBottom", window.scrollY.toString());
}
