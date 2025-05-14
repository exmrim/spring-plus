package org.example.expert.domain.todo.dto.response;

import lombok.Getter;

@Getter
public class TodoCustomResponse { //Lv3-10. QueryDSL 을 사용하여 검색 기능 만들기

    private final String title;
    private final Long commentCount;
    private final Long managerCount;

    public TodoCustomResponse(String title, Long commentCount, Long managerCount) {
        this.title = title;
        this.commentCount = commentCount;
        this.managerCount = managerCount;
    }
}
