-- VERSION = $Id$
INSERT INTO week
(id, tiebreaker, tiebreaker_answer)
VALUES
( 1, 'How many total points will be scored in BOTH Monday night games (Minnesota-Green Bay and Denver-Oakland) combined?', 0),
( 2, 'How many net passing yards will Dallas have in Monday night''s Eagles-Cowboys game?', 0),
( 3, 'How many net rushing yards will San Diego have in Monday night''s Jets-Chargers game?', 0),
( 4, 'How many total points will be scored in Monday night''s Ravens-Steelers game?', 0),
( 5, 'How many net passing yards will Minnesota have in Monday night''s Vikings-Saints game?', 0),
( 6, 'How many net rushing yards will New York have in Monday night''s Giants-Browns game?', 0),
( 7, 'How many total points will be scored in Monday night''s Broncos-Patriots game?', 0),
( 8, 'How many net passing yards will Indianapolis have in Monday night''s Colts-Titans game?', 0),
( 9, 'How many net rushing yards will Pittsburgh have in Monday night''s Steelers-Redskins game?', 0),
(10, 'How many total points will be scored in Monday night''s 49ers-Cardinals game?', 0),
(11, 'How many net passing yards will Buffalo have in Monday night''s Browns-Bills game?', 0),
(12, 'How many net rushing yards will Green Bay have in Monday night''s Packers-Saints game?', 0),
(13, 'How many total points will be scored in Monday night''s Jaguars-Texans game?', 0),
(14, 'How many net passing yards will Carolina have in Monday night''s Buccaneers-Panthers game?', 0),
(15, 'How many net rushing yards will Philadelphia have in Monday night''s Browns-Eagles game?', 0),
(16, 'How many total points will be scored in Monday night''s Packers-Bears game?', 0),
(17, 'How many total points will be scored in ALL of the final week''s games?', 0);

INSERT INTO game
(id, week_id, played_on, away_team_id, home_team_id)
VALUES
(  1, 1,'2008-09-04',32,21),
(  2, 1,'2008-09-07',29, 4),
(  3, 1,'2008-09-07',22,17),
(  4, 1,'2008-09-07',16,19),
(  5, 1,'2008-09-07',30,20),
(  6, 1,'2008-09-07',11, 2),
(  7, 1,'2008-09-07', 7, 3),
(  8, 1,'2008-09-07',26,24),
(  9, 1,'2008-09-07',13,25),
( 10, 1,'2008-09-07',15,31),
( 11, 1,'2008-09-07', 9, 8),
( 12, 1,'2008-09-07', 5,27),
( 13, 1,'2008-09-07', 1,28),
( 14, 1,'2008-09-07', 6,14),
( 15, 1,'2008-09-08',18,12),
( 16, 1,'2008-09-08',10,23),

( 17, 2,'2008-09-14', 6, 5),
( 18, 2,'2008-09-14',31, 7),
( 19, 2,'2008-09-14',12,11),
( 20, 2,'2008-09-14', 4,15),
( 21, 2,'2008-09-14',23,16),
( 22, 2,'2008-09-14',14,18),
( 23, 2,'2008-09-14',21,26),
( 24, 2,'2008-09-14',20,32),
( 25, 2,'2008-09-14',28,29),
( 26, 2,'2008-09-14', 2,30),
( 27, 2,'2008-09-14',17, 1),
( 28, 2,'2008-09-14',27,10),
( 29, 2,'2008-09-14',19,22),
( 30, 2,'2008-09-14',25, 8),
( 31, 2,'2008-09-15',24, 9),

( 32, 3,'2008-09-21',16, 2),
( 33, 3,'2008-09-21',23, 4),
( 34, 3,'2008-09-21',30, 6),
( 35, 3,'2008-09-21', 5,18),
( 36, 3,'2008-09-21', 1,32),
( 37, 3,'2008-09-21',13,31),
( 38, 3,'2008-09-21',17,19),
( 39, 3,'2008-09-21', 7,21),
( 40, 3,'2008-09-21',20,10),
( 41, 3,'2008-09-21',11,28),
( 42, 3,'2008-09-21',26,29),
( 43, 3,'2008-09-21', 8, 3),
( 44, 3,'2008-09-21',25,24),
( 45, 3,'2008-09-21',15,14),
( 46, 3,'2008-09-21', 9,12),
( 47, 3,'2008-09-22',22,27),

( 48, 4,'2008-09-28', 2, 5),
( 49, 4,'2008-09-28', 8, 7),
( 50, 4,'2008-09-28',13,15),
( 51, 4,'2008-09-28',28,20),
( 52, 4,'2008-09-28',18,31),
( 53, 4,'2008-09-28',12,30),
( 54, 4,'2008-09-28', 1,22),
( 55, 4,'2008-09-28',10,16),
( 56, 4,'2008-09-28',27,23),
( 57, 4,'2008-09-28', 4,26),
( 58, 4,'2008-09-28',32, 9),
( 59, 4,'2008-09-28',24, 6),
( 60, 4,'2008-09-29', 3,25),

( 61, 5,'2008-10-05',31, 3),
( 62, 5,'2008-10-05', 2,12),
( 63, 5,'2008-10-05',14,13),
( 64, 5,'2008-10-05', 6,11),
( 65, 5,'2008-10-05',16, 5),
( 66, 5,'2008-10-05',27,17),
( 67, 5,'2008-10-05',32,24),
( 68, 5,'2008-10-05',29,21),
( 69, 5,'2008-10-05',30,10),
( 70, 5,'2008-10-05',19,28),
( 71, 5,'2008-10-05', 7, 9),
( 72, 5,'2008-10-05', 4, 1),
( 73, 5,'2008-10-05',25,15),
( 74, 5,'2008-10-06',18,20),

( 75, 6,'2008-10-12', 6, 2),
( 76, 6,'2008-10-12',17,13),
( 77, 6,'2008-10-12', 3,14),
( 78, 6,'2008-10-12',11,18),
( 79, 6,'2008-10-12',23,20),
( 80, 6,'2008-10-12', 7,22),
( 81, 6,'2008-10-12', 5,30),
( 82, 6,'2008-10-12',26,32),
( 83, 6,'2008-10-12',15,10),
( 84, 6,'2008-10-12', 9, 1),
( 85, 6,'2008-10-12',24,28),
( 86, 6,'2008-10-12',12,29),
( 87, 6,'2008-10-12',19,27),
( 88, 6,'2008-10-13',21, 8),
 
( 89, 7,'2008-10-19',27, 4),
( 90, 7,'2008-10-19',20, 5),
( 91, 7,'2008-10-19',18, 6),
( 92, 7,'2008-10-19',25, 7),
( 93, 7,'2008-10-19',31,16),
( 94, 7,'2008-10-19', 3,17),
( 95, 7,'2008-10-19',28,21),
( 96, 7,'2008-10-19', 9,26),
( 97, 7,'2008-10-19',11,13),
( 98, 7,'2008-10-19',14,12),
( 99, 7,'2008-10-19',22,23),
(100, 7,'2008-10-19', 8,32),
(101, 7,'2008-10-19',29,30),
(102, 7,'2008-10-20',10,19),

(103, 8,'2008-10-26', 7,13),
(104, 8,'2008-10-26',23, 3),
(105, 8,'2008-10-26',32,11),
(106, 8,'2008-10-26',30, 9),
(107, 8,'2008-10-26', 1, 5),
(108, 8,'2008-10-26', 4,17),
(109, 8,'2008-10-26',27,20),
(110, 8,'2008-10-26', 2,24),
(111, 8,'2008-10-26',16,22),
(112, 8,'2008-10-26',26,19),
(113, 8,'2008-10-26', 8,15),
(114, 8,'2008-10-26',21,25),
(115, 8,'2008-10-26',29,28),
(116, 8,'2008-10-27',14,31),

(117, 9,'2008-11-02',22, 4),
(118, 9,'2008-11-02', 3, 8),
(119, 9,'2008-11-02',12,31),
(120, 9,'2008-11-02', 1,26),
(121, 9,'2008-11-02',13,18),
(122, 9,'2008-11-02',30,16),
(123, 9,'2008-11-02',15, 7),
(124, 9,'2008-11-02',11, 6),
(125, 9,'2008-11-02',17,10),
(126, 9,'2008-11-02', 9,21),
(127, 9,'2008-11-02', 2,23),
(128, 9,'2008-11-02',24,29),
(129, 9,'2008-11-02',19,14),
(130, 9,'2008-11-03',25,32),

(131,10,'2008-11-06',10, 8),
(132,10,'2008-11-09', 4,19),
(133,10,'2008-11-09',15,11),
(134,10,'2008-11-09',29,17),
(135,10,'2008-11-09',12,18),
(136,10,'2008-11-09', 3,13),
(137,10,'2008-11-09',31, 6),
(138,10,'2008-11-09',26,22),
(139,10,'2008-11-09',20, 2),
(140,10,'2008-11-09', 5,23),
(141,10,'2008-11-09',14,25),
(142,10,'2008-11-09',16,27),
(143,10,'2008-11-09',21,24),
(144,10,'2008-11-10',28, 1),

(145,11,'2008-11-13',22,19),
(146,11,'2008-11-16',10, 2),
(147,11,'2008-11-16',11, 5),
(148,11,'2008-11-16',24, 7),
(149,11,'2008-11-16', 6,12),
(150,11,'2008-11-16',13,14),
(151,11,'2008-11-16',31,15),
(152,11,'2008-11-16',20,16),
(153,11,'2008-11-16',23,17),
(154,11,'2008-11-16', 3,21),
(155,11,'2008-11-16',18,30),
(156,11,'2008-11-16',26,28),
(157,11,'2008-11-16', 1,29),
(158,11,'2008-11-16',27,25),
(159,11,'2008-11-16', 9,32),
(160,11,'2008-11-17', 8, 4),

(161,12,'2008-11-20', 7,25),
(162,12,'2008-11-23', 5, 2),
(163,12,'2008-11-23',24, 3),
(164,12,'2008-11-23',13, 8),
(165,12,'2008-11-23',28, 9),
(166,12,'2008-11-23',30,11),
(167,12,'2008-11-23',18,15),
(168,12,'2008-11-23',22,31),
(169,12,'2008-11-23', 4,16),
(170,12,'2008-11-23',19,17),
(171,12,'2008-11-23', 6,26),
(172,12,'2008-11-23',23,10),
(173,12,'2008-11-23',21, 1),
(174,12,'2008-11-23',32,29),
(175,12,'2008-11-23',14,27),
(176,12,'2008-11-24',12,20),

(177,13,'2008-11-27',31,11),
(178,13,'2008-11-27',29, 9),
(179,13,'2008-11-27', 1,24),
(180,13,'2008-11-30',28, 4),
(181,13,'2008-11-30',14, 8),
(182,13,'2008-11-30',10,22),
(183,13,'2008-11-30',20,30),
(184,13,'2008-11-30',21,32),
(185,13,'2008-11-30',17,26),
(186,13,'2008-11-30', 5,12),
(187,13,'2008-11-30', 3, 7),
(188,13,'2008-11-30', 2,27),
(189,13,'2008-11-30',25,19),
(190,13,'2008-11-30',16,23),
(191,13,'2008-11-30', 6,18),
(192,13,'2008-12-01',15,13),

(193,14,'2008-12-04',23,27),
(194,14,'2008-12-07',32, 3),
(195,14,'2008-12-07',15, 6),
(196,14,'2008-12-07',18,11),
(197,14,'2008-12-07', 7,14),
(198,14,'2008-12-07',24,21),
(199,14,'2008-12-07', 8,31),
(200,14,'2008-12-07', 2,20),
(201,14,'2008-12-07',13,12),
(202,14,'2008-12-07',22,28),
(203,14,'2008-12-07',17, 4),
(204,14,'2008-12-07',16,10),
(205,14,'2008-12-07',26, 1),
(206,14,'2008-12-07', 9,25),
(207,14,'2008-12-07',19,29),
(208,14,'2008-12-08',30, 5),

(209,15,'2008-12-11',20, 6),
(210,15,'2008-12-14',30, 2),
(211,15,'2008-12-14',25, 3),
(212,15,'2008-12-14',10, 5),
(213,15,'2008-12-14',32, 7),
(214,15,'2008-12-14',31,13),
(215,15,'2008-12-14',11,14),
(216,15,'2008-12-14',12,15),
(217,15,'2008-12-14',27,16),
(218,15,'2008-12-14',28,17),
(219,15,'2008-12-14', 4,22),
(220,15,'2008-12-14',29,26),
(221,15,'2008-12-14',18, 1),
(222,15,'2008-12-14',19,23),
(223,15,'2008-12-14',21, 9),
(224,15,'2008-12-15', 8,24),

(225,16,'2008-12-18',14,15),
(226,16,'2008-12-20', 3, 9),
(227,16,'2008-12-21', 7, 8),
(228,16,'2008-12-21',20,11),
(229,16,'2008-12-21',17,16),
(230,16,'2008-12-21', 2,18),
(231,16,'2008-12-21', 1,19),
(232,16,'2008-12-21', 5,21),
(233,16,'2008-12-21',28,26),
(234,16,'2008-12-21',25,31),
(235,16,'2008-12-21',24,32),
(236,16,'2008-12-21', 4,10),
(237,16,'2008-12-21',13,23),
(238,16,'2008-12-21',22,29),
(239,16,'2008-12-21',27,30),
(240,16,'2008-12-22',12, 6),

(241,17,'2008-12-28',26, 2),
(242,17,'2008-12-28',15, 3),
(243,17,'2008-12-28',19, 4),
(244,17,'2008-12-28',16, 7),
(245,17,'2008-12-28',11,12),
(246,17,'2008-12-28', 6,13),
(247,17,'2008-12-28',31,14),
(248,17,'2008-12-28',21,18),
(249,17,'2008-12-28', 5,20),
(250,17,'2008-12-28',17,22),
(251,17,'2008-12-28', 9,24),
(252,17,'2008-12-28', 8,25),
(253,17,'2008-12-28',23,30),
(254,17,'2008-12-28',29, 1),
(255,17,'2008-12-28',10,27),
(256,17,'2008-12-28',32,28);
