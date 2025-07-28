package com.split.ai.split.service.server.controller;

import com.split.ai.split.service.core.service.IExpenseService;
import com.split.ai.split.service.model.request.expense.CreateExpenseRequest;
import com.split.ai.split.service.model.request.expense.DeleteExpenseRequest;
import com.split.ai.split.service.model.request.expense.UpdateExpenseRequest;
import com.split.ai.split.service.model.response.expense.ExpenseHistoryResponse;
import com.split.ai.split.service.model.response.expense.ExpenseResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for expense related operations.
 */
@RestController
@RequestMapping("/v1/expenses")
@RequiredArgsConstructor
@Slf4j
public class ExpenseController {

    private final IExpenseService expenseService;

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
