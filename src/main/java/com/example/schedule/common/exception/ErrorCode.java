package com.example.schedule.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //common
    INVALID_INPUT_VALUE(400,"Bad Request", "C001","입력한 값을 확인해 주세요."),


    NOT_LOGIN(401,"Not Login","U004","로그인한 유저만 접근할 수 있습니다."),

    //user
    USER_LOGIN_FAIL(401, "Login Failed","U001","아이디와 비밀번호를 다시 확인해 주세요."),
    USER_NOT_FOUND(404, "Not Found", "U002", "존재하지 않는 사용자입니다"),
    WRONG_PASSWORD(401,"Wrong Password","U003", "비밀번호를 다시 확인해 주세요."),


    //schedule
    SCHEDULE_NOT_FOUND(404,"Not Found","S001","일정을 찾을 수 없습니다"),
    MISMATCH_USER(403, "Mismatch User","S002", "접근 권한이 없습니다."),


    //comment
    COMMENT_NOT_FOUND(404,"Not Found","C001","댓글을 찾을 수 없습니다"),
    UNAUTHORIZED_COMMENT_UPDATE(403,"Forbidden","C002","댓글 작성자만 댓글을 수정할 수 있습니다."),
    UNAUTHORIZED_COMMENT_DELETE(403,"Forbidden","C003","댓글 작성자 혹은 게시글 작성자만 댓글을 삭제할 수 있습니다.");

    private final int status;
    private final String error;
    private final String code;
    private final String message;
}
