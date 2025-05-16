package com.helium.oakcollectionsadmin.serviceImpls;

import com.helium.oakcollectionsadmin.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserInfoAuditService {
    @PersistenceContext
    private EntityManager entityManager;

    public Object getAuditHistory(String userId) {
        log.info("getAuditHistory has been called");
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        try {

            log.info("Fetching audit history for User ID: {}", userId);

            AuditReader reader = AuditReaderFactory.get(entityManager);

            List<Number> revisions = reader.getRevisions(UserInfo.class, userId);
            log.info("Found {} revisions for User Id: {}", revisions.size(), userId);
            if (revisions.isEmpty()) {
                log.info("No revisions found for User Id: {}", userId);
                return "No Revisions Found";

            }
            List<Map<String, Object>> history = new ArrayList<>();

            for (Number rev : revisions) {
                log.debug("Fetching data for changes made: {}", rev);
                UserInfo userHistoryTracker = reader.find(UserInfo.class, userId, rev);
                Date revisionDate = reader.getRevisionDate(rev);
                log.debug("Revision {} at {}: {}", rev, revisionDate, userHistoryTracker);

                Map<String, Object> record = new HashMap<>();
                record.put("revision", rev);
                record.put("revisionDate", revisionDate);
                record.put("adminData", userHistoryTracker);

                history.add(record);
            }
            log.info("Returning history: {}", history);
            log.info("Finished Fetching audit history for User Id: {}", userId);

            return history;
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        }


}
