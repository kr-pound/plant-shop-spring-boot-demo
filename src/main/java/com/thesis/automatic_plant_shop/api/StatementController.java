package com.thesis.automatic_plant_shop.api;

import com.thesis.automatic_plant_shop.model.Slot;
import com.thesis.automatic_plant_shop.model.Statement;
import com.thesis.automatic_plant_shop.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("api/v1/statement")
public class StatementController {

    private final StatementService statementService;

    @Autowired
    public StatementController(StatementService statementService) {
        this.statementService = statementService;
    }

    /**
     * Add a new Statement
     * @return Statement
     */
    @PostMapping
    public Statement addStatement(@Valid @NotNull @RequestBody Statement statement) {
        return statementService.addStatement(statement);
    }

    /* ================================================================= */

    @GetMapping
    public Iterable<Statement> getAllStatement() {
        return statementService.getAllStatement();
    }
    @GetMapping(path = "{statement_id}")
    public Statement getStatementById(@PathVariable("statement_id") UUID statement_id) {
        return statementService.getStatementById(statement_id).orElse(null);
    }

    /* ================================================================= */

    @DeleteMapping
    public void deleteAllStatement() {
        statementService.deleteAllStatement();
    }
    @DeleteMapping(path = "{statement_id}")
    public void deleteStatementById(@PathVariable("statement_id") UUID statement_id) {
        statementService.deleteStatementById(statement_id);
    }
}
