import axios from "axios";

/**
 * Creates connection to backend entry point
 */
export default axios.create({
    baseURL: 'http://localhost:8080'
});