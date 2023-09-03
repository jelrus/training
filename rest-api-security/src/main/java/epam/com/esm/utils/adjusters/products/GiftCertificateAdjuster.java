package epam.com.esm.utils.adjusters.products;

import epam.com.esm.exception.types.InputException;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.utils.date.IsoDateFormatter;
import epam.com.esm.view.dto.request.impl.products.GiftCertificateDtoRequest;
import epam.com.esm.view.dto.request.impl.products.GiftCertificateNameDtoRequest;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static epam.com.esm.utils.adjusters.products.TagAdjuster.convertTags;

/**
 * GiftCertificateAdjuster utility class, adjust fields on gift certificate create or update operations
 */
public final class GiftCertificateAdjuster {

    /**
     * Default constructor
     */
    private GiftCertificateAdjuster() {}

    /**
     * Checks gift certificate's dto fields for correct values
     *
     * @param dto requested gift certificate dto request
     * @return {@code true} if gift certificate dto request contains correct value for fields
     */
    public static boolean checkGiftCertificateDtoFields(GiftCertificateDtoRequest dto) {
        return dto != null
                && checkName(dto.getName())
                && checkDescription(dto.getDescription())
                && checkPrice(dto.getPrice())
                && checkDuration(dto.getDuration())
                && checkCreate(dto.getCreate())
                && checkUpdate(dto.getUpdate());
    }

    /**
     * Copies fields from gift certificate dto request to gift certificate
     *
     * @param gcReq requested gift certificate dto request
     * @param gc requested gift certificate
     */
    public static void passFieldsFromGiftCertificateDto(GiftCertificateDtoRequest gcReq, GiftCertificate gc) {
        gc.setName(gcReq.getName());
        gc.setDescription(gcReq.getDescription());
        gc.setPrice(gcReq.getPrice());
        gc.setDuration(gcReq.getDuration());
        gc.setCreate(IsoDateFormatter.stringToDate(gcReq.getCreate()));
        gc.setUpdate(IsoDateFormatter.stringToDate(gcReq.getUpdate()));
    }

    /**
     * Adjusts dates on create, sets now datetime if create/update are null
     *
     * @param dto requested gift certificate dto request
     */
    public static void adjustDatesOnCreate(GiftCertificateDtoRequest dto) {
        dto.setCreate(adjustCreateIfNull(dto.getCreate()));
        dto.setUpdate(adjustUpdateIfNull(dto.getUpdate()));
    }

    /**
     * Copies missing fields from gift certificate to gift certificate dto request
     *
     * @param gc requested gift certificate
     * @param gcReq requested gift certificate dto request
     */
    public static void adjustDtoFieldsOnUpdate(GiftCertificate gc, GiftCertificateDtoRequest gcReq) {
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
            gcReq.setUpdate(IsoDateFormatter.dateToString(LocalDateTime.now()));
        }

        if (!checkTagsNull(gcReq)) {
            gcReq.setTags(convertTags(gc));
        }
    }

    /**
     * Converts from gift certificate name dto requests to gift certificates
     *
     * @param gcReqs gift certificate name dto requests
     * @return {@code List<GiftCertificate>} converted gift certificates
     */
    public static List<GiftCertificate> convertToGiftCertificates(List<GiftCertificateNameDtoRequest> gcReqs) {
        List<GiftCertificate> gCerts = new ArrayList<>();

        for (GiftCertificateNameDtoRequest gcReq: gcReqs) {
            checkName(gcReq);
            gCerts.add(convertToGiftCertificate(gcReq));
        }

        return gCerts;
    }

    /**
     * Checks if gift certificate name dto requests is null or contains null value
     *
     * @param gcReqs gift certificate name dto requests
     * @return {@code true} if list not null and dto requests not null and doesn't contains null values
     */
    public static boolean checkGiftCertificates(List<GiftCertificateNameDtoRequest> gcReqs) {
        return gcReqs != null && !gcReqs.isEmpty() && !gcReqs.contains(null);
    }

    /**
     * Converts gift certificate name dto request to gift certificate
     *
     * @param gcReq  gift certificate name dto request
     * @return {@code GiftCertificate} converted gift certificate
     */
    private static GiftCertificate convertToGiftCertificate(GiftCertificateNameDtoRequest gcReq) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName(gcReq.getName());
        return giftCertificate;
    }

    /**
     * Adjusts create date if null
     *
     * @param create requested create date in string format
     * @return {@code String} if check passed returns input datetime, otherwise will return current datetime
     */
    private static String adjustCreateIfNull(String create) {
        return checkCreate(create) ? create : IsoDateFormatter.dateToString(LocalDateTime.now());
    }

    /**
     * Adjusts update date if null
     *
     * @param update requested update date in string format
     * @return {@code String} if check passed returns input datetime, otherwise will return current datetime
     */
    private static String adjustUpdateIfNull(String update) {
        return checkUpdate(update) ? update : IsoDateFormatter.dateToString(LocalDateTime.now());
    }

    /**
     * Checks gift certificate's name if null, empty or blank and it's length
     *
     * @param name name to check
     * @return {@code true} if name not null, empty, blank and has correct length
     */
    private static boolean checkName(String name) {
        return !StringUtils.isBlank(name) && name.length() <= 255;
    }

    /**
     * Checks gift certificate's description if null, empty or blank and it's length
     *
     * @param description description to check
     * @return {@code true} if description not null, empty, blank and has correct length
     */
    private static boolean checkDescription(String description) {
        return !StringUtils.isBlank(description) && description.length() <= 255;
    }

    /**
     * Checks gift certificate's price if null or less than zero
     *
     * @param price price to check
     * @return {@code true} if price not null and greater than zero
     */
    private static boolean checkPrice(BigDecimal price) {
        return price != null && price.doubleValue() > 0;
    }

    /**
     * Checks gift certificate's duration if null or less than zero
     *
     * @param duration duration to check
     * @return {@code true} if duration not null and greater than zero
     */
    private static boolean checkDuration(Integer duration) {
        return duration != null && duration > 0;
    }

    /**
     * Checks gift certificate's create date if null, empty or blank and is ISO8601 format
     *
     * @param create create date to check
     * @return {@code true} if create not null, empty, blank and is ISO8601 format
     */
    private static boolean checkCreate(String create) {
        return !StringUtils.isBlank(create) && IsoDateFormatter.checkIfIsoDate(create);
    }

    /**
     * Checks gift certificate's update date if null, empty or blank and is ISO8601 format
     *
     * @param update update date to check
     * @return {@code true} if update not null, empty, blank and is ISO8601 format
     */
    private static boolean checkUpdate(String update) {
        return !StringUtils.isBlank(update) && IsoDateFormatter.checkIfIsoDate(update);
    }

    /**
     * Checks gift certificate's dto request tags are null
     *
     * @param dto requested gift certificate dto request
     * @return {@code true} if update not null, empty, blank and is ISO8601 format
     */
    private static boolean checkTagsNull(GiftCertificateDtoRequest dto) {
        return dto.getTags() != null;
    }

    /**
     * Checks gift certificate's name dto request name if null, empty or blank
     *
     * @param gcReq requested gift certificate name dto request
     */
    private static void checkName(GiftCertificateNameDtoRequest gcReq) {
        if (StringUtils.isBlank(gcReq.getName())) {
            throw new InputException("Gift Certificate fields was corrupted or wrong format used");
        }
    }
}