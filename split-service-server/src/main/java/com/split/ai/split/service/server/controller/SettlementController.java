package com.split.ai.split.service.server.controller;

import com.split.ai.split.service.core.service.SettlementService;
import com.split.ai.split.service.model.request.SettlementRequest;
import com.split.ai.split.service.model.response.SettlementResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling settlements.
 */
@RestController
@RequestMapping("/v1/settlements")
@RequiredArgsConstructor
@Slf4j
public class SettlementController {

    private final SettlementService settlementService;

    @PostMapping
    public ResponseEntity<Void> settleUp(@Valid @RequestBody SettlementRequest request) {
        log.info("[SettlementController : settleUp] : {}", request);
        settlementService.settleUp(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{settlementId}")
    public ResponseEntity<SettlementResponse> getSettlement(@PathVariable @NotBlank String settlementId) {
        log.info("[SettlementController : getSettlement] : {}", settlementId);
        return ResponseEntity.ok(settlementService.getSettlement(settlementId));
    }
}
