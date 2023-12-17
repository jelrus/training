/**
 * Imports methods from target file for testing
 */
const {
    secondsToDate,
    toBase2Converter,
    substringOccurrencesCounter,
    repeatingLetters,
    redundant,
    towerHanoi,
    matrixMultiplication,
    gather
} = require('./../practice-tasks.js');

test('converts seconds to date', () => {
    //exceptional cases
    expect(() => {secondsToDate(null)}).toThrow("input = null is null");
    expect(() => {secondsToDate("11a")}).toThrow("input = 11a is not the integer number");
    expect(() => {secondsToDate(-1)}).toThrow("input = -1 is less than 0");

    //regular cases
    expect(secondsToDate(31536000)).toBe("01.06.2021");
    expect(secondsToDate(0)).toBe("01.06.2020");
    expect(secondsToDate(86400)).toBe("02.06.2020");
});

test('converts decimal number to string binary number', () => {
    //exceptional cases
    expect(() => {toBase2Converter(null)}).toThrow("input = null is null");
    expect(() => {toBase2Converter("22b")}).toThrow("input = 22b is not the integer number");
    expect(() => {toBase2Converter(-1)}).toThrow(
        "input = -1 is not in range (0; 1024)"
    );
    expect(() => {toBase2Converter(1025)}).toThrow(
        "input = 1025 is not in range (0; 1024)"
    );

    //regular cases
    expect(toBase2Converter(5)).toBe("101");
    expect(toBase2Converter(10)).toBe("1010");
});

test('counts the number of occurrences of a substring in a string', () => {
    //exceptional cases
    expect(() => {substringOccurrencesCounter(null, "test")}).toThrow("input = null is null");
    expect(() => {substringOccurrencesCounter("t", null)}).toThrow("input = null is null");
    expect(() => {substringOccurrencesCounter(10, "test")}).toThrow("input = 10 is not the string");
    expect(() => {substringOccurrencesCounter("t", 10)}).toThrow("input = 10 is not the string");

    //regular cases
    expect(substringOccurrencesCounter('a', 'test it')).toBe(0);
    expect(substringOccurrencesCounter('t', 'test it')).toBe(3);
    expect(substringOccurrencesCounter('T', 'test it')).toBe(3);
});

test('doubles letters in string', () => {
    //exceptional cases
    expect(() => {repeatingLetters(null)}).toThrow("input = null is null");
    expect(() => {repeatingLetters(10)}).toThrow("input = 10 is not the string");

    //regular cases
    expect(repeatingLetters("Hello")).toBe("HHeelloo");
    expect(repeatingLetters("Hello world")).toBe("HHeelloo  wwoorrlldd");
    expect(repeatingLetters("fuzz")).toBe("ffuuzz")
});

test('takes string and returns function', () => {
    //exceptional cases
    expect(() => {redundant(null)}).toThrow("input = null is null");
    expect(() => {redundant(10)}).toThrow("input = 10 is not the string");

    //regular cases
    const f1 = redundant("apple")
    expect(f1()).toBe("apple");

    const f2 = redundant("pear")
    expect(f2()).toBe("pear");

    const f3 = redundant("")
    expect(f3()).toBe("");
});

test('calculates the minimum number of moves to solve the tower of hanoi', () => {
    //exceptional cases
    expect(() => {towerHanoi(null)}).toThrow("input = null is null");
    expect(() => {towerHanoi("22b")}).toThrow("input = 22b is not the integer number");
    expect(() => {towerHanoi(-1)}).toThrow("input = -1 is less than 0");

    //regular cases
    expect(towerHanoi(2)).toBe(3);
    expect(towerHanoi(3)).toBe(7);
    expect(towerHanoi(4)).toBe(15);
});

test('multiplicates matrices', () => {
    //exceptional cases
    const matrixA1 = null;
    const matrixB1 = [[1, 0, 1],[2, 0, 2],[3, 0, 3]];

    expect(() => {matrixMultiplication(matrixA1, matrixB1)}).toThrow("input = " + matrixA1 + " is null");

    const matrixA2 = [[1, 0, 1],[2, 0, 2],[3, 0, 3]];
    const matrixB2 = null;
    expect(() => {matrixMultiplication(matrixA2, matrixB2)}).toThrow("input = " + matrixB2 + " is null");

    const matrixA3 = "aaa";
    const matrixB3 = [[1, 0, 1],[2, 0, 2],[3, 0, 3]];

    expect(() => {matrixMultiplication(matrixA3, matrixB3)}).toThrow("input = " + matrixA3 + " is not array");

    const matrixA4 = [[1, 0, 1],[2, 0, 2],[3, 0, 3]];
    const matrixB4 = 1111;

    expect(() => {matrixMultiplication(matrixA4, matrixB4)}).toThrow("input = " + matrixB4 + " is not array");

    const matrixA5 = [[1, 0, 1],[2, 0, 2],[3, 0, 3]];
    const matrixB5 = [[1, 0, 1],[2, 0, 2]];

    expect(() => {matrixMultiplication(matrixA5, matrixB5)}).toThrow("Invalid matrix dimensions for multiplication");

    //regular cases
    const matrixA = [[1, 0, 1],[2, 0, 2],[3, 0, 3]];
    const matrixB = [[1, 0, 1],[2, 0, 2],[3, 0, 3]];

    expect(matrixMultiplication(matrixA, matrixB)).toStrictEqual([[4, 0, 4],[8, 0, 8],[12, 0, 12]]);
});

test('gathers letters, orders them and write them into the string', () => {
    //exceptional cases
    expect(() => {gather(null)("b")("c").order(0)(1)(2).get()}).toThrow("input = null is null");
    expect(() => {gather("a")(null)("c").order(0)(1)(2).get()}).toThrow("input = null is null");
    expect(() => {gather("a")("b")(null).order(0)(1)(2).get()}).toThrow("input = null is null");

    expect(() => {gather(1)("b")("c").order(0)(1)(2).get()}).toThrow("input = 1 is not the string");
    expect(() => {gather("a")(2)("c").order(0)(1)(2).get()}).toThrow("input = 2 is not the string");
    expect(() => {gather("a")("b")(3).order(0)(1)(2).get()}).toThrow("input = 3 is not the string");

    expect(() => {gather("a")("b")("c").order(null)(1)(2).get()}).toThrow("input = null is null");
    expect(() => {gather("a")("b")("c").order(0)(null)(2).get()}).toThrow("input = null is null");
    expect(() => {gather("a")("b")("c").order(0)(1)(null).get()}).toThrow("input = null is null");

    expect(() => {gather("a")("b")("c").order("0a")(1)(2).get()}).toThrow("input = 0a is not the integer number");
    expect(() => {gather("a")("b")("c").order(0)("1b")(2).get()}).toThrow("input = 1b is not the integer number");
    expect(() => {gather("a")("b")("c").order(0)(1)("2c").get()}).toThrow("input = 2c is not the integer number");

    //regular cases
    expect(gather("a")("b")("c").order(0)(1)(2).get()).toBe("abc");
    expect(gather("a")("b")("c").order(2)(1)(0).get()).toBe("cba");
    expect(gather("e")("l")("o")("l")("!")("h").order(5)(0)(1)(3)(2)(4).get()).toBe("hello!");
});