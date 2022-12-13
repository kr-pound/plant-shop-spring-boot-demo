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

    public Statement addStatement(UUID plant_id) {
        return statementDAO.save(new Statement(plant_id));
    }
    public Statement addStatement(Statement statement) {
        return statementDAO.save(new Statement(statement.getStatement_id(), statement.getDate(), statement.getTime(),
                                statement.getStatus(), statement.getPlant().getId()));
    }

    ///////////////////////////////////////////////////////////////////////

    public Iterable<Statement> getAllStatement() {
        return statementDAO.findAll();
    }
    public Optional<Statement> getStatementById(UUID statement_id) {
        return statementDAO.findById(statement_id);
    }

    ///////////////////////////////////////////////////////////////////////

    public int updateStatus(UUID statement_id, String status) {

        // Get Statement if Present
        Statement statement;
        Optional<Statement> statementOptional = getStatementById(statement_id);
        if (statementOptional.isPresent()) {
            statement = statementOptional.get();
        } else {
            return -1;
        }

        // Set Status
        statement.setStatus(status);

        // Update
        deleteStatementById(statement_id);
        // Success
        if (addStatement(statement) != null)
            return 1;
        // Failed
        else
            return -1;
    }

    ///////////////////////////////////////////////////////////////////////

    public void deleteAllStatement() {
        statementDAO.deleteAll();
    }
    public void deleteStatementById(UUID statement_id) {
        statementDAO.deleteById(statement_id);
    }
}
