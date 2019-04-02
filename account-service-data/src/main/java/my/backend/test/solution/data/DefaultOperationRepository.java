package my.backend.test.solution.data;

import my.backend.test.solution.data.generated.tables.records.OperationRecord;
import my.backend.test.solution.domain.Operation;
import org.jooq.Batch;
import org.jooq.BatchBindStep;
import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.jooq.impl.DSL;

import java.sql.Timestamp;

import static my.backend.test.solution.data.generated.Sequences.OPERATION_SEQ;
import static my.backend.test.solution.data.generated.Tables.*;

public class DefaultOperationRepository implements OperationRepository {

    private final DSLContext dsl;

    public DefaultOperationRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public Long saveNewOperation(Operation operation) {
        Long operationId = dsl.nextval(OPERATION_SEQ);

        InsertSetMoreStep<OperationRecord> saveOperation = dsl.insertInto(OPERATION)
                .set(OPERATION.ID, operationId)
                .set(OPERATION.AMOUNT, operation.getAmount())
                .set(OPERATION.CREATED, Timestamp.valueOf(operation.getOperationDate()))
                .set(OPERATION.SRC_ACCOUNT_ID, operation.getSource().getId())
                .set(OPERATION.TGT_ACCOUNT_ID, operation.getTarget().getId());

        BatchBindStep saveMovements = dsl.batch(dsl.insertInto(ACCOUNT_MOVEMENT,
                ACCOUNT_MOVEMENT.ACCOUNT_ID,
                ACCOUNT_MOVEMENT.OPERATION_ID,
                ACCOUNT_MOVEMENT.DIRECTION,
                ACCOUNT_MOVEMENT.AMOUNT,
                ACCOUNT_MOVEMENT.CREATED)
                .values((Long) null, null, null, null, null));

        operation.getMovements().forEach(mv -> saveMovements.bind(
                mv.getTarget().getId(),
                operationId,
                mv.getDirection().getValue(),
                mv.getAmount(),
                Timestamp.valueOf(mv.getCreatedOn())
        ));

        Batch updateContracts = dsl.batch(dsl.update(ACCOUNT)
                        .set(ACCOUNT.ACCOUNT_BALANCE, operation.getSource().getCurrentBalance())
                        .where(ACCOUNT.ID.eq(operation.getSource().getId())),
                dsl.update(ACCOUNT)
                        .set(ACCOUNT.ACCOUNT_BALANCE, operation.getTarget().getCurrentBalance())
                        .where(ACCOUNT.ID.eq(operation.getTarget().getId())));


        dsl.transaction(() -> {
            saveOperation.execute();
            saveMovements.execute();
            updateContracts.execute();

        });

        return operationId;
    }


}
