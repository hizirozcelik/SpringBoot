CREATE TABLE location (
  locId         BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  locName		VARCHAR(75),
  locAddress	VARCHAR(255),
  locPhone		VARCHAR(25),
  locEmail		VARCHAR(75)
);

CREATE TABLE sec_user (
  userId            BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name				VARCHAR(75),
  lastName			VARCHAR(75),
  address			VARCHAR(255),
  phone				VARCHAR(10),
  email             VARCHAR(75) NOT NULL UNIQUE,
  encryptedPassword VARCHAR(128) NOT NULL,
  enabled           BIT NOT NULL
);

CREATE TABLE coach (

	coachId 	BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,	
	coachName	VARCHAR(75),
	coachLevel	BIGINT,
	coachRating BIGINT,
	aboutMe		VARCHAR(255)
);

CREATE TABLE fitnessClass (

	classId 	BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,	
	className	VARCHAR(75),
	classPrice	DOUBLE
);

CREATE TABLE generalCert (
	
	certId		BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	certName	VARCHAR(75)
);

CREATE TABLE certCoach (
	
	certId		BIGINT NOT NULL,
	coachId		BIGINT NOT NULL
);

ALTER TABLE certCoach
  ADD CONSTRAINT certCoach_FK1 FOREIGN KEY (certId)
  REFERENCES generalCert (certId);

ALTER TABLE certCoach
  ADD CONSTRAINT certCoach_FK2 FOREIGN KEY (coachId)
  REFERENCES coach (coachId);

CREATE TABLE userLocation (
	
	userLocId	BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	locId		BIGINT NOT NULL,
	userId		BIGINT NOT NULL,
	isHomeLoc	BIT,
	dayOfVisit	DATE
);

ALTER TABLE userLocation
  ADD CONSTRAINT userLocation_FK1 FOREIGN KEY (locId)
  REFERENCES location (locId);

ALTER TABLE userLocation
  ADD CONSTRAINT userLocation_FK2 FOREIGN KEY (userId)
  REFERENCES sec_user (userId);  

CREATE TABLE amenity (
	amenityId	BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	amenityName	VARCHAR(75),
	locId		BIGINT
);

ALTER TABLE amenity
  ADD CONSTRAINT amenity_FK FOREIGN KEY (locId)
  REFERENCES location (locId);
  
CREATE TABLE room (
	roomId		BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	roomName	VARCHAR(75),
	locId		BIGINT
);

ALTER TABLE room
  ADD CONSTRAINT room_FK FOREIGN KEY (locId)
  REFERENCES location (locId);

CREATE TABLE classCoach (
    classId  BIGINT NOT NULL,
    coachId  BIGINT NOT NULL
);

ALTER TABLE classCoach
  ADD CONSTRAINT classCoachFK1   FOREIGN KEY (classId)
    REFERENCES fitnessClass(classId);

ALTER TABLE classCoach
  ADD CONSTRAINT classCoachFK2    FOREIGN KEY (coachId)
    REFERENCES coach(coachId);

CREATE TABLE schedule (
    scheduleId      BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    scheduleDate    DATE,
    scheduleTime    TIME,
    roomId          BIGINT,
    classId         BIGINT NOT NULL,
    locId           BIGINT NOT NULL
);
    
ALTER TABLE schedule
  ADD CONSTRAINT scheduleFK1  FOREIGN KEY (roomId)
    REFERENCES room(RoomId);
    
ALTER TABLE schedule
  ADD CONSTRAINT scheduleFK2  FOREIGN KEY (classId)
    REFERENCES fitnessClass(classId);
    
ALTER TABLE schedule
  ADD CONSTRAINT ScheduleFK3  FOREIGN KEY (LocId)
    REFERENCES location(LocId);

CREATE TABLE reservation (
    ClassId     BIGINT NOT NULL,
    userId	    BIGINT NOT NULL
);

ALTER TABLE reservation
  ADD CONSTRAINT reservationFK1  FOREIGN KEY (classId)
    REFERENCES fitnessClass(classId);
    
ALTER TABLE reservation
  ADD CONSTRAINT reservationFK2  FOREIGN KEY (userId)
    REFERENCES sec_user(userId);

CREATE TABLE workFrom (
    coachId     BIGINT NOT NULL,
    locId       BIGINT NOT NULL
);

ALTER TABLE workFrom
  ADD CONSTRAINT workFromFK1  FOREIGN KEY (coachId)
    REFERENCES coach(coachId);
    
ALTER TABLE workFrom
  ADD CONSTRAINT workFromFK2  FOREIGN KEY (locId)
    REFERENCES location(locId);

CREATE TABLE review (
    ReviewId    BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    CoachId     BIGINT NOT NULL,
    userId    BIGINT NOT NULL,
    ReviewDate  DATE,
    ComScore    CHAR(1),
    EnthScore   CHAR(1),
    PunctScore  CHAR(1),
    ReviewText  VARCHAR(500)
);

ALTER TABLE review
  ADD CONSTRAINT reviewFK1  FOREIGN KEY (coachId)
    REFERENCES coach(coachId);
ALTER TABLE review
  ADD CONSTRAINT reviewFK2  FOREIGN KEY (userId)
    REFERENCES sec_user(userId);

CREATE TABLE Reference (
    CoachId     BIGINT NOT NULL,
    userId    BIGINT NOT NULL
);
ALTER TABLE Reference
  ADD CONSTRAINT ReferenceFK1  FOREIGN KEY (coachId)
    REFERENCES coach(coachId);
ALTER TABLE review
  ADD CONSTRAINT ReferenceFK2  FOREIGN KEY (userId)
    REFERENCES sec_user(userId);

CREATE TABLE ClientCoach (
    coachId     BIGINT NOT NULL,
    userId    	BIGINT NOT NULL,
    myCoach     BIT
);

ALTER TABLE ClientCoach
  ADD CONSTRAINT ClientCoachFK1  FOREIGN KEY (coachId)
    REFERENCES coach(coachId);
ALTER TABLE ClientCoach
  ADD CONSTRAINT ClientCoachFK2  FOREIGN KEY (userId)
    REFERENCES sec_user(userId);


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


ALTER TABLE user_role
  ADD CONSTRAINT user_role_uk UNIQUE (userId, roleId);

ALTER TABLE user_role
  ADD CONSTRAINT user_role_fk1 FOREIGN KEY (userId)
  REFERENCES sec_user (userId);
 
ALTER TABLE user_role
  ADD CONSTRAINT user_role_fk2 FOREIGN KEY (roleId)
  REFERENCES sec_role (roleId);
