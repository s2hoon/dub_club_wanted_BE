insert into `member` (`member_id`, `email`, `gender`, `name`, `password`, `role`)
values (1, 'suhoon@naver.com','남자','조수훈','1234', 'ROLE_CLUB');

insert into `member` (`member_id`, `email`, `gender`, `name`, `password`, `role`)
values (2, 'sohoon@naver.com','여자','조소훈','1234', 'ROLE_CLUB');

-- Inserting data into the "club" table
INSERT INTO `club` (`club_id`, `club_image_url`, `club_name`, `group_name`, `introduction`, `question1`, `member_id`)
VALUES (1, 'naver.com', '멋쟁이사지', '코딩동아리', '안녕하세요!!멋재이사자입니다.', '지원동기??', 1);

INSERT INTO `club` (`club_id`, `club_image_url`, `club_name`, `group_name`, `introduction`, `question1`, `member_id`)
VALUES (2, 'naver.com', 'UMC','코딩동아리2', '안녕하세요!!UMC입니다.', '지원동기??', 2);

-- Inserting data into the "post" table
INSERT INTO `post` (`id`, `club_id`, `post_title`, `content`, `post_image`)
VALUES (1, 1, 'First Post in 멋쟁이사지', '안녕하세요! 멋쟁이사자의 첫 번째 글입니다.', 'image_url1.jpg');

INSERT INTO `post` (`id`, `club_id`, `post_title`, `content`, `post_image`)
VALUES (2, 2, 'First Post in UMC', '안녕하세요! UMC의 첫 번째 글입니다.', 'image_url2.jpg');

