package com.itsm.audit.repository;

import com.itsm.audit.domain.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    
    Page<AuditLog> findByTenantIdOrderByCreatedAtDesc(String tenantId, Pageable pageable);
    
    Page<AuditLog> findByTenantIdAndUserIdOrderByCreatedAtDesc(String tenantId, String userId, Pageable pageable);
    
    Page<AuditLog> findByTenantIdAndResourceTypeAndResourceIdOrderByCreatedAtDesc(String tenantId, String resourceType, String resourceId, Pageable pageable);
    
    Page<AuditLog> findByTenantIdAndCreatedAtBetweenOrderByCreatedAtDesc(String tenantId, LocalDateTime start, LocalDateTime end, Pageable pageable);
}
