/**
 * Holds certificates from local storage
 *
 * @type {{}[]|*[]}
 */
let certificates = JSON.parse(localStorage.getItem('certificates')) || [];

const cert1 = {
    created: new Date().getTime() + 1000,
    category: "Travel",
    validTo: new Date().getTime() + 24 * 60 * 60 * 1000,
    price: 8.00,
    images: "../../resources/products/zoo.jpg",
    itemName: "Zoo Exhibition",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert2 = {
    created: new Date().getTime() + 2000,
    category: "Food",
    validTo: new Date().getTime() + 2 * 24 * 60 * 60 * 1000,
    price: 25.00,
    images: "../../resources/products/cooking_courses.jpg",
    itemName: "Cooking Courses",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert3 = {
    created: new Date().getTime() + 3000,
    category: "Beauty",
    validTo: new Date().getTime() + 3 * 24 * 60 * 60 * 1000,
    price: 12.00,
    images: "../../resources/products/yoga-session.jpg",
    itemName: "Yoga Sessions",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert4 = {
    created: new Date().getTime() + 4000,
    category: "Beauty",
    validTo: new Date().getTime() + 4 * 24 * 60 * 60 * 1000,
    price: 10.00,
    images: "../../resources/products/gym.jpg",
    itemName: "Gym Discount",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert5 = {
    created: new Date().getTime() + 5000,
    category: "Photo",
    validTo: new Date().getTime() + 5 * 24 * 60 * 60 * 1000,
    price: 35.00,
    images: "../../resources/products/photo-session.jpg",
    itemName: "Photo Session",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert6 = {
    created: new Date().getTime() + 6000,
    category: "Travel",
    validTo: new Date().getTime() + 6 * 24 * 60 * 60 * 1000,
    price: 15.00,
    images: "../../resources/products/planetarium.jpg",
    itemName: "Planetarium",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert7 = {
    created: new Date().getTime() + 7000,
    category: "Travel",
    validTo: new Date().getTime() + 7 * 24 * 60 * 60 * 1000,
    price: 75.00,
    images: "../../resources/products/horse_ride.jpg",
    itemName: "Horse Ride",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert8 = {
    created: new Date().getTime() + 8000,
    category: "Beauty",
    validTo: new Date().getTime() + 8 * 24 * 60 * 60 * 1000,
    price: 25.00,
    images: "../../resources/products/massage.jpg",
    itemName: "Massage",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert9 = {
    created: new Date().getTime() + 9000,
    category: "Travel",
    validTo: new Date().getTime() + 9 * 24 * 60 * 60 * 1000,
    price: 105.00,
    images: "../../resources/products/air_balloon_ride.jpg",
    itemName: "Air Balloon Ride",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert10 = {
    created: new Date().getTime() + 10000,
    category: "Cars",
    validTo: new Date().getTime() + 10 * 24 * 60 * 60 * 1000,
    price: 45.00,
    images: "../../resources/products/extreme-driving.jpg",
    itemName: "Extreme Driving",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert11 = {
    created: new Date().getTime() + 11000,
    category: "Cars",
    validTo: new Date().getTime() + 11 * 24 * 60 * 60 * 1000,
    price: 30.00,
    images: "../../resources/products/driver-license.jpg",
    itemName: "Driver License",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert12 = {
    created: new Date().getTime() + 12000,
    category: "Cars",
    validTo: new Date().getTime() + 12 * 24 * 60 * 60 * 1000,
    price: 30.00,
    images: "../../resources/products/motorbike_ride.jpg",
    itemName: "Motorbike Ride",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert13 = {
    created: new Date().getTime() + 13000,
    category: "Beauty",
    validTo: new Date().getTime() + 13 * 24 * 60 * 60 * 1000,
    price: 30.00,
    images: "../../resources/products/makeup.jpg",
    itemName: "Make Up",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert14 = {
    created: new Date().getTime() + 14000,
    category: "Travel",
    validTo: new Date().getTime() + 14 * 24 * 60 * 60 * 1000,
    price: 120.00,
    images: "../../resources/products/helicopter_ride.jpg",
    itemName: "Helicopter Ride",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert15 = {
    created: new Date().getTime() + 15000,
    category: "Beauty",
    validTo: new Date().getTime() + 15 * 24 * 60 * 60 * 1000,
    price: 55.00,
    images: "../../resources/products/haircut-man.jpg",
    itemName: "Haircut for Men",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert16 = {
    created: new Date().getTime() + 16000,
    category: "Beauty",
    validTo: new Date().getTime() + 16 * 24 * 60 * 60 * 1000,
    price: 110.00,
    images: "../../resources/products/haircut-woman.jpg",
    itemName: "Haircut for Women",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert17 = {
    created: new Date().getTime() + 17000,
    category: "Beauty",
    validTo: new Date().getTime() + 17 * 24 * 60 * 60 * 1000,
    price: 120.00,
    images: "../../resources/products/nails.jpg",
    itemName: "Nail Art",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert18 = {
    created: new Date().getTime() + 18000,
    category: "Beauty",
    validTo: new Date().getTime() + 18 * 24 * 60 * 60 * 1000,
    price: 150.00,
    images: "../../resources/products/spa.jpg",
    itemName: "Spa",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert19 = {
    created: new Date().getTime() + 19000,
    category: "Beauty",
    validTo: new Date().getTime() + 19 * 24 * 60 * 60 * 1000,
    price: 100.00,
    images: "../../resources/products/solarium.jpg",
    itemName: "Solarium",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert20 = {
    created: new Date().getTime() + 20000,
    category: "Cars",
    validTo: new Date().getTime() + 20 * 24 * 60 * 60 * 1000,
    price: 50.00,
    images: "../../resources/products/quadbiking.jpg",
    itemName: "Quad Biking",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert21 = {
    created: new Date().getTime() + 21000,
    category: "Cars",
    validTo: new Date().getTime() + 21 * 24 * 60 * 60 * 1000,
    price: 25.00,
    images: "../../resources/products/karts.jpg",
    itemName: "Kart Racing",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert22 = {
    created: new Date().getTime() + 22000,
    category: "Cars",
    validTo: new Date().getTime() + 22 * 24 * 60 * 60 * 1000,
    price: 45.00,
    images: "../../resources/products/f1-racing.jpg",
    itemName: "F1 Race Tickets Discount",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert23 = {
    created: new Date().getTime() + 23000,
    category: "Cars",
    validTo: new Date().getTime() + 23 * 24 * 60 * 60 * 1000,
    price: 49.00,
    images: "../../resources/products/motorbike-fest.jpg",
    itemName: "Motorbike Fest Tickets Discount",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert24 = {
    created: new Date().getTime() + 24000,
    category: "Cars",
    validTo: new Date().getTime() + 24 * 24 * 60 * 60 * 1000,
    price: 1200.00,
    images: "../../resources/products/car-salon.jpg",
    itemName: "Car Salon Discount",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert25 = {
    created: new Date().getTime() + 25000,
    category: "Cars",
    validTo: new Date().getTime() + 25 * 24 * 60 * 60 * 1000,
    price: 37.50,
    images: "../../resources/products/vintage-cars.jpg",
    itemName: "Vintage Cars Exhibition",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert26 = {
    created: new Date().getTime() + 26000,
    category: "Travel",
    validTo: new Date().getTime() + 26 * 24 * 60 * 60 * 1000,
    price: 1050.50,
    images: "../../resources/products/mountain-hiking.jpg",
    itemName: "Mountain Hiking",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert27 = {
    created: new Date().getTime() + 27000,
    category: "Travel",
    validTo: new Date().getTime() + 27 * 24 * 60 * 60 * 1000,
    price: 1750.50,
    images: "../../resources/products/cruise.jpg",
    itemName: "Cruise Tickets Discount",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert28 = {
    created: new Date().getTime() + 28000,
    category: "Travel",
    validTo: new Date().getTime() + 28 * 24 * 60 * 60 * 1000,
    price: 95.75,
    images: "../../resources/products/city-tour.jpg",
    itemName: "City Tour",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert29 = {
    created: new Date().getTime() + 29000,
    category: "Travel",
    validTo: new Date().getTime() + 29 * 24 * 60 * 60 * 1000,
    price: 83.45,
    images: "../../resources/products/museum-tour.jpg",
    itemName: "Museum Tour",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert30 = {
    created: new Date().getTime() + 30000,
    category: "Photo",
    validTo: new Date().getTime() + 30 * 24 * 60 * 60 * 1000,
    price: 120.00,
    images: "../../resources/products/wedding-photo.jpg",
    itemName: "Wedding Photo Session",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert31 = {
    created: new Date().getTime() + 31000,
    category: "Photo",
    validTo: new Date().getTime() + 31 * 24 * 60 * 60 * 1000,
    price: 20.55,
    images: "../../resources/products/id-photo.jpg",
    itemName: "ID Photo Session",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert32 = {
    created: new Date().getTime() + 32000,
    category: "Photo",
    validTo: new Date().getTime() + 32 * 24 * 60 * 60 * 1000,
    price: 20.55,
    images: "../../resources/products/family-photo.jpg",
    itemName: "Family Photo Session",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert33 = {
    created: new Date().getTime() + 33000,
    category: "Food",
    validTo: new Date().getTime() + 33 * 24 * 60 * 60 * 1000,
    price: 55.55,
    images: "../../resources/products/chinese-restaurant.jpg",
    itemName: "Chinese Restaurant Coupons",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert34 = {
    created: new Date().getTime() + 34000,
    category: "Food",
    validTo: new Date().getTime() + 34 * 24 * 60 * 60 * 1000,
    price: 67.45,
    images: "../../resources/products/italian-restaurant.jpg",
    itemName: "Italian Restaurant Coupons",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

const cert35 = {
    created: new Date().getTime() + 35000,
    category: "Food",
    validTo: new Date().getTime() + 35 * 24 * 60 * 60 * 1000,
    price: 67.45,
    images: "../../resources/products/advanced-cooking-courses.jpg",
    itemName: "Advanced Cooking Courses",
    shortDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    longDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
}

let certArray = [
    cert1, cert2, cert3, cert4,
    cert5, cert6, cert7, cert8,
    cert9, cert10, cert11, cert12,
    cert13, cert14, cert15, cert16,
    cert17, cert18, cert19, cert20,
    cert21, cert22, cert23, cert24,
    cert25, cert26, cert27, cert28,
    cert29, cert30, cert31, cert32,
    cert33, cert34, cert35
];

/**
 * Saves generated certificates into local storage
 */
function saveCertificatesToLocalStorage() {
    localStorage.setItem('certificates', JSON.stringify(certificates));
}

/**
 * Creates certificate with defined params and sorts them by created date
 *
 * @param {string}created
 * @param {string}company
 * @param {string}category
 * @param {string}validTo
 * @param {string}price
 * @param {string}images
 * @param {string}itemName
 * @param {string}shortDesc
 * @param {string}longDesc
 */
function createCertificate(created, category, validTo, price, images, itemName, shortDesc, longDesc) {
    const certificate = {
        created: new Date(created),
        category: category,
        validTo: new Date(validTo),
        price: parseFloat(price).toFixed(2),
        images: images,
        itemName: itemName,
        shortDescription: shortDesc,
        longDescription: longDesc
    };

    certificates.push(certificate);
    sortCertificatesByCreated();
}

/**
 * Creates certificates for local storage
 */
function createCertificates() {
    if (localStorage.getItem("certificates") === null) {
        for (let i = 0; i < certArray.length; i++) {
            createCertificate(
                certArray[i].created,
                certArray[i].category,
                certArray[i].validTo,
                certArray[i].price,
                certArray[i].images,
                certArray[i].itemName,
                certArray[i].shortDescription,
                certArray[i].longDescription
            );
        }
    }

    saveCertificatesToLocalStorage();
}

/**
 * Sorts certificates by created date
 */
function sortCertificatesByCreated() {
    certificates = certificates.sort(
        (a, b) => Number(b.created) - Number(a.created)
    );
}