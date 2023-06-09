CREATE TABLE ROLE(
     ID_ROLE BIGINT AUTO_INCREMENT PRIMARY KEY,
     ROLE_NAME VARCHAR(50),
     DESCRIPTION VARCHAR(100),
     CREATED TIMESTAMP NOT NULL,
     CREATED_BY BINARY(32) NOT NULL,
     MODIFIED TIMESTAMP,
     MODIFIED_BY BINARY(32)
);

CREATE TABLE "USER" (
      ID_USER BINARY(32) PRIMARY KEY,
      NAME VARCHAR(100),
      EMAIL VARCHAR(50),
      PASSWORD VARCHAR(100),
      TOKEN VARCHAR(255),
      IS_ACTIVE BOOLEAN DEFAULT FALSE,
      LAST_LOGIN TIMESTAMP,
      CREATED TIMESTAMP NOT NULL,
      CREATED_BY BINARY(32) NOT NULL,
      MODIFIED TIMESTAMP,
      MODIFIED_BY BINARY(32)
);

CREATE TABLE USER_ROLE(
      ID_ROLE_USER BIGINT AUTO_INCREMENT PRIMARY KEY,
      ID_ROLE BIGINT NOT NULL,
      ID_USER BINARY(32) NOT NULL,
      CONSTRAINT FK_ID_ROL_ROL_USER FOREIGN KEY(ID_ROLE) REFERENCES ROLE(ID_ROLE),
      CONSTRAINT FK_ID_USER_ROL_USER FOREIGN KEY(ID_USER) REFERENCES "USER"(ID_USER)
);

CREATE TABLE PHONE(
      ID_PHONE BIGINT AUTO_INCREMENT PRIMARY KEY,
      ID_USER BINARY(32) NOT NULL,
      PHONE_NUMBER VARCHAR(20),
      CITY_CODE VARCHAR(5),
      COUNTRY_CODE VARCHAR(5),
      CREATED TIMESTAMP NOT NULL,
      CREATED_BY BINARY(32) NOT NULL,
      MODIFIED TIMESTAMP,
      MODIFIED_BY BINARY(32),
      CONSTRAINT FK_ID_USER_PHONE FOREIGN KEY(ID_USER) REFERENCES "USER"(ID_USER)
);

CREATE VIEW VW_USER AS
(
SELECT U.NAME,
       U.EMAIL,
       R.DESCRIPTION AS ROL,
       T.PHONE_NUMBER
FROM "USER" U
         INNER JOIN USER_ROLE RU
                    ON RU.ID_USER = U.ID_USER
         INNER JOIN ROLE R
                    ON R.ID_ROLE = RU.ID_ROLE
         LEFT JOIN PHONE T
                   ON T.ID_USER = U.ID_USER
    );