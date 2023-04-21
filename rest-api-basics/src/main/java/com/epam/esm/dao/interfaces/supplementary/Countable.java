package com.epam.esm.dao.interfaces.supplementary;

import java.sql.ResultSet;

public interface Countable {

    Integer count();

    //TODO: convert to default method ??
    Integer getCountFromResultSet(ResultSet rs, String countColumn);
}