CREATE TABLE sec_user (
  userId            BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name				VARCHAR(75),
  lastName			VARCHAR(75),
  email             VARCHAR(75) NOT NULL UNIQUE,
  encryptedPassword VARCHAR(128) NOT NULL,
  enabled           BIT NOT NULL 
);

CREATE TABLE user_schedule (
  id            	BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name				VARCHAR(75),
  lastName			VARCHAR(75),
  email             VARCHAR(75),
  prefDay1			VARCHAR(75),
  prefTime1			VARCHAR(75),
  prefDay2			VARCHAR(75),
  prefTime2			VARCHAR(75)
);

CREATE TABLE sec_role(
  roleId   BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  roleName VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE user_role
(
  id     BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  userId BIGINT NOT NULL,
  roleId BIGINT NOT NULL
);

ALTER TABLE user_schedule
  ADD CONSTRAINT user_schedule_fk FOREIGN KEY (name, lastName, email)
  REFERENCES sec_user (name, lastName, email);

ALTER TABLE user_role
  ADD CONSTRAINT user_role_uk UNIQUE (userId, roleId);

ALTER TABLE user_role
  ADD CONSTRAINT user_role_fk1 FOREIGN KEY (userId)
  REFERENCES sec_user (userId);
 
ALTER TABLE user_role
  ADD CONSTRAINT user_role_fk2 FOREIGN KEY (roleId)
  REFERENCES sec_role (roleId);
