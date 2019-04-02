CREATE TABLE account (
  id              NUMBER(18)    NOT NULL,
  account_number  VARCHAR2(256) NOT NULL,
  account_balance NUMBER(20, 2) NOT NULL,
  date_open       DATE          NOT NULL,
  CONSTRAINT pk_account PRIMARY KEY (
    id
  )
);

CREATE TABLE account_movement (
  account_id   NUMBER(18)    NOT NULL,
  operation_id NUMBER(18)    NOT NULL,
  amount       NUMBER(20, 2) NOT NULL,
  direction    number(1),
  created      TIMESTAMP(2)
);

CREATE TABLE operation (
  id             NUMBER(18)    NOT NULL,
  src_account_id number(18)    NOT NULL,
  tgt_account_id number(18)    NOT NULL,
  amount         NUMBER(20, 2) NOT NULL,
  created        TIMESTAMP(2),
  CONSTRAINT pk_operation PRIMARY KEY (
    id
  )
);

ALTER TABLE account_movement
  ADD CONSTRAINT fk_mv_acc FOREIGN KEY (account_id)
REFERENCES account (id);

ALTER TABLE account_movement
  ADD CONSTRAINT fk_mv_op FOREIGN KEY (operation_id)
REFERENCES operation (id);


ALTER TABLE operation
  ADD CONSTRAINT fk_src_acc_id FOREIGN KEY (src_account_id)
REFERENCES account (id);

ALTER TABLE operation
  ADD CONSTRAINT fk_tgt_acc_id FOREIGN KEY (tgt_account_id)
REFERENCES account (id);

CREATE UNIQUE INDEX idx_acc_number
  ON account (account_number);


CREATE INDEX idx_mv_acc
  ON account_movement (account_id, direction);

CREATE INDEX idx_mv_op
  ON account_movement (operation_id);

create index idx_op_src_acc
  ON operation (src_account_id, created);


CREATE SEQUENCE account_seq
  MINVALUE 1
  MAXVALUE 999999999999999999
  INCREMENT BY 1
  START WITH 1
  CACHE 1000
  NOCYCLE;
CREATE SEQUENCE operation_seq
  MINVALUE 1
  MAXVALUE 999999999999999999
  INCREMENT BY 1
  START WITH 1
  CACHE 1000
  NOCYCLE;

