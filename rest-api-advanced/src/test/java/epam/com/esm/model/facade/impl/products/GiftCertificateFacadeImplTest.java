package epam.com.esm.model.facade.impl.products;

import epam.com.esm.model.service.impl.products.GiftCertificateServiceImpl;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.utils.search.data.AbstractDataHandler;
import epam.com.esm.utils.search.data.impl.products.GiftCertificateDataHandler;
import epam.com.esm.utils.search.data.impl.products.TagDataHandler;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import epam.com.esm.utils.verifiers.products.GiftCertificateDtoVerifier;
import epam.com.esm.view.dto.request.impl.products.GiftCertificateDtoRequest;
import epam.com.esm.view.dto.request.impl.products.GiftCertificateTagsDtoRequest;
import epam.com.esm.view.dto.response.impl.products.GiftCertificateDtoResponse;
import epam.com.esm.view.dto.response.impl.products.TagDtoResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static epam.com.esm.model.suppliers.facade.products.GiftCertificateFacadeSupplier.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateFacadeImplTest {

    @Mock
    public GiftCertificateServiceImpl gcService;

    @Mock
    public GiftCertificateDtoVerifier gcDtoVerifier;

    @InjectMocks
    public GiftCertificateFacadeImpl gcFacade;

    @Test
    public void willCreate() {
        GiftCertificateDtoRequest gcReq = getProperGiftCertificateDtoRequest();
        GiftCertificate gc = getProperGiftCertificate();
        GiftCertificateDtoResponse gcResp = getProperGiftCertificateDtoResponse(gc);

        when(gcDtoVerifier.verifyCreate(gcReq)).thenReturn(gc);
        when(gcService.create(gc)).thenReturn(gc);

        GiftCertificateDtoResponse created = gcFacade.create(gcReq);
        Assertions.assertEquals(gcResp, created);
    }

    @Test
    public void willFindById() {
        GiftCertificate gc = getProperGiftCertificate();
        GiftCertificateDtoResponse gcResp = getProperGiftCertificateDtoResponse(gc);

        when(gcService.findById(gc.getId())).thenReturn(gc);

        GiftCertificateDtoResponse found = gcFacade.findById(gc.getId());
        Assertions.assertEquals(gcResp, found);
    }

    @Test
    public void willUpdate() {
        GiftCertificateDtoRequest gcReq = getProperGiftCertificateDtoRequest();
        GiftCertificate gc = getProperGiftCertificate();
        GiftCertificateDtoResponse gcResp = getProperGiftCertificateDtoResponse(gc);

        when(gcService.findById(gc.getId())).thenReturn(gc);
        when(gcDtoVerifier.verifyUpdate(gcReq, gc)).thenReturn(gc);
        when(gcService.update(gc)).thenReturn(gc);

        GiftCertificateDtoResponse updated = gcFacade.update(gcReq, gc.getId());
        Assertions.assertEquals(gcResp, updated);
    }

    @Test
    public void willDelete() {
        GiftCertificate gc = getProperGiftCertificate();
        GiftCertificateDtoResponse gcResp = getProperGiftCertificateDtoResponse(gc);

        when(gcService.delete(gc.getId())).thenReturn(gc);

        GiftCertificateDtoResponse deleted = gcFacade.delete(gc.getId());
        Assertions.assertEquals(gcResp, deleted);
    }

    @Test
    public void willFindAll() {
        WebRequest webRequest = mock(WebRequest.class);

        AbstractDataHandler<GiftCertificate, GiftCertificateDtoResponse> adh = new GiftCertificateDataHandler();
        SearchParamResponse<GiftCertificate> spResp = new SearchParamResponse<>();

        when(gcService.findAll(adh.processSearch(webRequest))).thenReturn(spResp);

        PageDataResponse<GiftCertificateDtoResponse> pdr = gcFacade.findAll(webRequest);
        Assertions.assertEquals(spResp.getFullParams(), pdr.getFullParams());
        Assertions.assertEquals(spResp.getItems()
                                      .stream()
                                      .map(GiftCertificateDtoResponse::new)
                                      .collect(Collectors.toCollection(ArrayList::new)),
                                pdr.getItems());
    }

    @Test
    public void willFindAllTagged() {
        WebRequest webRequest = mock(WebRequest.class);

        AbstractDataHandler<GiftCertificate, GiftCertificateDtoResponse> adh = new GiftCertificateDataHandler();
        SearchParamResponse<GiftCertificate> spResp = new SearchParamResponse<>();

        when(gcService.findAllTagged(adh.processSearch(webRequest))).thenReturn(spResp);

        PageDataResponse<GiftCertificateDtoResponse> pdr = gcFacade.findAllTagged(webRequest);
        Assertions.assertEquals(spResp.getFullParams(), pdr.getFullParams());
        Assertions.assertEquals(spResp.getItems()
                        .stream()
                        .map(GiftCertificateDtoResponse::new)
                        .collect(Collectors.toCollection(ArrayList::new)),
                pdr.getItems());
    }

    @Test
    public void willFindAllNotTagged() {
        WebRequest webRequest = mock(WebRequest.class);

        AbstractDataHandler<GiftCertificate, GiftCertificateDtoResponse> adh = new GiftCertificateDataHandler();
        SearchParamResponse<GiftCertificate> spResp = new SearchParamResponse<>();

        when(gcService.findAllNotTagged(adh.processSearch(webRequest))).thenReturn(spResp);

        PageDataResponse<GiftCertificateDtoResponse> pdr = gcFacade.findAllNotTagged(webRequest);
        Assertions.assertEquals(spResp.getFullParams(), pdr.getFullParams());
        Assertions.assertEquals(spResp.getItems()
                        .stream()
                        .map(GiftCertificateDtoResponse::new)
                        .collect(Collectors.toCollection(ArrayList::new)),
                pdr.getItems());
    }

    @Test
    public void willFindTags() {
        WebRequest webRequest = mock(WebRequest.class);

        GiftCertificate gc = getProperGiftCertificate();
        AbstractDataHandler<Tag, TagDtoResponse> adh = new TagDataHandler();
        SearchParamResponse<Tag> spResp = new SearchParamResponse<>();

        when(gcService.findTags(gc.getId(), adh.processSearch(webRequest))).thenReturn(spResp);

        PageDataResponse<TagDtoResponse> pdr = gcFacade.findTags(gc.getId(), webRequest);
        Assertions.assertEquals(spResp.getItems()
                                      .stream()
                                      .map(TagDtoResponse::new)
                                      .collect(Collectors.toCollection(ArrayList::new)),
                                pdr.getItems());
    }

    @Test
    public void willAddTags() {
        GiftCertificate gc = getProperGiftCertificate();
        GiftCertificateDtoResponse gcResp = getProperGiftCertificateDtoResponse(gc);
        GiftCertificateTagsDtoRequest gcReq = getProperTagsDtoRequest();

        when(gcService.findById(gc.getId())).thenReturn(gc);
        when(gcDtoVerifier.verifyTagsChanges(gcReq, gc)).thenReturn(gc);
        when(gcService.addTags(gc)).thenReturn(gc);

        GiftCertificateDtoResponse withTagsAdded = gcFacade.addTags(gc.getId(), gcReq);
        Assertions.assertEquals(gcResp, withTagsAdded);
    }

    @Test
    public void willDeleteTags() {
        GiftCertificate gc = getProperGiftCertificate();
        GiftCertificateDtoResponse gcResp = getProperGiftCertificateDtoResponse(gc);
        GiftCertificateTagsDtoRequest gcReq = getProperTagsDtoRequest();

        when(gcService.findById(gc.getId())).thenReturn(gc);
        when(gcDtoVerifier.verifyTagsChanges(gcReq, gc)).thenReturn(gc);
        when(gcService.deleteTags(gc)).thenReturn(gc);

        GiftCertificateDtoResponse withTagsAdded = gcFacade.deleteTags(gc.getId(), gcReq);
        Assertions.assertEquals(gcResp, withTagsAdded);
    }
}