package com.split.ai.split.service.server.controller;

import com.split.ai.split.service.core.service.ExpenseService;
import com.split.ai.split.service.model.request.*;
import com.split.ai.split.service.model.response.ExpenseHistoryResponse;
import com.split.ai.split.service.model.response.ExpenseResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for expense related operations.
 */
@RestController
@RequestMapping("/v1/expenses")
@RequiredArgsConstructor
@Slf4j
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("/get/{expenseId}")
    public ResponseEntity<ExpenseResponse> getExpense(@PathVariable @NotBlank String expenseId) {
        log.info("[ExpenseController : getExpense] : {}", expenseId);
        return ResponseEntity.ok(expenseService.getExpense(expenseId));
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createExpense(@Valid @RequestBody CreateExpenseRequest request) {
        log.info("[ExpenseController : createExpense] : {}", request);
        expenseService.createExpense(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateExpense(@Valid @RequestBody UpdateExpenseRequest request) {
        log.info("[ExpenseController : updateExpense] : {}", request);
        expenseService.updateExpense(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteExpense(@Valid @RequestBody DeleteExpenseRequest request) {
        log.info("[ExpenseController : deleteExpense] : {}", request);
        expenseService.deleteExpense(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{expenseId}/history")
    public ResponseEntity<ExpenseHistoryResponse> getExpenseHistory(@PathVariable @NotBlank String expenseId) {
        log.info("[ExpenseController : getExpenseHistory] : {}", expenseId);
        return ResponseEntity.ok(expenseService.getHistory(expenseId));
    }
}
