'use strict';

/**
 * Exports methods from current file
 *
 * @type {any}
 */
module.exports = {
    secondsToDate,
    toBase2Converter,
    substringOccurrencesCounter,
    repeatingLetters,
    redundant,
    towerHanoi,
    matrixMultiplication,
    gather
};

/**
 * You must return a date that comes in a predetermined number of seconds after 01.06.2020 00:00:002020
 * @param {number} seconds
 * @returns {string}
 *
 * @example
 *      31536000 -> 01.06.2021
 *      0 -> 01.06.2020
 *      86400 -> 02.06.2020
 */
function secondsToDate(seconds) {
    checkIfNull(seconds);
    checkIfInteger(seconds);
    checkIfLessThanZero(seconds);
    return new Date(new Date("2020-06-01").getTime() + seconds * 1000).toLocaleDateString('ru-RU');
}

/**
 * You must create a function that returns a base 2 (binary) representation of a base 10 (decimal) string number
 * ! Numbers will always be below 1024 (not including 1024)
 * ! You are not able to use parseInt
 * @param {number} decimal
 * @return {string}
 *
 * @example
 *      5 -> "101"
 *      10 -> "1010"
 */
function toBase2Converter(decimal) {
    checkIfNull(decimal);
    checkIfInteger(decimal);
    checkIfInRange(decimal, 0, 1024);
    return decimal.toString(2);
}

/**
 * You must create a function that takes two strings as arguments and returns the number of times the first string
 * is found in the text.
 * @param {string} substring
 * @param {string} text
 * @return {number}
 *
 * @example
 *      'a', 'test it' -> 0
 *      't', 'test it' -> 3
 *      'T', 'test it' -> 3
 */
function substringOccurrencesCounter(substring, text) {
    checkIfNull(substring);
    checkIfNull(text);
    checkIfString(substring);
    checkIfString(text);
    return text.split(substring.toLowerCase()).length - 1;
}

/**
 * You must create a function that takes a string and returns a string in which each character is repeated once.
 *
 * @param {string} string
 * @return {string}
 *
 * @example
 *      "Hello" -> "HHeelloo"
 *      "Hello world" -> "HHeelloo  wwoorrlldd" // o, l is repeated more than once. Space was also repeated
 */
function repeatingLetters(string) {
    checkIfNull(string);
    checkIfString(string);

    const split = string.split('');
    const edit = [];

    for (let i = 0; i < split.length; i++){
        edit.push(split[i]);

        if (split[i] !== split[i+1] && split[i-1] !== split[i]) {
            edit.push(split[i]);
        }
    }

    return edit.join('').toString();
}

/**
 * You must write a function redundant that takes in a string str and returns a function that returns str.
 * ! Your function should return a function, not a string.
 *
 * @param {string} str
 * @return {function}
 *
 * @example
 *      const f1 = redundant("apple")
 *      f1() ➞ "apple"
 *
 *      const f2 = redundant("pear")
 *      f2() ➞ "pear"
 *
 *      const f3 = redundant("")
 *      f3() ➞ ""
 */
function redundant(str) {
    checkIfNull(str);
    checkIfString(str);

    return function() {
        return str;
    };
}

/**
 * https://en.wikipedia.org/wiki/Tower_of_Hanoi
 *
 * @param {number} disks
 * @return {number}
 */
function towerHanoi(disks) {
    checkIfNull(disks);
    checkIfInteger(disks);
    checkIfLessThanZero(disks);
    return Math.pow(2, disks) - 1;
}

/**
 * You must create a function that multiplies two matricies (n x n each).
 *
 * @param {array} matrixA
 * @param {array} matrixB
 * @return {array}
 *
 */
function matrixMultiplication(matrixA, matrixB) {
    checkIfNull(matrixA);
    checkIfNull(matrixB);
    checkIfArray(matrixA);
    checkIfArray(matrixB);

    const n = matrixA.length;

    if (n !== matrixA[0].length || n !== matrixB.length || n !== matrixB[0].length) {
        throw "Invalid matrix dimensions for multiplication";
    }

    const result = Array.from({ length: n }, () => Array(n).fill(0));

    for (let i = 0; i < n; i++) {
        for (let j = 0; j < n; j++) {
            for (let k = 0; k < n; k++) {
                result[i][j] += matrixA[i][k] * matrixB[k][j];
            }
        }
    }

    return result;
}

/**
 * Create a gather function that accepts a string argument and returns another function.
 * The function calls should support continued chaining until order is called.
 * order should accept a number as an argument and return another function.
 * The function calls should support continued chaining until get is called.
 * get should return all the arguments provided to the gather functions as a string in the order specified in the order functions.
 *
 * @param {string} str
 * @return {any}
 *
 * @example
 *      gather("a")("b")("c").order(0)(1)(2).get() ➞ "abc"
 *      gather("a")("b")("c").order(2)(1)(0).get() ➞ "cba"
 *      gather("e")("l")("o")("l")("!")("h").order(5)(0)(1)(3)(2)(4).get()  ➞ "hello!"
 */
function gather(str) {
    checkIfNull(str);
    checkIfString(str);

    const values = [str];
    const indices = [];

    function gatherFn(value) {
        checkIfNull(value);
        checkIfString(value);

        values.push(value)
        return gatherFn;
    }

    const order = gatherFn.order = function(number) {
        checkIfNull(number);
        checkIfInteger(number);

        indices.push(number);
        return order;
    }

    order.get = function() {
        return indices.map((index) => values[index]).join('');
    }

    return gatherFn;
}

/**
 * Checks if input is null
 * @param input
 */
function checkIfNull(input) {
    if (input === null) {
        throw "input = " + input + " is null";
    }
}

/**
 * Checks if input is string
 *
 * @param input
 */
function checkIfString(input) {
    if (typeof input !== 'string') {
        throw "input = " + input + " is not the string";
    }
}

/**
 * Checks if input is integer number
 *
 * @param input
 */
function checkIfInteger(input) {
    if (!Number.isInteger(input)) {
        throw "input = " + input + " is not the integer number";
    }
}

function checkIfLessThanZero(input) {
    if (input < 0) {
        throw "input = " + input + " is less than 0";
    }
}

/**
 * Checks if input is in range [from; to] inclusively
 *
 * @param input
 * @param from
 * @param to
 */
function checkIfInRange(input, from, to) {
    if (input < from || input > to) {
        throw "input = " + input + " is not in range (" + from + "; " + to + ")";
    }
}

/**
 * Checks if input is array type
 * @param input
 */
function checkIfArray(input) {
    if (!Array.isArray(input)) {
        throw "input = " + input + " is not array";
    }
}