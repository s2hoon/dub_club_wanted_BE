-- Inserting data into the "member" table
INSERT INTO `member` (`member_id`, `email`, `gender`, `name`, `password`, `role`)
VALUES (1, 'suhoon@naver.com', '남자', '조수훈', '1234', 'USER');

INSERT INTO `member` (`member_id`, `email`, `gender`, `name`, `password`, `role`)
VALUES (2, 'sohoon@naver.com', '여자', '조소훈', '1234', 'USER');

-- Inserting data into the "club" table
INSERT INTO `club` (`club_id`, `club_image_url`, `club_name`, `group_name`, `introduction`, `question1`, `member_id`)
VALUES (1, 'naver.com', '멋쟁이사지', '코딩동아리', '안녕하세요!!멋재이사자입니다.', '지원동기??', 1);

INSERT INTO `club` (`club_id`, `club_image_url`, `club_name`, `group_name`, `introduction`, `question1`, `member_id`)
VALUES (2, 'naver.com', 'UMC','코딩동아리2', '안녕하세요!!UMC입니다.', '지원동기??', 2);
