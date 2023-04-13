package com.epam.esm.dao.impl;

import com.epam.esm.dao.interfaces.entity.GiftCertificateDao;
import com.epam.esm.db.connector.DataSourceConnector;
import com.epam.esm.entity.impl.GiftCertificate;
import com.epam.esm.entity.impl.Tag;
import com.epam.esm.utils.queries.GiftCertificateQueries;
import com.epam.esm.utils.rsets.GiftCertificateConverter;
import com.epam.esm.utils.rsets.TagConverter;
import com.epam.esm.utils.search.dao.SearchParamRequest;
import com.epam.esm.utils.search.dao.SearchParamResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger("error");

    private final DataSourceConnector connector;

    @Autowired
    public GiftCertificateDaoImpl(DataSourceConnector connector) {
        this.connector = connector;
    }

    @Override
    public Long create(GiftCertificate gCert) {
        Long key = -1L;

        try (PreparedStatement ps = connector.getConnection().prepareStatement(GiftCertificateQueries.CREATE,
                                                                               Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, gCert.getId());
            ps.setString(2,gCert.getName());
            ps.setString(3, gCert.getDescription());
            ps.setBigDecimal(4, gCert.getPrice());
            ps.setInt(5, gCert.getDuration());
            ps.setTimestamp(6, new Timestamp(gCert.getCreateDate().getTime()));
            ps.setTimestamp(7, new Timestamp(gCert.getLastUpdateDate().getTime()));
            ps.executeUpdate();
            key = generateKey(ps);
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Gift Certificate] Create operation failed.");
        }

        return key;
    }

    @Override
    public GiftCertificate findById(Long id) {
        GiftCertificate gCert = new GiftCertificate();

        try (PreparedStatement ps = connector.getConnection().prepareStatement(GiftCertificateQueries.FIND_BY_ID)) {
            ps.setLong(1, id);
            gCert = GiftCertificateConverter.convertSingle(ps.executeQuery());
        } catch (SQLException e) {
            LOGGER_ERROR.warn("[Gift Certificate] Find by id operation failed for id " + id);
        }

        return gCert;
    }

    @Override
    public Boolean update(GiftCertificate gCert) {
        try (PreparedStatement ps = connector.getConnection().prepareStatement(GiftCertificateQueries.UPDATE)) {
            ps.setString(1, gCert.getName());
            ps.setString(2, gCert.getDescription());
            ps.setBigDecimal(3, gCert.getPrice());
            ps.setInt(4, gCert.getDuration());
            ps.setTimestamp(5, new Timestamp(gCert.getLastUpdateDate().getTime()));
            ps.setLong(6, gCert.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER_ERROR.warn("[Gift Certificate] Update operation failed for Gift Certificate with id " + gCert.getId());
            return false;
        }
        return true;
    }

    @Override
    public Boolean delete(Long id) {
        try (PreparedStatement ps = connector.getConnection().prepareStatement(GiftCertificateQueries.DELETE)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Gift Certificate] Delete operation failed for Gift Certificate with id " + id);
            return false;
        }
        return true;
    }

    @Override
    public Set<GiftCertificate> findAll() {
        Set<GiftCertificate> gCerts = new LinkedHashSet<>();

        try (PreparedStatement ps = connector.getConnection().prepareStatement(GiftCertificateQueries.FIND_ALL)) {
            gCerts = GiftCertificateConverter.convertMultiple(ps.executeQuery());
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Gift Certificate] Find all operation failed.");
        }

        return gCerts;
    }

    @Override
    public Boolean existById(Long id) {
        int count;

        try (PreparedStatement ps = connector.getConnection().prepareStatement(GiftCertificateQueries.EXIST)) {
            ps.setLong(1, id);
            count = getCountFromResultSet(ps.executeQuery(), "count");
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Gift Certificate] Exist operation failed for id " + id);
            return false;
        }

        return count == 1;
    }

    @Override
    public Integer count() {
        int count = 0;

        try (PreparedStatement ps = connector.getConnection().prepareStatement(GiftCertificateQueries.COUNT)) {
            count = getCountFromResultSet(ps.executeQuery(), "count");
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Gift Certificate] Count operation failed.");
        }

        return count;
    }

    @Override
    public Integer getCountFromResultSet(ResultSet rs, String countColumn) {
        int count = 0;

        try {
            while (rs.next()) {
                count = rs.getInt(countColumn);
            }
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Gift Certificate] Get count from result set failed.");
        }

        return count;
    }

    @Override
    public Long generateKey(PreparedStatement ps) {
        long genKey = -1;

        try (ResultSet keys = ps.getGeneratedKeys()) {
            while (keys.next()) {
                genKey = keys.getLong(1);
            }
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Gift Certificate] Generate key operation failed.");
        }

        return genKey;
    }

    @Override
    public Long countTagsByGiftCertificate(Long giftCertificateId) {
        long count = 0L;

        try (PreparedStatement ps = connector.getConnection()
                                             .prepareStatement(GiftCertificateQueries.COUNT_TAGS_BY_GIFT_CERTIFICATE)) {
            ps.setLong(1, giftCertificateId);
            count = getCountFromResultSet(ps.executeQuery(), "count");
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Gift Certificate] Count tags by gift certificate operation failed for " +
                               "gift_certificate_id" + giftCertificateId);
        }

        return count;
    }

    @Override
    public Set<Tag> findTagsByGiftCertificate(Long giftCertificateId) {
        Set<Tag> tags = new LinkedHashSet<>();

        try (PreparedStatement ps = connector.getConnection()
                                             .prepareStatement(GiftCertificateQueries.FIND_TAGS_BY_GIFT_CERTIFICATE)) {
            ps.setLong(1, giftCertificateId);
            tags = TagConverter.convertMultiple(ps.executeQuery());
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Gift Certificate] Find tags by gift certificate operation failed for " +
                               "gift_certificate_id " + giftCertificateId);
        }

        return tags;
    }

    @Override
    public Boolean addTag(Long giftCertificateId, Long tagId) {
        try (PreparedStatement ps = connector.getConnection().prepareStatement(GiftCertificateQueries.ADD_TAG)) {
            ps.setLong(1, giftCertificateId);
            ps.setLong(2, tagId);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Gift Certificate] Add tag operation failed for gift_certificate_id " +
                                giftCertificateId + " and tag_id " + tagId);
            return false;
        }
        return true;
    }

    @Override
    public Boolean deleteTag(Long giftCertificateId, Long tagId) {
        try (PreparedStatement ps = connector.getConnection().prepareStatement(GiftCertificateQueries.REMOVE_TAG)) {
            ps.setLong(1, giftCertificateId);
            ps.setLong(2, tagId);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Gift Certificate] Delete tag operation failed for gift_certificate_id " +
                                giftCertificateId + " and tag_id " + tagId);
            return false;
        }
        return true;
    }

    @Override
    public Set<GiftCertificate> findAllTagged() {
        Set<GiftCertificate> gCerts = new LinkedHashSet<>();

        try (PreparedStatement ps = connector.getConnection()
                                             .prepareStatement(GiftCertificateQueries.FIND_ALL_TAGGED_CERTIFICATES)) {
            gCerts = GiftCertificateConverter.convertMultiple(ps.executeQuery());
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Gift Certificate] Find all tagged certificates operation failed.");
        }

        return gCerts;
    }

    @Override
    public SearchParamResponse<GiftCertificate> findAllParamSearch(SearchParamRequest req) {
        Set<GiftCertificate> gCerts = new LinkedHashSet<>();

        try (PreparedStatement ps = connector.getConnection()
                                          .prepareStatement(GiftCertificateQueries.findAllCertificatesByParams(req))) {
            gCerts = GiftCertificateConverter.convertMultiple(ps.executeQuery());
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Gift Certificate] Find all certificates by param search operation failed.");
        }

        SearchParamResponse<GiftCertificate> gCertsResp = new SearchParamResponse<>(req);
        gCertsResp.setItems(gCerts);
        return gCertsResp;
    }
}