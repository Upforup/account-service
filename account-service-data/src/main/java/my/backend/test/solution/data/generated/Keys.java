/*
 * This file is generated by jOOQ.
 */
package my.backend.test.solution.data.generated;


import javax.annotation.Generated;

import my.backend.test.solution.data.generated.tables.AccountMovementTable;
import my.backend.test.solution.data.generated.tables.AccountTable;
import my.backend.test.solution.data.generated.tables.OperationTable;
import my.backend.test.solution.data.generated.tables.records.AccountMovementRecord;
import my.backend.test.solution.data.generated.tables.records.AccountRecord;
import my.backend.test.solution.data.generated.tables.records.OperationRecord;

import org.jooq.ForeignKey;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>PUBLIC</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AccountRecord> PK_ACCOUNT = UniqueKeys0.PK_ACCOUNT;
    public static final UniqueKey<OperationRecord> PK_OPERATION = UniqueKeys0.PK_OPERATION;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<AccountMovementRecord, AccountRecord> FK_MV_ACC = ForeignKeys0.FK_MV_ACC;
    public static final ForeignKey<AccountMovementRecord, OperationRecord> FK_MV_OP = ForeignKeys0.FK_MV_OP;
    public static final ForeignKey<OperationRecord, AccountRecord> FK_SRC_ACC_ID = ForeignKeys0.FK_SRC_ACC_ID;
    public static final ForeignKey<OperationRecord, AccountRecord> FK_TGT_ACC_ID = ForeignKeys0.FK_TGT_ACC_ID;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class UniqueKeys0 {
        public static final UniqueKey<AccountRecord> PK_ACCOUNT = Internal.createUniqueKey(AccountTable.ACCOUNT, "PK_ACCOUNT", AccountTable.ACCOUNT.ID);
        public static final UniqueKey<OperationRecord> PK_OPERATION = Internal.createUniqueKey(OperationTable.OPERATION, "PK_OPERATION", OperationTable.OPERATION.ID);
    }

    private static class ForeignKeys0 {
        public static final ForeignKey<AccountMovementRecord, AccountRecord> FK_MV_ACC = Internal.createForeignKey(my.backend.test.solution.data.generated.Keys.PK_ACCOUNT, AccountMovementTable.ACCOUNT_MOVEMENT, "FK_MV_ACC", AccountMovementTable.ACCOUNT_MOVEMENT.ACCOUNT_ID);
        public static final ForeignKey<AccountMovementRecord, OperationRecord> FK_MV_OP = Internal.createForeignKey(my.backend.test.solution.data.generated.Keys.PK_OPERATION, AccountMovementTable.ACCOUNT_MOVEMENT, "FK_MV_OP", AccountMovementTable.ACCOUNT_MOVEMENT.OPERATION_ID);
        public static final ForeignKey<OperationRecord, AccountRecord> FK_SRC_ACC_ID = Internal.createForeignKey(my.backend.test.solution.data.generated.Keys.PK_ACCOUNT, OperationTable.OPERATION, "FK_SRC_ACC_ID", OperationTable.OPERATION.SRC_ACCOUNT_ID);
        public static final ForeignKey<OperationRecord, AccountRecord> FK_TGT_ACC_ID = Internal.createForeignKey(my.backend.test.solution.data.generated.Keys.PK_ACCOUNT, OperationTable.OPERATION, "FK_TGT_ACC_ID", OperationTable.OPERATION.TGT_ACCOUNT_ID);
    }
}
