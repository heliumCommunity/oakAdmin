package com.helium.oakcollectionsadmin.controller;

import com.helium.oakcollectionsadmin.serviceImpls.UserInfoAuditService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/oakcollectionsadmin")
@RequiredArgsConstructor
public class OakAdminController {

    private static final Logger log = LoggerFactory.getLogger(OakAdminController.class);
    private final UserInfoAuditService auditService;

    @GetMapping("/get-user-history")
    public List<Map<String, Object>> getOakCollectionsAdminHistory(@RequestHeader Long userId) {
        log.info("get-user-history has been called::::::");
        return auditService.getAuditHistory(userId);
    }
}
