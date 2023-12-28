package epam.com.esm.model.facade.impl.products;

import epam.com.esm.model.service.interfaces.entity.products.TagService;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.utils.search.data.AbstractDataHandler;
import epam.com.esm.utils.search.data.impl.products.GiftCertificateDataHandler;
import epam.com.esm.utils.search.data.impl.products.TagDataHandler;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import epam.com.esm.utils.verifiers.products.TagDtoVerifier;
import epam.com.esm.view.dto.request.impl.products.TagDtoRequest;
import epam.com.esm.view.dto.request.impl.products.TagGiftCertificatesDtoRequest;
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

import static epam.com.esm.model.suppliers.facade.products.TagFacadeSupplier.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagFacadeImplTest {

    @Mock
    public TagService tagService;

    @Mock
    public TagDtoVerifier tagDtoVerifier;

    @InjectMocks
    public TagFacadeImpl tagFacade;

    @Test
    public void willCreate() {
        //initial data
        TagDtoRequest tReq = getProperTagDtoRequest();
        Tag t = getProperTag();
        TagDtoResponse tResp = getProperTagDtoResponse(t);

        //create
        when(tagDtoVerifier.verifyOnCreate(tReq)).thenReturn(t);
        when(tagService.create(t)).thenReturn(t);

        //generate response
        TagDtoResponse created = tagFacade.create(tReq);
        Assertions.assertEquals(tResp, created);
    }

    @Test
    public void willFindById() {
        //initial data
        Tag t = getProperTag();
        TagDtoResponse tResp = getProperTagDtoResponse(t);

        //find by id
        when(tagService.findById(t.getId())).thenReturn(t);

        //generate response
        TagDtoResponse found = tagFacade.findById(t.getId());
        Assertions.assertEquals(tResp, found);
    }

    @Test
    public void willDelete() {
        //initial data
        Tag t = getProperTag();
        TagDtoResponse tResp = getProperTagDtoResponse(t);

        //delete
        when(tagService.delete(t.getId())).thenReturn(t);

        //generate response
        TagDtoResponse deleted = tagFacade.delete(t.getId());
        Assertions.assertEquals(tResp, deleted);
    }

    @Test
    public void willFindAll() {
        //mock web request
        WebRequest webRequest = mock(WebRequest.class);

        //initial data
        AbstractDataHandler<Tag, TagDtoResponse> adh = new TagDataHandler();
        SearchParamResponse<Tag> spResp = new SearchParamResponse<>();

        //find all
        when(tagService.findAll(adh.processSearch(webRequest))).thenReturn(spResp);

        //generate response
        PageDataResponse<TagDtoResponse> pdr = tagFacade.findAll(webRequest);
        Assertions.assertEquals(spResp.getFullParams(), pdr.getFullParams());
        Assertions.assertEquals(spResp.getItems()
                                      .stream()
                                      .map(TagDtoResponse::new)
                                      .collect(Collectors.toCollection(ArrayList::new)),
                                pdr.getItems());
    }

    @Test
    public void willFindAllCertificated() {
        //mock web request
        WebRequest webRequest = mock(WebRequest.class);

        //initial data
        AbstractDataHandler<Tag, TagDtoResponse> adh = new TagDataHandler();
        SearchParamResponse<Tag> spResp = new SearchParamResponse<>();

        //find all certificated
        when(tagService.findAllCertificated(adh.processSearch(webRequest))).thenReturn(spResp);

        //generate response
        PageDataResponse<TagDtoResponse> pdr = tagFacade.findAllCertificated(webRequest);
        Assertions.assertEquals(spResp.getFullParams(), pdr.getFullParams());
        Assertions.assertEquals(spResp.getItems()
                                      .stream()
                                      .map(TagDtoResponse::new)
                                      .collect(Collectors.toCollection(ArrayList::new)),
                                pdr.getItems());
    }

    @Test
    public void willFindAllNotCertificated() {
        //mock web request
        WebRequest webRequest = mock(WebRequest.class);

        //initial data
        AbstractDataHandler<Tag, TagDtoResponse> adh = new TagDataHandler();
        SearchParamResponse<Tag> spResp = new SearchParamResponse<>();

        //find all not certificated
        when(tagService.findAllNotCertificated(adh.processSearch(webRequest))).thenReturn(spResp);

        //generate response
        PageDataResponse<TagDtoResponse> pdr = tagFacade.findAllNotCertificated(webRequest);
        Assertions.assertEquals(spResp.getFullParams(), pdr.getFullParams());
        Assertions.assertEquals(spResp.getItems()
                                      .stream()
                                      .map(TagDtoResponse::new)
                                      .collect(Collectors.toCollection(ArrayList::new)),
                                pdr.getItems());
    }

    @Test
    public void willFindGiftCertificates() {
        //mock web request
        WebRequest webRequest = mock(WebRequest.class);

        //initial data
        Tag t = getProperTag();
        AbstractDataHandler<GiftCertificate, GiftCertificateDtoResponse> adh = new GiftCertificateDataHandler();
        SearchParamResponse<GiftCertificate> spResp = new SearchParamResponse<>();

        //find gift certificates
        when(tagService.findGiftCertificates(t.getId(), adh.processSearch(webRequest))).thenReturn(spResp);

        //generate response
        PageDataResponse<GiftCertificateDtoResponse> pdr = tagFacade.findGiftCertificates(t.getId(), webRequest);
        Assertions.assertEquals(spResp.getItems()
                                      .stream()
                                      .map(GiftCertificateDtoResponse::new)
                                      .collect(Collectors.toCollection(ArrayList::new)),
                                pdr.getItems());
    }

    @Test
    public void willAddTags() {
        //initial data
        TagGiftCertificatesDtoRequest gcReq = getProperGiftCertificatesDtoRequest();
        Tag t = getProperTag();
        TagDtoResponse tResp = getProperTagDtoResponse(t);

        //add gift certificates
        when(tagService.findById(t.getId())).thenReturn(t);
        when(tagDtoVerifier.verifyChanges(gcReq, t)).thenReturn(t);
        when(tagService.addGiftCertificates(t)).thenReturn(t);

        //generate response
        TagDtoResponse withGiftCertificatesAdded = tagFacade.addGiftCertificates(t.getId(), gcReq);
        Assertions.assertEquals(tResp, withGiftCertificatesAdded);
    }

    @Test
    public void willDeleteGiftCertificates() {
        //initial data
        TagGiftCertificatesDtoRequest gcReq = getProperGiftCertificatesDtoRequest();
        Tag t = getProperTag();
        TagDtoResponse tResp = getProperTagDtoResponse(t);

        //delete gift certificates
        when(tagService.findById(t.getId())).thenReturn(t);
        when(tagDtoVerifier.verifyChanges(gcReq, t)).thenReturn(t);
        when(tagService.deleteGiftCertificates(t)).thenReturn(t);

        //generate response
        TagDtoResponse withGiftCertificatesAdded = tagFacade.deleteGiftCertificates(t.getId(), gcReq);
        Assertions.assertEquals(tResp, withGiftCertificatesAdded);
    }
}