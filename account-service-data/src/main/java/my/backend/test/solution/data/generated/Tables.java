/*
 * This file is generated by jOOQ.
 */
package my.backend.test.solution.data.generated;


import javax.annotation.Generated;

import my.backend.test.solution.data.generated.tables.AccountMovementTable;
import my.backend.test.solution.data.generated.tables.AccountTable;
import my.backend.test.solution.data.generated.tables.OperationTable;


/**
 * Convenience access to all tables in PUBLIC
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>PUBLIC.ACCOUNT</code>.
     */
    public static final AccountTable ACCOUNT = my.backend.test.solution.data.generated.tables.AccountTable.ACCOUNT;

    /**
     * The table <code>PUBLIC.ACCOUNT_MOVEMENT</code>.
     */
    public static final AccountMovementTable ACCOUNT_MOVEMENT = my.backend.test.solution.data.generated.tables.AccountMovementTable.ACCOUNT_MOVEMENT;

    /**
     * The table <code>PUBLIC.OPERATION</code>.
     */
    public static final OperationTable OPERATION = my.backend.test.solution.data.generated.tables.OperationTable.OPERATION;
}
