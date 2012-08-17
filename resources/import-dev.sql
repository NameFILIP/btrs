-- SQL statements which are executed at application startup if hibernate.hbm2ddl.auto is 'create' or 'create-drop'
INSERT INTO application_variable(id, name, value) VALUES (1, 'carCoefficient', 6);

INSERT INTO application_variable(id, name, value) VALUES (2, 'TAIPEI_TAIPEI', 0);
INSERT INTO application_variable(id, name, value) VALUES (3, 'TAIPEI_BANQIAO', 35);
INSERT INTO application_variable(id, name, value) VALUES (4, 'TAIPEI_TAOYUAN', 155);
INSERT INTO application_variable(id, name, value) VALUES (5, 'TAIPEI_HSINCHU', 280);
INSERT INTO application_variable(id, name, value) VALUES (6, 'TAIPEI_TAICHUNG', 675);
INSERT INTO application_variable(id, name, value) VALUES (7, 'TAIPEI_CHIAYI', 1045);
INSERT INTO application_variable(id, name, value) VALUES (8, 'TAIPEI_TAINAN', 1305);
INSERT INTO application_variable(id, name, value) VALUES (9, 'TAIPEI_ZUOYING', 1445);

INSERT INTO application_variable(id, name, value) VALUES (10, 'BANQIAO_TAIPEI', 35);
INSERT INTO application_variable(id, name, value) VALUES (11, 'BANQIAO_BANQIAO', 0);
INSERT INTO application_variable(id, name, value) VALUES (12, 'BANQIAO_TAOYUAN', 125);
INSERT INTO application_variable(id, name, value) VALUES (13, 'BANQIAO_HSINCHU', 250);
INSERT INTO application_variable(id, name, value) VALUES (14, 'BANQIAO_TAICHUNG', 645);
INSERT INTO application_variable(id, name, value) VALUES (15, 'BANQIAO_CHIAYI', 1015);
INSERT INTO application_variable(id, name, value) VALUES (16, 'BANQIAO_TAINAN', 1280);
INSERT INTO application_variable(id, name, value) VALUES (17, 'BANQIAO_ZUOYING', 1415);

INSERT INTO application_variable(id, name, value) VALUES (18, 'TAOYUAN_TAIPEI', 155);
INSERT INTO application_variable(id, name, value) VALUES (19, 'TAOYUAN_BANQIAO', 125);
INSERT INTO application_variable(id, name, value) VALUES (20, 'TAOYUAN_TAOYUAN', 0);
INSERT INTO application_variable(id, name, value) VALUES (21, 'TAOYUAN_HSINCHU', 125);
INSERT INTO application_variable(id, name, value) VALUES (22, 'TAOYUAN_TAICHUNG', 520);
INSERT INTO application_variable(id, name, value) VALUES (23, 'TAOYUAN_CHIAYI', 890);
INSERT INTO application_variable(id, name, value) VALUES (24, 'TAOYUAN_TAINAN', 1150);
INSERT INTO application_variable(id, name, value) VALUES (25, 'TAOYUAN_ZUOYING', 1290);

INSERT INTO application_variable(id, name, value) VALUES (26, 'HSINCHU_TAIPEI', 280);
INSERT INTO application_variable(id, name, value) VALUES (27, 'HSINCHU_BANQIAO', 250);
INSERT INTO application_variable(id, name, value) VALUES (28, 'HSINCHU_TAOYUAN', 125);
INSERT INTO application_variable(id, name, value) VALUES (29, 'HSINCHU_HSINCHU', 0);
INSERT INTO application_variable(id, name, value) VALUES (30, 'HSINCHU_TAICHUNG', 395);
INSERT INTO application_variable(id, name, value) VALUES (31, 'HSINCHU_CHIAYI', 765);
INSERT INTO application_variable(id, name, value) VALUES (32, 'HSINCHU_TAINAN', 1025);
INSERT INTO application_variable(id, name, value) VALUES (33, 'HSINCHU_ZUOYING', 1160);

INSERT INTO application_variable(id, name, value) VALUES (34, 'TAICHUNG_TAIPEI', 675);
INSERT INTO application_variable(id, name, value) VALUES (35, 'TAICHUNG_BANQIAO', 645);
INSERT INTO application_variable(id, name, value) VALUES (36, 'TAICHUNG_TAOYUAN', 520);
INSERT INTO application_variable(id, name, value) VALUES (37, 'TAICHUNG_HSINCHU', 395);
INSERT INTO application_variable(id, name, value) VALUES (38, 'TAICHUNG_TAICHUNG', 0);
INSERT INTO application_variable(id, name, value) VALUES (39, 'TAICHUNG_CHIAYI', 365);
INSERT INTO application_variable(id, name, value) VALUES (40, 'TAICHUNG_TAINAN', 630);
INSERT INTO application_variable(id, name, value) VALUES (41, 'TAICHUNG_ZUOYING', 765);

INSERT INTO application_variable(id, name, value) VALUES (42, 'CHIAYI_TAIPEI', 1045);
INSERT INTO application_variable(id, name, value) VALUES (43, 'CHIAYI_BANQIAO', 1015);
INSERT INTO application_variable(id, name, value) VALUES (44, 'CHIAYI_TAOYUAN', 890);
INSERT INTO application_variable(id, name, value) VALUES (45, 'CHIAYI_HSINCHU', 765);
INSERT INTO application_variable(id, name, value) VALUES (46, 'CHIAYI_TAICHUNG', 365);
INSERT INTO application_variable(id, name, value) VALUES (47, 'CHIAYI_CHIAYI', 0);
INSERT INTO application_variable(id, name, value) VALUES (48, 'CHIAYI_TAINAN', 270);
INSERT INTO application_variable(id, name, value) VALUES (49, 'CHIAYI_ZUOYING', 395);

INSERT INTO application_variable(id, name, value) VALUES (50, 'TAINAN_TAIPEI', 1305);
INSERT INTO application_variable(id, name, value) VALUES (51, 'TAINAN_BANQIAO', 1280);
INSERT INTO application_variable(id, name, value) VALUES (52, 'TAINAN_TAOYUAN', 1150);
INSERT INTO application_variable(id, name, value) VALUES (53, 'TAINAN_HSINCHU', 1025);
INSERT INTO application_variable(id, name, value) VALUES (54, 'TAINAN_TAICHUNG', 630);
INSERT INTO application_variable(id, name, value) VALUES (55, 'TAINAN_CHIAYI', 270);
INSERT INTO application_variable(id, name, value) VALUES (56, 'TAINAN_TAINAN', 0);
INSERT INTO application_variable(id, name, value) VALUES (57, 'TAINAN_ZUOYING', 135);

INSERT INTO application_variable(id, name, value) VALUES (58, 'ZUOYING_TAIPEI', 1445);
INSERT INTO application_variable(id, name, value) VALUES (59, 'ZUOYING_BANQIAO', 1415);
INSERT INTO application_variable(id, name, value) VALUES (60, 'ZUOYING_TAOYUAN', 1290);
INSERT INTO application_variable(id, name, value) VALUES (61, 'ZUOYING_HSINCHU', 1160);
INSERT INTO application_variable(id, name, value) VALUES (62, 'ZUOYING_TAICHUNG', 765);
INSERT INTO application_variable(id, name, value) VALUES (63, 'ZUOYING_CHIAYI', 395);
INSERT INTO application_variable(id, name, value) VALUES (64, 'ZUOYING_TAINAN', 135);
INSERT INTO application_variable(id, name, value) VALUES (65, 'ZUOYING_ZUOYING', 0);


