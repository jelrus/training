package com.epam.esm.dao.interfaces.general;

import java.sql.PreparedStatement;

public interface GenerateKeys<T> {

    //TODO: convert to default method ??
    T generateKey(PreparedStatement ps);
}