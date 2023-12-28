import './App.css';
import {Routes, Route} from "react-router-dom";

import MainLayout from "./components/layouts/MainLayout";
import LoginLayout from "./components/layouts/LoginLayout";
import ListLayout from "./components/layouts/ListLayout";
import LoginPage from "./components/pages/LoginPage"
import Certificates from "./components/pages/Certificates";

/**
 * Main component, used for pages' routing
 *
 * @returns {JSX.Element}
 * @constructor
 */
function App() {

    /**
     * Creates JSX element based on defined paths components
     */
    return (
        <div className="App">
            <Routes>
                <Route path="/" element={<MainLayout/>}></Route>
                <Route path="/" element={<ListLayout/>}>
                    <Route path="/certificates" element={<Certificates/>}></Route>
                </Route>
                <Route path="/" element={<LoginLayout/>}>
                    <Route path="/login" element={<LoginPage/>}></Route>
                </Route>
            </Routes>
        </div>
    );
}

export default App;
