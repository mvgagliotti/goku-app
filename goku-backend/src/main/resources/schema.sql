CREATE TABLE GK_USER(
    ID VARCHAR(36) NOT NULL,
    USERNAME VARCHAR(255) UNIQUE NOT NULL,
    PASSWORD VARCHAR(255),
    ROLES VARCHAR(255),
    PRIMARY KEY(ID)
);