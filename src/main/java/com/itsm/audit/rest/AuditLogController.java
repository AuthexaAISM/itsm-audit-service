package com.itsm.audit.rest;

import com.itsm.audit.domain.AuditLog;
import com.itsm.audit.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/audit")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogRepository auditLogRepository;

    @GetMapping
    public ResponseEntity<Page<AuditLog>> getAuditLogs(
            @RequestHeader("X-Tenant-ID") String tenantId,
            @PageableDefault(size = 50) Pageable pageable) {
        return ResponseEntity.ok(auditLogRepository.findByTenantIdOrderByCreatedAtDesc(tenantId, pageable));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<AuditLog>> getUserAuditLogs(
            @RequestHeader("X-Tenant-ID") String tenantId,
            @PathVariable String userId,
            @PageableDefault(size = 50) Pageable pageable) {
        return ResponseEntity.ok(auditLogRepository.findByTenantIdAndUserIdOrderByCreatedAtDesc(tenantId, userId, pageable));
    }

    @GetMapping("/resource/{resourceType}/{resourceId}")
    public ResponseEntity<Page<AuditLog>> getResourceAuditLogs(
            @RequestHeader("X-Tenant-ID") String tenantId,
            @PathVariable String resourceType,
            @PathVariable String resourceId,
            @PageableDefault(size = 50) Pageable pageable) {
        return ResponseEntity.ok(auditLogRepository.findByTenantIdAndResourceTypeAndResourceIdOrderByCreatedAtDesc(
                tenantId, resourceType, resourceId, pageable));
    }

    @GetMapping("/timerange")
    public ResponseEntity<Page<AuditLog>> getLogsInTimeRange(
            @RequestHeader("X-Tenant-ID") String tenantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @PageableDefault(size = 50) Pageable pageable) {
        return ResponseEntity.ok(auditLogRepository.findByTenantIdAndCreatedAtBetweenOrderByCreatedAtDesc(
                tenantId, start, end, pageable));
    }
}
