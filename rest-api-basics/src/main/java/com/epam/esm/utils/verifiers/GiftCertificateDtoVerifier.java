package com.epam.esm.utils.verifiers;

import com.epam.esm.persistence.entity.impl.GiftCertificate;
import com.epam.esm.exception.types.InputException;
import com.epam.esm.utils.date.IsoDateFormatter;
import com.epam.esm.view.dto.request.impl.GiftCertificateDtoRequest;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * GiftCertificateDtoVerifier is the utility class, which used for dto request verification and dto conversion
 */
public final class GiftCertificateDtoVerifier {

    /**
     * Default constructor
     */
    private GiftCertificateDtoVerifier() {}

    /**
     * Verifies dto request for create operation and returns GiftCertificate object if dto contains proper data
     * Adjusts dates if they are null
     *
     * @param dto requested dto
     * @return {@code GiftCertificate} the result of verification
     */
    public static GiftCertificate verifyCreate(GiftCertificateDtoRequest dto) {
        adjustDatesOnCreate(dto);
        return verify(dto);
    }

    /**
     * Verifies dto request for update operation and returns GiftCertificate object if dto contains proper data
     * Adjusts fields if they are null, empty or blank by copying them from pre update object
     *
     * @param dto requested dto
     * @param preUpdate requested pre update object
     * @return {@code GiftCertificate} the result of verification
     */
    public static GiftCertificate verifyUpdate(GiftCertificateDtoRequest dto, GiftCertificate preUpdate) {
        adjustDtoFieldsOnUpdate(preUpdate, dto);
        GiftCertificate updated = verify(dto);
        updated.setId(preUpdate.getId());
        return updated;
    }

    /**
     * Verifies dto request fields and requested tag set for it
     * Used for dto response construction, if fields contain proper data
     * Will throw InputException, if one of the fields is inappropriate for GiftCertificate object
     *
     * @param dto requested dto
     * @return {@code GiftCertificate} the result of verification
     */
    private static GiftCertificate verify(GiftCertificateDtoRequest dto) {
        if (checkGiftCertificateDtoFields(dto)) {
            GiftCertificate gCert = new GiftCertificate();

            if (TagDtoVerifier.checkDtoTags(dto.getTags())) {
                gCert.setTags(TagDtoVerifier.convertToTags(dto.getTags()));
            }

            passFieldsFromGiftCertificateDto(dto, gCert);
            return gCert;
        } else {
            throw new InputException("Gift Certificate fields was corrupted or wrong format used");
        }
    }

    /**
     * Checks all fields of dto request except for tags set
     *
     * @param dto requested dto
     * @return {@code true} if fields contain proper data
     */
    private static boolean checkGiftCertificateDtoFields(GiftCertificateDtoRequest dto) {
        return dto != null
               && checkName(dto.getName())
               && checkDescription(dto.getDescription())
               && checkPrice(dto.getPrice())
               && checkDuration(dto.getDuration())
               && checkCreate(dto.getCreate())
               && checkUpdate(dto.getUpdate());
    }

    /**
     * Checks if name field contains proper data
     *
     * @param name requested name value
     * @return {@code true} if name contains proper data
     */
    private static boolean checkName(String name) {
        return !StringUtils.isBlank(name) && name.length() <= 255;
    }

    /**
     * Checks if description field contains proper data
     *
     * @param description requested description value
     * @return {@code true} if description contains proper data
     */
    private static boolean checkDescription(String description) {
        return !StringUtils.isBlank(description);
    }

    /**
     * Checks if price field contains proper data
     *
     * @param price requested price value
     * @return {@code true} if price contains proper data
     */
    private static boolean checkPrice(BigDecimal price) {
        return price != null && price.doubleValue() > 0;
    }

    /**
     * Checks if duration field contains proper data
     *
     * @param duration requested duration value
     * @return {@code true} if duration contains proper data
     */
    private static boolean checkDuration(Integer duration) {
        return duration != null && duration > 0;
    }

    /**
     * Checks if create field contains proper data
     *
     * @param create requested create value
     * @return {@code true} if create contains proper data
     */
    private static boolean checkCreate(String create) {
        return !StringUtils.isBlank(create) && IsoDateFormatter.checkIfIsoDate(create);
    }

    /**
     * Checks if update field contains proper data
     *
     * @param update requested update value
     * @return {@code true} if update contains proper data
     */
    private static boolean checkUpdate(String update) {
        return !StringUtils.isBlank(update) && IsoDateFormatter.checkIfIsoDate(update);
    }

    /**
     * Adjusts create date:
     * - if not null, empty, blank and compliant wth ISO8601 date format - returns this string
     * - if else - creates current new date
     *
     * @param create requested create value
     * @return {@code String} result of create date adjusting
     */
    private static String adjustCreateIfNull(String create) {
        return checkCreate(create) ? create
                                   : IsoDateFormatter.dateToString(new Date(System.currentTimeMillis()));
    }

    /**
     * Adjusts update date:
     * - if not null, empty, blank and compliant wth ISO8601 date format - returns this string
     * - if else - creates current new date
     *
     * @param update requested update value
     * @return {@code String} result of update date adjusting
     */
    private static String adjustUpdateIfNull(String update) {
        return checkUpdate(update) ? update
                                   : IsoDateFormatter.dateToString(new Date(System.currentTimeMillis()));
    }

    /**
     * Adjusts dates for create operation
     *
     * @param dto requested dto
     */
    private static void adjustDatesOnCreate(GiftCertificateDtoRequest dto) {
        dto.setCreate(adjustCreateIfNull(dto.getCreate()));
        dto.setUpdate(adjustUpdateIfNull(dto.getUpdate()));
    }

    /**
     * Copies fields from GiftCertificateDtoRequest object to GiftCertificate
     *
     * @param gcReq requested dto
     * @param gc requested object
     */
    private static void passFieldsFromGiftCertificateDto(GiftCertificateDtoRequest gcReq, GiftCertificate gc) {
        gc.setName(gcReq.getName());
        gc.setDescription(gcReq.getDescription());
        gc.setPrice(gcReq.getPrice());
        gc.setDuration(gcReq.getDuration());
        gc.setCreate(IsoDateFormatter.stringToDate(gcReq.getCreate()));
        gc.setUpdate(IsoDateFormatter.stringToDate(gcReq.getUpdate()));
    }

    /**
     * Adjusts all fields, except set of tags, for GiftCertificateDtoRequest object for update operation
     * If requested field doesn't meet check's conditions copies field from pre update object
     *
     * @param gc pre update object
     * @param gcReq requested dto
     */
    private static void adjustDtoFieldsOnUpdate(GiftCertificate gc, GiftCertificateDtoRequest gcReq) {
        if (!checkName(gcReq.getName())) {
            gcReq.setName(gc.getName());
        }

        if (!checkDescription(gcReq.getDescription())) {
            gcReq.setDescription(gc.getDescription());
        }

        if (!checkPrice(gcReq.getPrice())) {
            gcReq.setPrice(gc.getPrice());
        }

        if (!checkDuration(gcReq.getDuration())) {
            gcReq.setDuration(gc.getDuration());
        }

        if (!checkCreate(gcReq.getCreate())) {
            gcReq.setCreate(IsoDateFormatter.dateToString(gc.getCreate()));
        }

        if (!checkUpdate(gcReq.getUpdate())) {
            gcReq.setUpdate(IsoDateFormatter.dateToString(new Date(System.currentTimeMillis())));
        }
    }

    /**
     * Checks set of GiftCertificateDtoRequest if null, empty or contains null value
     *
     * @param dtoGiftCerts set of gift certificates dto requests
     * @return {@code true} if requested set meets conditions
     */
    static boolean checkDtoGiftCertificates(Set<GiftCertificateDtoRequest> dtoGiftCerts) {
        return dtoGiftCerts != null && !dtoGiftCerts.isEmpty() && !dtoGiftCerts.contains(null);
    }

    /**
     * Converts set of GiftCertificateDtoRequest to set of GiftCertificate
     *
     * @param gCertsReq requested set
     * @return {@code Set<GiftCertificate>} result of conversion
     */
    static Set<GiftCertificate> convertToGiftCertificates(Set<GiftCertificateDtoRequest> gCertsReq) {
        Set<GiftCertificate> gCerts = new LinkedHashSet<>();

        for (GiftCertificateDtoRequest gcReq: gCertsReq) {
            GiftCertificateDtoVerifier.adjustDatesOnCreate(gcReq);
            addGiftCertificateIfCorrect(gCerts, gcReq);
        }

        return gCerts;
    }

    /**
     * Checks GiftCertificateDtoRequest object for data requirements and adds it to requested set
     *
     * @param gCerts requested set
     * @param gcReq requested dto
     */
    private static void addGiftCertificateIfCorrect(Set<GiftCertificate> gCerts, GiftCertificateDtoRequest gcReq) {
        if (GiftCertificateDtoVerifier.checkGiftCertificateDtoFields(gcReq)) {
            gCerts.add(convertGiftCertificate(gcReq));
        } else {
            throw new InputException("Gift Certificate fields was corrupted or wrong format used");
        }
    }

    /**
     * Converts single GiftCertificateDtoRequest to GiftCertificate
     *
     * @param gCertReq requested dto
     * @return {@code GiftCertificate} result of conversion
     */
    private static GiftCertificate convertGiftCertificate(GiftCertificateDtoRequest gCertReq) {
        GiftCertificate gCert = new GiftCertificate();
        GiftCertificateDtoVerifier.passFieldsFromGiftCertificateDto(gCertReq, gCert);
        return gCert;
    }
}