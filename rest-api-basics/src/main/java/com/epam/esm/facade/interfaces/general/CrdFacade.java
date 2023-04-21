package com.epam.esm.facade.interfaces.general;

import com.epam.esm.view.dto.request.DtoRequest;
import com.epam.esm.view.dto.response.DtoResponse;

import java.util.Set;

public interface CrdFacade<Q extends DtoRequest, A extends DtoResponse, T> {

    A create(Q request);

    A findById(T id);

    A delete(T id);

    Set<A> findAll();
}