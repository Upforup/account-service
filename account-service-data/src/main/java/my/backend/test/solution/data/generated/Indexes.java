/*
 * This file is generated by jOOQ.
 */
package my.backend.test.solution.data.generated;


import javax.annotation.Generated;

import my.backend.test.solution.data.generated.tables.AccountMovementTable;
import my.backend.test.solution.data.generated.tables.AccountTable;
import my.backend.test.solution.data.generated.tables.OperationTable;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>PUBLIC</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index IDX_ACC_NUMBER = Indexes0.IDX_ACC_NUMBER;
    public static final Index PRIMARY_KEY_E = Indexes0.PRIMARY_KEY_E;
    public static final Index FK_MV_ACC_INDEX_C = Indexes0.FK_MV_ACC_INDEX_C;
    public static final Index FK_MV_OP_INDEX_C = Indexes0.FK_MV_OP_INDEX_C;
    public static final Index IDX_MV_ACC = Indexes0.IDX_MV_ACC;
    public static final Index IDX_MV_OP = Indexes0.IDX_MV_OP;
    public static final Index FK_SRC_ACC_ID_INDEX_9 = Indexes0.FK_SRC_ACC_ID_INDEX_9;
    public static final Index FK_TGT_ACC_ID_INDEX_9 = Indexes0.FK_TGT_ACC_ID_INDEX_9;
    public static final Index IDX_OP_SRC_ACC = Indexes0.IDX_OP_SRC_ACC;
    public static final Index PRIMARY_KEY_9 = Indexes0.PRIMARY_KEY_9;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index IDX_ACC_NUMBER = Internal.createIndex("IDX_ACC_NUMBER", AccountTable.ACCOUNT, new OrderField[] { AccountTable.ACCOUNT.ACCOUNT_NUMBER }, true);
        public static Index PRIMARY_KEY_E = Internal.createIndex("PRIMARY_KEY_E", AccountTable.ACCOUNT, new OrderField[] { AccountTable.ACCOUNT.ID }, true);
        public static Index FK_MV_ACC_INDEX_C = Internal.createIndex("FK_MV_ACC_INDEX_C", AccountMovementTable.ACCOUNT_MOVEMENT, new OrderField[] { AccountMovementTable.ACCOUNT_MOVEMENT.ACCOUNT_ID }, false);
        public static Index FK_MV_OP_INDEX_C = Internal.createIndex("FK_MV_OP_INDEX_C", AccountMovementTable.ACCOUNT_MOVEMENT, new OrderField[] { AccountMovementTable.ACCOUNT_MOVEMENT.OPERATION_ID }, false);
        public static Index IDX_MV_ACC = Internal.createIndex("IDX_MV_ACC", AccountMovementTable.ACCOUNT_MOVEMENT, new OrderField[] { AccountMovementTable.ACCOUNT_MOVEMENT.ACCOUNT_ID, AccountMovementTable.ACCOUNT_MOVEMENT.DIRECTION }, false);
        public static Index IDX_MV_OP = Internal.createIndex("IDX_MV_OP", AccountMovementTable.ACCOUNT_MOVEMENT, new OrderField[] { AccountMovementTable.ACCOUNT_MOVEMENT.OPERATION_ID }, false);
        public static Index FK_SRC_ACC_ID_INDEX_9 = Internal.createIndex("FK_SRC_ACC_ID_INDEX_9", OperationTable.OPERATION, new OrderField[] { OperationTable.OPERATION.SRC_ACCOUNT_ID }, false);
        public static Index FK_TGT_ACC_ID_INDEX_9 = Internal.createIndex("FK_TGT_ACC_ID_INDEX_9", OperationTable.OPERATION, new OrderField[] { OperationTable.OPERATION.TGT_ACCOUNT_ID }, false);
        public static Index IDX_OP_SRC_ACC = Internal.createIndex("IDX_OP_SRC_ACC", OperationTable.OPERATION, new OrderField[] { OperationTable.OPERATION.SRC_ACCOUNT_ID, OperationTable.OPERATION.CREATED }, false);
        public static Index PRIMARY_KEY_9 = Internal.createIndex("PRIMARY_KEY_9", OperationTable.OPERATION, new OrderField[] { OperationTable.OPERATION.ID }, true);
    }
}
