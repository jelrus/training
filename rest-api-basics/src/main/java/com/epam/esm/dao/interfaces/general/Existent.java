package com.epam.esm.dao.interfaces.general;

public interface Existent<T> {

    Boolean existById(T id);
}