/**
 * Holds categories from local storage
 *
 * @type {{}[]|*[]}
 */
let categories = JSON.parse(localStorage.getItem('categories')) || [];

const category1 = {
    name: "Photo",
    image: "../../resources/categories/photo.jpg"
}

const category2 = {
    name: "Food",
    image: "../../resources/categories/food.jpg"
}

const category3 = {
    name: "Travel",
    image: "../../resources/categories/travel.jpg"
}

const category4 = {
    name: "Cars",
    image: "../../resources/categories/cars.jpg"
}

const category5 = {
    name: "Beauty",
    image: "../../resources/categories/beauty_salons.jpg"
}

const catArray = [
    category1, category2, category3, category4, category5
];

/**
 * Saves generated categories into local storage
 */
function saveCategoriesToLocalStorage() {
    localStorage.setItem('categories', JSON.stringify(categories));
}

/**
 * Creates category with defined name and image
 *
 * @param {string}name
 * @param {string}image
 */
function createCategory(name, image) {
    const category = {
        name: name,
        image: image,
    };

    categories.unshift(category);
}

/**
 * Creates categories for local storage
 */
function createCategories() {
    if (localStorage.getItem("categories") === null) {
        for (let i = 0; i < catArray.length; i++) {
            createCategory(
                catArray[i].name,
                catArray[i].image
            );
        }
    }

    saveCategoriesToLocalStorage();
}