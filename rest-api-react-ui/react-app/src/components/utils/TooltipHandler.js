import {Tooltip as ReactTooltip} from "react-tooltip";
import React from "react";

/**
 * Applies tooltip for specified html element with specified message
 *
 * @param elementId
 * @param tooltipId
 * @param message
 * @returns {JSX.Element}
 */
function tooltip(elementId, tooltipId, message) {
    if (document.getElementById(elementId) !== null) {
        return (
            <ReactTooltip id={tooltipId}
                          place="top"
                          type="dark"
                          effect="solid"
                          content={message}/>
        )
    }
}

export {tooltip}