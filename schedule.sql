CREATE TABLE schedule(
                         id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
                         title VARCHAR(255) COMMENT '일정 제목',
                         contents TEXT COMMENT '일정 내용',
                         created_at DATETIME(6) COMMENT '작성일',
                         updated_at DATETIME(6) COMMENT '수정일'
);

CREATE TABLE user(
                     id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '유저 식별자',
                     username VARCHAR(255) COMMENT '이름',
                     email VARCHAR(255) COMMENT '이메일',
                     password VARCHAR(255) COMMENT '비밀번호',
                     created_at DATETIME(6) COMMENT '작성일',
                     updated_at DATETIME(6) COMMENT '수정일'
);


CREATE TABLE comment(
                         id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '댓글 식별자',
                         content TEXT COMMENT '댓글 내용'
);
