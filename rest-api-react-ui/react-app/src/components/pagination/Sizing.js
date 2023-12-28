import React from "react";

import certificates from "../../static/css/certificates.module.css";

/**
 * Paging is the component which handles all functionality related with sizing
 *
 * @param pageSize
 * @param event
 * @param sizes
 * @returns {JSX.Element}
 * @constructor
 */
const Sizing = ({pageSize, event, sizes}) => {

    /**
     * Creates JSX element of sizing element
     */
    return (
        <div className={certificates.size}>
            <label>
                <select id="size" value={pageSize} onChange={event}>
                    {
                        sizes?.map(s => {
                            return (
                                <option key={s} value={s}>{s}</option>
                            )
                        })
                    }
                </select>
            </label>
        </div>
    )
}

export default Sizing;