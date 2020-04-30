CREATE TABLE users (
                       id BIGINT NOT NULL PRIMARY KEY,
                       username VARCHAR(255),
                       password VARCHAR(255),
                       email VARCHAR(255),
                       role_id VARCHAR(255)
);

CREATE TABLE transactions (
                              id BIGINT NOT NULL PRIMARY KEY,
                              accountNumber VARCHAR(255),
                              date DATE,
                              beneficiaryOrPayerAccount  VARCHAR(255),
                              beneficiaryOrPayerName VARCHAR(255),
                              details VARCHAR(255),
                              amount NUMERIC(19,2),
                              currency VARCHAR(3),
                              debitOrCredit VARCHAR(10),
                              incomeStatementType VARCHAR(255),
                              user_id BIGINT
);

CREATE TABLE rules (
                       id BIGINT NOT NULL PRIMARY KEY,
                       name VARCHAR(255),
                       dateMade DATE,
                       transactionBeneficiaryOrPayerAccount  VARCHAR(255),
                       transactionBeneficiaryOrPayerName VARCHAR(255),
                       transactionDetails VARCHAR(255),
                       incomeStatementType VARCHAR(255),
                       isAddedByUser boolean,
                       user_id BIGINT
);

CREATE TABLE roles (
                       id BIGINT NOT NULL PRIMARY KEY,
                       eRole varchar(255)
);

create table reportrows(
                           id BIGINT NOT NULL PRIMARY KEY,
                           incomeStatementType VARCHAR(255),
                           sumOfTransactions NUMERIC(19,2),
                           report_id BIGINT,
                           transactions_id BIGINT
);

create table reports(
                        id BIGINT NOT NULL PRIMARY KEY,
                        name VARCHAR(255),
                        dateMade DATE,
                        startDate DATE,
                        endDate DATE,
                        report_id BIGINT,
                        user_id BIGINT

);

create table files(
                      id BIGINT NOT NULL PRIMARY KEY,
                      name VARCHAR(255),
                      uploadDate DATE,
                      user_id BIGINT
);
