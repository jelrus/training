package com.epam.esm.model.facade.impl;

import com.epam.esm.exception.types.InputException;
import com.epam.esm.model.service.interfaces.entity.GiftCertificateService;
import com.epam.esm.persistence.entity.impl.GiftCertificate;
import com.epam.esm.persistence.entity.impl.Tag;
import com.epam.esm.utils.date.IsoDateFormatter;
import com.epam.esm.utils.search.response.DataResponse;
import com.epam.esm.utils.search.response.SearchParamResponse;
import com.epam.esm.utils.verifiers.GiftCertificateDtoVerifier;
import com.epam.esm.view.dto.request.impl.GiftCertificateDtoRequest;
import com.epam.esm.view.dto.response.impl.GiftCertificateDtoResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.model.facade.suppliers.FacadeObjectsSupplier.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateFacadeImplTest {

    @Mock
    private GiftCertificateService giftCertificateService;

    @InjectMocks
    private GiftCertificateFacadeImpl giftCertificateFacade;

    @Test
    public void willCreateWithNullTagsProperDto() {
        GiftCertificateDtoRequest gcDtoReq = generateProperDtoRequestNullTags();
        GiftCertificate giftCertificate = generateProperGiftCertificateNullTags();
        GiftCertificateDtoResponse gcDtoResp = generateProperGiftCertificateResponse(giftCertificate);
        when(giftCertificateService.create(GiftCertificateDtoVerifier.verifyCreate(gcDtoReq)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.create(gcDtoReq);
        Assertions.assertEquals(gcDtoResp, result);
    }

    @Test
    public void willCreateWithEmptyTagsProperDto() {
        GiftCertificateDtoRequest gcDtoReq = generateProperDtoRequestEmptyTags();
        GiftCertificate giftCertificate = generateProperGiftCertificateEmptyTags();
        GiftCertificateDtoResponse gcDtoResp = generateProperGiftCertificateResponse(giftCertificate);
        when(giftCertificateService.create(GiftCertificateDtoVerifier.verifyCreate(gcDtoReq)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.create(gcDtoReq);
        Assertions.assertEquals(gcDtoResp, result);
    }

    @Test
    public void willCreateWithTagsProperDto() {
        GiftCertificateDtoRequest gcDtoReq = generateProperDtoRequestWithTags();
        GiftCertificate giftCertificate = generateProperGiftCertificateWithTags();
        GiftCertificateDtoResponse gcDtoResp = generateProperGiftCertificateResponse(giftCertificate);
        when(giftCertificateService.create(GiftCertificateDtoVerifier.verifyCreate(gcDtoReq)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.create(gcDtoReq);
        Assertions.assertEquals(gcDtoResp, result);
    }

    @Test
    public void willThrowOnCorruptedNameNullCreate() {
        GiftCertificateDtoRequest gcDtoReq = generateNameCorruptedNullDtoRequest();
        Assertions.assertThrows(InputException.class, () -> giftCertificateFacade.create(gcDtoReq));
    }

    @Test
    public void willThrowOnCorruptedNameEmptyCreate() {
        GiftCertificateDtoRequest gcDtoReq = generateNameCorruptedEmptyDtoRequest();
        Assertions.assertThrows(InputException.class, () -> giftCertificateFacade.create(gcDtoReq));
    }

    @Test
    public void willThrowOnCorruptedNameBlankCreate() {
        GiftCertificateDtoRequest gcDtoReq = generateNameCorruptedBlankDtoRequest();
        Assertions.assertThrows(InputException.class, () -> giftCertificateFacade.create(gcDtoReq));
    }

    @Test
    public void willThrowOnCorruptedDescriptionNullCreate() {
        GiftCertificateDtoRequest gcDtoReq = generateDescriptionCorruptedNullDtoRequest();
        Assertions.assertThrows(InputException.class, () -> giftCertificateFacade.create(gcDtoReq));
    }

    @Test
    public void willThrowOnCorruptedDescriptionEmptyCreate() {
        GiftCertificateDtoRequest gcDtoReq = generateDescriptionCorruptedEmptyDtoRequest();
        Assertions.assertThrows(InputException.class, () -> giftCertificateFacade.create(gcDtoReq));
    }

    @Test
    public void willThrowOnCorruptedDescriptionBlankCreate() {
        GiftCertificateDtoRequest gcDtoReq = generateDescriptionCorruptedBlankDtoRequest();
        Assertions.assertThrows(InputException.class, () -> giftCertificateFacade.create(gcDtoReq));
    }

    @Test
    public void willThrowOnCorruptedPriceNullCreate() {
        GiftCertificateDtoRequest gcDtoReq = generatePriceCorruptedNullDtoRequest();
        Assertions.assertThrows(InputException.class, () -> giftCertificateFacade.create(gcDtoReq));
    }

    @Test
    public void willThrowOnCorruptedPriceLessThanZeroCreate() {
        GiftCertificateDtoRequest gcDtoReq = generatePriceCorruptedLessThanZeroDtoRequest();
        Assertions.assertThrows(InputException.class, () -> giftCertificateFacade.create(gcDtoReq));
    }

    @Test
    public void willThrowOnCorruptedDurationNullCreate() {
        GiftCertificateDtoRequest gcDtoReq = generateDurationCorruptedNullDtoRequest();
        Assertions.assertThrows(InputException.class, () -> giftCertificateFacade.create(gcDtoReq));
    }

    @Test
    public void willThrowOnCorruptedDurationLessThanZeroCreate() {
        GiftCertificateDtoRequest gcDtoReq = generateDurationCorruptedLessThanZeroDtoRequest();
        Assertions.assertThrows(InputException.class, () -> giftCertificateFacade.create(gcDtoReq));
    }

    @Test
    public void willAdjustOnCorruptedCreateDateNullCreate() {
        GiftCertificateDtoRequest gcDtoReq = generateCreateDateCorruptedNullDtoRequest();
        GiftCertificate giftCertificate = giftCertificateFromRequest(gcDtoReq);
        when(giftCertificateService.create(GiftCertificateDtoVerifier.verifyCreate(gcDtoReq)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.create(gcDtoReq);
        Assertions.assertNotNull(result.getCreate());
    }

    @Test
    public void willAdjustOnCorruptedCreateDateEmptyCreate() {
        GiftCertificateDtoRequest gcDtoReq = generateCreateDateCorruptedEmptyDtoRequest();
        GiftCertificate giftCertificate = giftCertificateFromRequest(gcDtoReq);
        when(giftCertificateService.create(GiftCertificateDtoVerifier.verifyCreate(gcDtoReq)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.create(gcDtoReq);
        Assertions.assertFalse(StringUtils.isEmpty(result.getCreate()));
    }

    @Test
    public void willAdjustOnCorruptedCreateDateBlankCreate() {
        GiftCertificateDtoRequest gcDtoReq = generateCreateDateCorruptedBlankDtoRequest();
        GiftCertificate giftCertificate = giftCertificateFromRequest(gcDtoReq);
        when(giftCertificateService.create(GiftCertificateDtoVerifier.verifyCreate(gcDtoReq)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.create(gcDtoReq);
        Assertions.assertFalse(StringUtils.isBlank(result.getCreate()));
    }

    @Test
    public void willAdjustOnCorruptedCreateDateNotIsoFormatCreate() {
        GiftCertificateDtoRequest gcDtoReq = generateCreateDateCorruptedNotIsoFormatDtoRequest();
        GiftCertificate giftCertificate = giftCertificateFromRequest(gcDtoReq);
        when(giftCertificateService.create(GiftCertificateDtoVerifier.verifyCreate(gcDtoReq)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.create(gcDtoReq);
        Assertions.assertTrue(IsoDateFormatter.checkIfIsoDate(result.getCreate()));
    }

    @Test
    public void willAdjustOnCorruptedUpdateDateNullCreate() {
        GiftCertificateDtoRequest gcDtoReq = generateUpdateDateCorruptedNullDtoRequest();
        GiftCertificate giftCertificate = giftCertificateFromRequest(gcDtoReq);
        when(giftCertificateService.create(GiftCertificateDtoVerifier.verifyCreate(gcDtoReq)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.create(gcDtoReq);
        Assertions.assertNotNull(result.getCreate());
    }

    @Test
    public void willAdjustOnCorruptedUpdateDateEmptyCreate() {
        GiftCertificateDtoRequest gcDtoReq = generateUpdateDateCorruptedEmptyDtoRequest();
        GiftCertificate giftCertificate = giftCertificateFromRequest(gcDtoReq);
        when(giftCertificateService.create(GiftCertificateDtoVerifier.verifyCreate(gcDtoReq)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.create(gcDtoReq);
        Assertions.assertFalse(StringUtils.isEmpty(result.getUpdate()));
    }

    @Test
    public void willAdjustOnCorruptedUpdateDateBlankCreate() {
        GiftCertificateDtoRequest gcDtoReq = generateUpdateDateCorruptedBlankDtoRequest();
        GiftCertificate giftCertificate = giftCertificateFromRequest(gcDtoReq);
        when(giftCertificateService.create(GiftCertificateDtoVerifier.verifyCreate(gcDtoReq)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.create(gcDtoReq);
        Assertions.assertFalse(StringUtils.isBlank(result.getUpdate()));
    }

    @Test
    public void willAdjustOnCorruptedUpdateDateNotIsoFormatCreate() {
        GiftCertificateDtoRequest gcDtoReq = generateUpdateDateCorruptedNotIsoFormatDtoRequest();
        GiftCertificate giftCertificate = giftCertificateFromRequest(gcDtoReq);
        when(giftCertificateService.create(GiftCertificateDtoVerifier.verifyCreate(gcDtoReq)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.create(gcDtoReq);
        Assertions.assertTrue(IsoDateFormatter.checkIfIsoDate(result.getUpdate()));
    }

    @Test
    public void willFindById() {
        GiftCertificate giftCertificate = generateProperGiftCertificate();
        GiftCertificateDtoResponse gcDtoResp = new GiftCertificateDtoResponse(giftCertificate);
        when(giftCertificateService.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        GiftCertificateDtoResponse giftCertificateDtoResponse = giftCertificateFacade.findById(giftCertificate.getId());
        Assertions.assertEquals(gcDtoResp, giftCertificateDtoResponse);
    }

    @Test
    public void willUpdateWithNullTagsProperDto() {
        GiftCertificateDtoRequest gcDtoReq = generateProperDtoRequestNullTags();
        GiftCertificate giftCertificate = generateProperGiftCertificateNullTags();
        GiftCertificateDtoResponse gcDtoResp = generateProperGiftCertificateResponse(giftCertificate);
        when(giftCertificateService.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateService.update(GiftCertificateDtoVerifier.verifyUpdate(gcDtoReq, giftCertificate)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.update(gcDtoReq, giftCertificate.getId());
        Assertions.assertEquals(gcDtoResp, result);
    }

    @Test
    public void willUpdateWithEmptyTagsProperDto() {
        GiftCertificateDtoRequest gcDtoReq = generateProperDtoRequestEmptyTags();
        GiftCertificate giftCertificate = generateProperGiftCertificateEmptyTags();
        GiftCertificateDtoResponse gcDtoResp = generateProperGiftCertificateResponse(giftCertificate);
        when(giftCertificateService.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateService.update(GiftCertificateDtoVerifier.verifyUpdate(gcDtoReq, giftCertificate)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.update(gcDtoReq, giftCertificate.getId());
        Assertions.assertEquals(gcDtoResp, result);
    }

    @Test
    public void willUpdateWithTagsProperDto() {
        GiftCertificateDtoRequest gcDtoReq = generateProperDtoRequestWithTags();
        GiftCertificate giftCertificate = generateProperGiftCertificateWithTags();
        GiftCertificateDtoResponse gcDtoResp = generateProperGiftCertificateResponse(giftCertificate);
        when(giftCertificateService.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateService.update(GiftCertificateDtoVerifier.verifyUpdate(gcDtoReq, giftCertificate)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.update(gcDtoReq, giftCertificate.getId());
        Assertions.assertEquals(gcDtoResp, result);
    }

    @Test
    public void willReplaceOnCorruptedNameNullUpdate() {
        GiftCertificateDtoRequest gcDtoReq = generateNameCorruptedNullDtoRequest();
        GiftCertificate giftCertificate = generateProperGiftCertificate();
        GiftCertificateDtoResponse gcDtoResp = generateProperGiftCertificateResponse(giftCertificate);
        when(giftCertificateService.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateService.update(GiftCertificateDtoVerifier.verifyUpdate(gcDtoReq, giftCertificate)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.update(gcDtoReq, giftCertificate.getId());
        Assertions.assertEquals(gcDtoResp, result);
    }

    @Test
    public void willReplaceOnCorruptedNameEmptyUpdate() {
        GiftCertificateDtoRequest gcDtoReq = generateNameCorruptedEmptyDtoRequest();
        GiftCertificate giftCertificate = generateProperGiftCertificate();
        GiftCertificateDtoResponse gcDtoResp = generateProperGiftCertificateResponse(giftCertificate);
        when(giftCertificateService.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateService.update(GiftCertificateDtoVerifier.verifyUpdate(gcDtoReq, giftCertificate)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.update(gcDtoReq, giftCertificate.getId());
        Assertions.assertEquals(gcDtoResp, result);
    }

    @Test
    public void willReplaceOnCorruptedNameBlankUpdate() {
        GiftCertificateDtoRequest gcDtoReq = generateNameCorruptedBlankDtoRequest();
        GiftCertificate giftCertificate = generateProperGiftCertificate();
        GiftCertificateDtoResponse gcDtoResp = generateProperGiftCertificateResponse(giftCertificate);
        when(giftCertificateService.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateService.update(GiftCertificateDtoVerifier.verifyUpdate(gcDtoReq, giftCertificate)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.update(gcDtoReq, giftCertificate.getId());
        Assertions.assertEquals(gcDtoResp, result);
    }

    @Test
    public void willReplaceOnCorruptedDescriptionNullUpdate() {
        GiftCertificateDtoRequest gcDtoReq = generateDescriptionCorruptedNullDtoRequest();
        GiftCertificate giftCertificate = generateProperGiftCertificate();
        GiftCertificateDtoResponse gcDtoResp = generateProperGiftCertificateResponse(giftCertificate);
        when(giftCertificateService.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateService.update(GiftCertificateDtoVerifier.verifyUpdate(gcDtoReq, giftCertificate)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.update(gcDtoReq, giftCertificate.getId());
        Assertions.assertEquals(gcDtoResp, result);
    }

    @Test
    public void willReplaceOnCorruptedDescriptionEmptyUpdate() {
        GiftCertificateDtoRequest gcDtoReq = generateDescriptionCorruptedEmptyDtoRequest();
        GiftCertificate giftCertificate = generateProperGiftCertificate();
        GiftCertificateDtoResponse gcDtoResp = generateProperGiftCertificateResponse(giftCertificate);
        when(giftCertificateService.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateService.update(GiftCertificateDtoVerifier.verifyUpdate(gcDtoReq, giftCertificate)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.update(gcDtoReq, giftCertificate.getId());
        Assertions.assertEquals(gcDtoResp, result);
    }

    @Test
    public void willReplaceOnCorruptedDescriptionBlankUpdate() {
        GiftCertificateDtoRequest gcDtoReq = generateDescriptionCorruptedBlankDtoRequest();
        GiftCertificate giftCertificate = generateProperGiftCertificate();
        GiftCertificateDtoResponse gcDtoResp = generateProperGiftCertificateResponse(giftCertificate);
        when(giftCertificateService.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateService.update(GiftCertificateDtoVerifier.verifyUpdate(gcDtoReq, giftCertificate)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.update(gcDtoReq, giftCertificate.getId());
        Assertions.assertEquals(gcDtoResp, result);
    }

    @Test
    public void willReplaceOnCorruptedPriceNullUpdate() {
        GiftCertificateDtoRequest gcDtoReq = generatePriceCorruptedNullDtoRequest();
        GiftCertificate giftCertificate = generateProperGiftCertificate();
        GiftCertificateDtoResponse gcDtoResp = generateProperGiftCertificateResponse(giftCertificate);
        when(giftCertificateService.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateService.update(GiftCertificateDtoVerifier.verifyUpdate(gcDtoReq, giftCertificate)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.update(gcDtoReq, giftCertificate.getId());
        Assertions.assertEquals(gcDtoResp, result);
    }

    @Test
    public void willReplaceOnCorruptedPriceLessThanZeroUpdate() {
        GiftCertificateDtoRequest gcDtoReq = generatePriceCorruptedLessThanZeroDtoRequest();
        GiftCertificate giftCertificate = generateProperGiftCertificate();
        GiftCertificateDtoResponse gcDtoResp = generateProperGiftCertificateResponse(giftCertificate);
        when(giftCertificateService.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateService.update(GiftCertificateDtoVerifier.verifyUpdate(gcDtoReq, giftCertificate)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.update(gcDtoReq, giftCertificate.getId());
        Assertions.assertEquals(gcDtoResp, result);
    }

    @Test
    public void willReplaceOnCorruptedDurationNullUpdate() {
        GiftCertificateDtoRequest gcDtoReq = generateDurationCorruptedNullDtoRequest();
        GiftCertificate giftCertificate = generateProperGiftCertificate();
        GiftCertificateDtoResponse gcDtoResp = generateProperGiftCertificateResponse(giftCertificate);
        when(giftCertificateService.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateService.update(GiftCertificateDtoVerifier.verifyUpdate(gcDtoReq, giftCertificate)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.update(gcDtoReq, giftCertificate.getId());
        Assertions.assertEquals(gcDtoResp, result);
    }

    @Test
    public void willReplaceOnCorruptedDurationLessThanZeroUpdate() {
        GiftCertificateDtoRequest gcDtoReq = generateDurationCorruptedLessThanZeroDtoRequest();
        GiftCertificate giftCertificate = generateProperGiftCertificate();
        GiftCertificateDtoResponse gcDtoResp = generateProperGiftCertificateResponse(giftCertificate);
        when(giftCertificateService.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateService.update(GiftCertificateDtoVerifier.verifyUpdate(gcDtoReq, giftCertificate)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.update(gcDtoReq, giftCertificate.getId());
        Assertions.assertEquals(gcDtoResp, result);
    }

    @Test
    public void willAdjustOnCorruptedCreateDateNullUpdate() {
        GiftCertificateDtoRequest gcDtoReq = generateCreateDateCorruptedNullDtoRequest();
        GiftCertificate giftCertificate = giftCertificateFromRequest(gcDtoReq);
        when(giftCertificateService.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateService.update(GiftCertificateDtoVerifier.verifyUpdate(gcDtoReq, giftCertificate)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.update(gcDtoReq, giftCertificate.getId());
        Assertions.assertNotNull(result.getCreate());
    }

    @Test
    public void willAdjustOnCorruptedCreateDateEmptyUpdate() {
        GiftCertificateDtoRequest gcDtoReq = generateCreateDateCorruptedEmptyDtoRequest();
        GiftCertificate giftCertificate = giftCertificateFromRequest(gcDtoReq);
        when(giftCertificateService.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateService.update(GiftCertificateDtoVerifier.verifyUpdate(gcDtoReq, giftCertificate)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.update(gcDtoReq, giftCertificate.getId());
        Assertions.assertFalse(StringUtils.isEmpty(result.getCreate()));
    }

    @Test
    public void willAdjustOnCorruptedCreateDateBlankUpdate() {
        GiftCertificateDtoRequest gcDtoReq = generateCreateDateCorruptedBlankDtoRequest();
        GiftCertificate giftCertificate = giftCertificateFromRequest(gcDtoReq);
        when(giftCertificateService.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateService.update(GiftCertificateDtoVerifier.verifyUpdate(gcDtoReq, giftCertificate)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.update(gcDtoReq, giftCertificate.getId());
        Assertions.assertFalse(StringUtils.isBlank(result.getCreate()));
    }

    @Test
    public void willAdjustOnCorruptedCreateDateNotIsoFormatUpdate() {
        GiftCertificateDtoRequest gcDtoReq = generateCreateDateCorruptedNotIsoFormatDtoRequest();
        GiftCertificate giftCertificate = giftCertificateFromRequest(gcDtoReq);
        when(giftCertificateService.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateService.update(GiftCertificateDtoVerifier.verifyUpdate(gcDtoReq, giftCertificate)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.update(gcDtoReq, giftCertificate.getId());
        Assertions.assertTrue(IsoDateFormatter.checkIfIsoDate(result.getCreate()));
    }

    @Test
    public void willAdjustOnCorruptedUpdateDateNullUpdate() {
        GiftCertificateDtoRequest gcDtoReq = generateUpdateDateCorruptedNullDtoRequest();
        GiftCertificate giftCertificate = giftCertificateFromRequest(gcDtoReq);
        when(giftCertificateService.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateService.update(GiftCertificateDtoVerifier.verifyUpdate(gcDtoReq, giftCertificate)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.update(gcDtoReq, giftCertificate.getId());
        Assertions.assertNotNull(result.getCreate());
    }

    @Test
    public void willAdjustOnCorruptedUpdateDateEmptyUpdate() {
        GiftCertificateDtoRequest gcDtoReq = generateUpdateDateCorruptedEmptyDtoRequest();
        GiftCertificate giftCertificate = giftCertificateFromRequest(gcDtoReq);
        when(giftCertificateService.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateService.update(GiftCertificateDtoVerifier.verifyUpdate(gcDtoReq, giftCertificate)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.update(gcDtoReq, giftCertificate.getId());
        Assertions.assertFalse(StringUtils.isEmpty(result.getUpdate()));
    }

    @Test
    public void willAdjustOnCorruptedUpdateDateBlankUpdate() {
        GiftCertificateDtoRequest gcDtoReq = generateUpdateDateCorruptedBlankDtoRequest();
        GiftCertificate giftCertificate = giftCertificateFromRequest(gcDtoReq);
        when(giftCertificateService.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateService.update(GiftCertificateDtoVerifier.verifyUpdate(gcDtoReq, giftCertificate)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.update(gcDtoReq, giftCertificate.getId());
        Assertions.assertFalse(StringUtils.isBlank(result.getUpdate()));
    }

    @Test
    public void willAdjustOnCorruptedUpdateDateNotIsoFormatUpdate() {
        GiftCertificateDtoRequest gcDtoReq = generateUpdateDateCorruptedNotIsoFormatDtoRequest();
        GiftCertificate giftCertificate = giftCertificateFromRequest(gcDtoReq);
        when(giftCertificateService.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateService.update(GiftCertificateDtoVerifier.verifyUpdate(gcDtoReq, giftCertificate)))
                .thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.update(gcDtoReq, giftCertificate.getId());
        Assertions.assertTrue(IsoDateFormatter.checkIfIsoDate(result.getUpdate()));
    }

    @Test
    public void willDelete() {
        GiftCertificateDtoRequest gcDtoReq = generateProperDtoRequest();
        GiftCertificate giftCertificate = giftCertificateFromRequest(gcDtoReq);
        GiftCertificateDtoResponse gcDtoResp = generateProperGiftCertificateResponse(giftCertificate);
        when(giftCertificateService.delete(giftCertificate.getId())).thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.delete(giftCertificate.getId());
        Assertions.assertEquals(gcDtoResp, result);
    }

    @Test
    public void willFindAll() {
        GiftCertificate giftCertificate = generateProperGiftCertificateEmptyTags();
        Set<GiftCertificate> giftCertificates = new LinkedHashSet<>(Collections.singletonList(giftCertificate));
        when(giftCertificateService.findAll()).thenReturn(giftCertificates);
        Set<GiftCertificateDtoResponse> result = giftCertificateFacade.findAll();
        Assertions.assertFalse(result.isEmpty());

        GiftCertificateDtoResponse gcDtoResp = generateProperGiftCertificateResponse(giftCertificate);
        Set<GiftCertificateDtoResponse> converted = new LinkedHashSet<>(Collections.singletonList(gcDtoResp));
        Assertions.assertEquals(converted, result);
    }

    @Test
    public void willFindAllTagged() {
        GiftCertificate giftCertificate = generateProperGiftCertificateWithTags();
        Set<GiftCertificate> giftCertificates = new LinkedHashSet<>(Collections.singletonList(giftCertificate));
        when(giftCertificateService.findAllTagged()).thenReturn(giftCertificates);
        Set<GiftCertificateDtoResponse> result = giftCertificateFacade.findAllTagged();
        Assertions.assertFalse(result.isEmpty());

        GiftCertificateDtoResponse gcDtoResp = generateProperGiftCertificateResponse(giftCertificate);
        Set<GiftCertificateDtoResponse> converted = new LinkedHashSet<>(Collections.singletonList(gcDtoResp));
        Assertions.assertEquals(converted, result);
    }

    @Test
    public void willFindAllSearch() {
        WebRequest webRequest = mock(WebRequest.class);
        SearchParamResponse<GiftCertificate> gcDtoResp = new SearchParamResponse<>();
        when(giftCertificateService.findAllSearch(generateSearchParamRequest()))
                .thenReturn(generateSearchParamResponse());
        DataResponse<GiftCertificateDtoResponse> result = giftCertificateFacade.findAllSearch(webRequest);
        Assertions.assertEquals(gcDtoResp.getFullParams(), result.getFullParams());
        Assertions.assertEquals(gcDtoResp.getItems().stream()
                                                    .map(GiftCertificateDtoResponse::new)
                                                    .collect(Collectors.toSet()),
                                result.getItems());
    }

    @Test
    public void willAddTag() {
        GiftCertificate giftCertificate = generateProperGiftCertificate();
        Tag tag = generateProperTag();
        GiftCertificateDtoResponse gcDtoResp = new GiftCertificateDtoResponse(giftCertificate);
        when(giftCertificateService.addTag(giftCertificate.getId(), tag.getId())).thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.addTag(giftCertificate.getId(), tag.getId());
        Assertions.assertEquals(gcDtoResp, result);
    }

    @Test
    public void willDeleteTag() {
        GiftCertificate giftCertificate = generateProperGiftCertificate();
        Tag tag = generateProperTag();
        GiftCertificateDtoResponse gcDtoResp = new GiftCertificateDtoResponse(giftCertificate);
        when(giftCertificateService.deleteTag(giftCertificate.getId(), tag.getId())).thenReturn(giftCertificate);
        GiftCertificateDtoResponse result = giftCertificateFacade.deleteTag(giftCertificate.getId(), tag.getId());
        Assertions.assertEquals(gcDtoResp, result);
    }
}