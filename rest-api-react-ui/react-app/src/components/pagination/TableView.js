import certificates from "../../static/css/certificates.module.css";

import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import 'font-awesome/css/font-awesome.min.css';
import * as fontawesome from "@fortawesome/fontawesome-svg-core";
import {faEye, faPenToSquare, faTrash,} from "@fortawesome/free-solid-svg-icons";
import {Tooltip} from "react-tooltip";

fontawesome.library.add(faEye, faPenToSquare, faTrash);

/**
 * TableView is the component which represent listing part of products
 *
 * @param data
 * @param viewEvent
 * @param editEvent
 * @param deleteEvent
 * @returns {*}
 * @constructor
 */
const TableView = ({data, viewEvent, editEvent, deleteEvent}) => {

    /**
     * Creates JSX element of listing part of products
     */
    return (
        data?.map(i => {
            return (
                <tr key={i.id}>
                    <td>{new Date(i.create).toLocaleString("sv-SE")}</td>
                    <td>{i.name}</td>
                    <td>
                        <div className={certificates.viewTags}>
                            {i.tags?.map(t => {
                                return (
                                    <span key={t.id}>#{t.name}</span>
                                )
                            })}
                        </div>
                    </td>
                    <td>{i.description}</td>
                    <td>{i.price}</td>
                    <td>
                        <div className={certificates.actionBtnWrapper}>
                            <button id={i.id} className={certificates.actionBtn} onClick={viewEvent}>
                                    <span data-tooltip-id={"tooltip-view-" + i.id}>
                                        <FontAwesomeIcon icon="eye"></FontAwesomeIcon>
                                    </span>
                            </button>

                            <Tooltip id={"tooltip-view-" + i.id}>View Item</Tooltip>

                            <button id={i.id} className={certificates.actionBtn} onClick={editEvent}>
                                <span data-tooltip-id={"tooltip-edit-" + i.id}>
                                    <FontAwesomeIcon icon="pen-to-square"></FontAwesomeIcon>
                                </span>
                            </button>

                            <Tooltip id={"tooltip-edit-" + i.id}>Edit Item</Tooltip>

                            <button id={i.id} className={certificates.actionBtn} onClick={deleteEvent}>
                                <span data-tooltip-id={"tooltip-delete-" + i.id}>
                                    <FontAwesomeIcon icon="trash"></FontAwesomeIcon>
                                </span>
                            </button>

                            <Tooltip id={"tooltip-delete-" + i.id}>Delete Item</Tooltip>
                        </div>
                    </td>
                </tr>
            )
        })
    )
}

export default TableView;