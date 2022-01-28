INSERT INTO sec_user (name, lastName, email, encryptedPassword, enabled)
VALUES ('Hizir', 'Ozcelik', 'ozcelikh@sheridancollege.ca', '$2a$10$1ltibqiyyBJMJQ4hqM7f0OusP6np/IHshkYc4TjedwHnwwNChQZCy', 1);
INSERT INTO sec_user (name, lastName, email, encryptedPassword, enabled)
VALUES ('Alex', 'Oz', 'alex@fitness.ca', '$2a$10$mOAnKoH8YowDzdP2MqXF0ewjkAgXOYjTkunyDUXzJQ8q4s/aEYLvi', 1);
INSERT INTO sec_user (name, lastName, email, encryptedPassword, enabled)
VALUES ('Admin', 'Admin', 'admin@fitness.ca', '$2a$10$kmxx2C5yt3EzGQ2BuMdExe.I5tqMv8nDeExsNI6mDN.nOiTr9kwPG', 1);

INSERT INTO location (locName, locAddress, locPhone, locEmail)
VALUES ('MidTown', '2435 Trafalgar Rd., Oakville, ON, L6H4S2', '555123456', 'info.trafalgar@fitness.ca');
INSERT INTO location (locName, locAddress, locPhone, locEmail)
VALUES ('CabbageTown', '245 Third Line, Oakville, ON, L9H4L2', '555123456', 'info.glenabbey@fitness.ca');
INSERT INTO location (locName, locAddress, locPhone, locEmail)
VALUES ('EastView', '245 Ronalds Drive, Oakville, ON, L9H4L2', '555123456', 'info.ronalds@fitness.ca');
INSERT INTO location (locName, locAddress, locPhone, locEmail)
VALUES ('NewView', '245 Ronalds Drive, Oakville, ON, L9H4L2', '555123456', 'info.new@fitness.ca');

INSERT INTO Coach (CoachName, CoachLevel, CoachRating, aboutMe)
    VALUES ('Sally', 3, 5, 'I am focusing success');    
INSERT INTO Coach (CoachName, CoachLevel, CoachRating, aboutMe)
    VALUES ('Adam', 2, 4, 'Cant explain you need to work with:)');    
INSERT INTO Coach (CoachName, CoachLevel, CoachRating, aboutMe)
    VALUES ('Sander', 3, 5, 'I am good.');

INSERT INTO generalCert (certName)
VALUES ('Advanced Fit');
INSERT INTO generalCert (certName)
VALUES ('Bikram Yoga');
INSERT INTO generalCert (certName)
VALUES ('Massage III');
INSERT INTO generalCert (certName)
VALUES ('Cross Fit');

INSERT INTO certCoach (certId, coachId)
    VALUES (1,1);    
INSERT INTO certCoach (certId, coachId)
    VALUES (2,1);    
INSERT INTO certCoach (certId, coachId)
    VALUES (3,1);    
INSERT INTO certCoach (certId, coachId)
    VALUES (4,1);
INSERT INTO certCoach (certId, coachId)
    VALUES (2,2);
INSERT INTO certCoach (certId, coachId)
    VALUES (3,2);
INSERT INTO certCoach (certId, coachId)
    VALUES (4,3);

INSERT INTO Amenity (amenityName, locId)
    VALUES ('Sauna', 1);
INSERT INTO Amenity (AmenityName, LocId)
    VALUES ('Sauna', 2);
INSERT INTO Amenity (AmenityName, LocId)
    VALUES ('Green Zone', 1);
INSERT INTO Amenity (AmenityName, LocId)
    VALUES ('Hot Yoga', 1);
INSERT INTO Amenity (AmenityName, LocId)
    VALUES ('Spinning', 1);
INSERT INTO Amenity (AmenityName, LocId)
    VALUES ('Pool', 1);
INSERT INTO Amenity (AmenityName, LocId)
    VALUES ('Spinning', 2);
INSERT INTO Amenity (AmenityName, LocId)
    VALUES ('Pool', 2);
   
INSERT INTO Room (RoomName, LocId)
    VALUES ('Big Studio', 1);
INSERT INTO Room (RoomName, LocId)
    VALUES ('Small Studio', 1);
INSERT INTO Room (RoomName, LocId)
    VALUES ('Studio 1', 3);
INSERT INTO Room (RoomName, LocId)
    VALUES ('Studio 2', 2);

INSERT INTO fitnessClass (className, classPrice)
VALUES ('Body Pump', 10.0);
INSERT INTO fitnessClass (className, classPrice)
VALUES ('Hiit', 9.50);
INSERT INTO fitnessClass (className, classPrice)
VALUES ('Yoga', 11.0);

INSERT INTO ClassCoach (ClassId, CoachId)
    VALUES (1,1);
INSERT INTO ClassCoach (ClassId, CoachId)
    VALUES (2,1);

INSERT INTO Schedule (ScheduleDate, ScheduleTime, RoomId, ClassId, LocId)
    VALUES ('2022-1-12','09:50', 3, 1, 3);
INSERT INTO Schedule (ScheduleDate, ScheduleTime, RoomId, ClassId, LocId)
    VALUES ('2022-1-12','10:50', 1, 2, 1);
INSERT INTO Schedule (ScheduleDate, ScheduleTime, RoomId, ClassId, LocId)
    VALUES ('2022-1-12','10:20', 2, 1, 1);
INSERT INTO Schedule (ScheduleDate, ScheduleTime, RoomId, ClassId, LocId)
    VALUES ('2022-1-12','09:50', 2, 3, 1);

INSERT INTO Reservation (ClassId, userId)
    VALUES (1,1);
INSERT INTO Reservation (ClassId, userId)
    VALUES (1,2);
INSERT INTO Reservation (ClassId, userId)
    VALUES (2,1);
INSERT INTO Reservation (ClassId, userId)
    VALUES (3,2);
    
INSERT INTO WorkFrom (CoachId, LocId)
    VALUES (1,1);
INSERT INTO WorkFrom (CoachId, LocId)
    VALUES (2,1);
INSERT INTO WorkFrom (CoachId, LocId)
    VALUES (1,2);
INSERT INTO WorkFrom (CoachId, LocId)
    VALUES (2,2);

INSERT INTO Review (CoachId, userId, ReviewDate, ComScore, EnthScore, PunctScore, ReviewText)
    VALUES (1, 1, '2022-1-12', 3, 4, 3, 'Good coach');
INSERT INTO Review (CoachId, userId, ReviewDate, ComScore, EnthScore, PunctScore, ReviewText)
    VALUES (1, 2, '2022-1-12', 4, 5, 3, 'The best ever');
INSERT INTO Review (CoachId, userId, ReviewDate, ComScore, EnthScore, PunctScore)
    VALUES (3, 3, '2022-1-12', 4, 5, 3);
INSERT INTO Review (CoachId, userId, ReviewDate, ComScore, EnthScore, PunctScore)
    VALUES (2, 1, '2022-1-26', 3, 5, 5);

    
INSERT INTO Reference (CoachId, userId)
    VALUES (1, 2);
INSERT INTO Reference (CoachId, userId)
    VALUES (1, 1);
INSERT INTO Reference (CoachId, userId)
    VALUES (1, 3);

INSERT INTO ClientCoach (CoachId, userId, MyCoach)
    VALUES (1, 1, 1);
INSERT INTO ClientCoach (CoachId, userId, MyCoach)
    VALUES (2, 1, 0);
INSERT INTO ClientCoach (CoachId, userId, MyCoach)
    VALUES (2, 3, 1);

 
INSERT INTO userLocation (locId, userId, isHomeLoc, dayOfVisit)
    VALUES (1,1,1, '2022-1-02');    
INSERT INTO userLocation (locId, userId, isHomeLoc, dayOfVisit)
    VALUES (1,1,1, '2022-1-03');    
INSERT INTO userLocation (locId, userId, isHomeLoc, dayOfVisit)
    VALUES (1,1,1, '2022-1-04');    
INSERT INTO userLocation (locId, userId, isHomeLoc, dayOfVisit)
    VALUES (2,1,0, '2022-1-05');    
INSERT INTO userLocation (locId, userId, isHomeLoc, dayOfVisit)
    VALUES (1,2,0, '2022-1-26');    
INSERT INTO userLocation (locId, userId, isHomeLoc, dayOfVisit)
    VALUES (1,1,1, '2022-1-02');    
INSERT INTO userLocation (locId, userId, isHomeLoc, dayOfVisit)
    VALUES (1,2,0, '2022-1-10');    
INSERT INTO userLocation (locId, userId, isHomeLoc, dayOfVisit)
    VALUES (2,2,1, '2022-1-04');    
INSERT INTO userLocation (locId, userId, isHomeLoc, dayOfVisit)
    VALUES (2,1,0, '2022-1-05');    
INSERT INTO userLocation (locId, userId, isHomeLoc, dayOfVisit)
    VALUES (3,1,1, '2021-12-26');

INSERT INTO sec_role (roleName)
VALUES ('ROLE_CLIENT');
INSERT INTO sec_role (roleName)
VALUES ('ROLE_COACH');
INSERT INTO sec_role (roleName)
VALUES ('ROLE_ADMIN');
 
INSERT INTO user_role (userId, roleId)
VALUES (1, 1);
INSERT INTO user_role (userId, roleId)
VALUES (2, 2);
INSERT INTO user_role (userId, roleId)
VALUES (3, 3);




    


