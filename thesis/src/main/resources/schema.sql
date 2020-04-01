/*DROP SCHEMA public CASCADE;

CREATE SEQUENCE transaction_sequence AS INTEGER START WITH 1;

CREATE TABLE transactions (
  id BIGINT NOT NULL PRIMARY KEY,
  accountNumber VARCHAR(255),
  date DATE,
  beneficiaryOrPayerAccount  VARCHAR(255),
  beneficiaryOrPayerName VARCHAR(255),
  details VARCHAR(255),
  amount NUMERIC(19,2),
  currency VARCHAR(3),
  debitOrCredit VARCHAR(1),
  incomeStatementType VARCHAR(255),
   report_row_id BIGINT
);*/

/*create table files(
                       id BIGINT NOT NULL PRIMARY KEY,
                       name VARCHAR(255),
                       uploadDate DATE
);*/
/*create table reports(
                       id BIGINT NOT NULL PRIMARY KEY,
                       name VARCHAR(255),
                       dateMade DATE
);*/

/*create table reportRows(
                        id BIGINT NOT NULL PRIMARY KEY,
                        sumOfTransactions NUMERIC(19,2),
                        incomeStatementType VARCHAR(255),
                        report_id VARCHAR(255)
);*/

CREATE TABLE rules (
                              id BIGINT NOT NULL PRIMARY KEY,
                              name VARCHAR(255),
                              dateMade DATE,
                              transactionBeneficiaryOrPayerAccount  VARCHAR(255),
                              transactionBeneficiaryOrPayerName VARCHAR(255),
                              transactionDetails VARCHAR(255),
                              incomeStatementType VARCHAR(255),
                              isAddedByUser boolean
);
