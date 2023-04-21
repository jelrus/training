package com.epam.esm.facade.interfaces.general;

import com.epam.esm.view.dto.request.DtoRequest;
import com.epam.esm.view.dto.response.DtoResponse;

public interface CrudFacade<Q extends DtoRequest, A extends DtoResponse, T> extends CrdFacade<Q,A,T> {

    A update(Q request, T id);
}