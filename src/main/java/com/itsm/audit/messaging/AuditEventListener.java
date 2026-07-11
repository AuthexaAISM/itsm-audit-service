package com.itsm.audit.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsm.audit.domain.AuditLog;
import com.itsm.audit.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditEventListener {

    private final AuditLogRepository auditLogRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "audit-events", groupId = "itsm-audit-group")
    @Transactional
    public void consumeAuditEvent(String message) {
        log.debug("Received audit event: {}", message);
        try {
            AuditLog auditLog = objectMapper.readValue(message, AuditLog.class);
            auditLogRepository.save(auditLog);
            log.info("Saved audit log for tenant: {}, action: {}, resource: {}", 
                    auditLog.getTenantId(), auditLog.getAction(), auditLog.getResourceType());
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize audit event: {}", message, e);
        } catch (Exception e) {
            log.error("Error saving audit event", e);
            throw e; // Kafka will retry based on config
        }
    }
}
