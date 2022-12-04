package com.thesis.automatic_plant_shop.service;

import com.thesis.automatic_plant_shop.dao.StatementDAO;
import com.thesis.automatic_plant_shop.model.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class StatementService {

    private final StatementDAO statementDAO;

    @Autowired
    public StatementService(@Qualifier("postgres_statement") StatementDAO statementDAO) {
        this.statementDAO = statementDAO;
    }

    public Statement addStatement(Statement statement) {
        return statementDAO.save(new Statement(statement.getPlant().getId()));
    }

    ///////////////////////////////////////////////////////////////////////

    public Iterable<Statement> getAllStatement() {
        return statementDAO.findAll();
    }
    public Optional<Statement> getStatementById(UUID statement_id) {
        return statementDAO.findById(statement_id);
    }

    ///////////////////////////////////////////////////////////////////////

    public void deleteAllStatement() {
        statementDAO.deleteAll();
    }
    public void deleteStatementById(UUID statement_id) {
        statementDAO.deleteById(statement_id);
    }
}
