package com.epam.esm.dao.impl;

import com.epam.esm.dao.interfaces.entity.TagDao;
import com.epam.esm.db.connector.DataSourceConnector;
import com.epam.esm.entity.impl.GiftCertificate;
import com.epam.esm.entity.impl.Tag;
import com.epam.esm.utils.queries.TagQueries;
import com.epam.esm.utils.rsets.GiftCertificateConverter;
import com.epam.esm.utils.rsets.TagConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class TagDaoImpl implements TagDao {

    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger("error");

    private final DataSourceConnector connector;

    @Autowired
    public TagDaoImpl(DataSourceConnector connector) {
        this.connector = connector;
    }

    @Override
    public Long create(Tag tag) {
        Long key = -1L;

        try (PreparedStatement ps = connector.getConnection().prepareStatement(TagQueries.CREATE,
                                                                               Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, tag.getId());
            ps.setString(2, tag.getName());
            ps.executeUpdate();
            key = generateKey(ps);
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Tag] Creation failed.");
        }

        return key;
    }

    @Override
    public Tag findById(Long id) {
        Tag tag = new Tag();

        try (PreparedStatement ps = connector.getConnection().prepareStatement(TagQueries.EXIST)){
            ps.setLong(1, id);
            tag = TagConverter.convertSingle(ps.executeQuery());
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Tag] Find by id operation failed for id " + id);
        }

        return tag;
    }

    @Override
    public Boolean delete(Long id) {
        try (PreparedStatement ps = connector.getConnection().prepareStatement(TagQueries.DELETE)){
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Tag] Delete operation failed for Tag with id " + id);
            return false;
        }
        return true;
    }

    @Override
    public Set<Tag> findAll() {
        Set<Tag> tags = new LinkedHashSet<>();

        try (PreparedStatement ps = connector.getConnection().prepareStatement(TagQueries.FIND_ALL)) {
            tags = TagConverter.convertMultiple(ps.executeQuery());
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Tag] Find all operation failed.");
        }

        return tags;
    }

    @Override
    public Boolean existById(Long id) {
        int count;

        try (PreparedStatement ps = connector.getConnection().prepareStatement(TagQueries.EXIST)) {
            ps.setLong(1, id);
            count = getCountFromResultSet(ps.executeQuery(), "count");
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Tag] Exist operation failed for id " + id);
            return false;
        }

        return count == 1;
    }

    @Override
    public Integer count() {
        int count = 0;

        try (PreparedStatement ps = connector.getConnection().prepareStatement(TagQueries.COUNT)) {
            count = getCountFromResultSet(ps.executeQuery(), "count");
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Tag] Count operation failed.");
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
            LOGGER_ERROR.error("[Tag] Get count from result set failed.");
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
            LOGGER_ERROR.error("[Tag] Generate key operation failed.");
        }

        return genKey;
    }

    @Override
    public Long countGiftCertificatesByTag(Long tagId) {
        long count = 0L;

        try (PreparedStatement ps = connector.getConnection()
                .prepareStatement(TagQueries.COUNT_CERTIFICATES_BY_TAG)) {
            ps.setLong(1, tagId);
            count = getCountFromResultSet(ps.executeQuery(), "count");
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Tag] Count gift certificates by tag operation failed for tag_id " + tagId);
        }

        return count;
    }

    @Override
    public Set<GiftCertificate> findGiftCertificatesByTag(Long tagId) {
        Set<GiftCertificate> gCerts = new LinkedHashSet<>();

        try (PreparedStatement ps = connector.getConnection()
                .prepareStatement(TagQueries.FIND_GIFT_CERTIFICATES_BY_TAG)) {
            ps.setLong(1, tagId);
            gCerts = GiftCertificateConverter.convertMultiple(ps.executeQuery());
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Tag] Find gift certificates by tag operation failed for tag_id " + tagId);
        }

        return gCerts;
    }

    @Override
    public Boolean addGiftCertificate(Long tagId, Long giftCertificateId) {
        try (PreparedStatement ps = connector.getConnection().prepareStatement(TagQueries.ADD_GIFT_CERTIFICATE)) {
            ps.setLong(1, giftCertificateId);
            ps.setLong(2, tagId);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Tag] Add gift certificate operation failed for tag_id " + tagId +
                               " and gift_certificate_id " + giftCertificateId);
            return false;
        }
        return true;
    }

    @Override
    public Boolean deleteGiftCertificate(Long tagId, Long giftCertificateId) {
        try (PreparedStatement ps = connector.getConnection().prepareStatement(TagQueries.REMOVE_GIFT_CERTIFICATE)) {
            ps.setLong(1, giftCertificateId);
            ps.setLong(2, tagId);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Tag] Remove gift certificate operation failed for tag_id " + tagId +
                               " and gift_certificate_id " + giftCertificateId);
            return false;
        }
        return true;
    }

    @Override
    public Set<Tag> findAllCertificated() {
        Set<Tag> tags = new LinkedHashSet<>();

        try (PreparedStatement ps = connector.getConnection().prepareStatement(TagQueries.FIND_ALL_CERTIFICATED)) {
            tags = TagConverter.convertMultiple(ps.executeQuery());
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Tag] Find all certificated tags operation failed.");
        }

        return tags;
    }
}