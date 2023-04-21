package com.epam.esm.dao.interfaces.supplementary;

public interface Existent<T> {

    Boolean existById(T id);
}