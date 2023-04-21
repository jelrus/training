package com.epam.esm.view.dto.response;

public abstract class DtoResponse {

    private Long id;

    public DtoResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}