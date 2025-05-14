package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.response.TodoCustomResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoQueryRepositoryCustom {
    Optional<Todo> findByIdWithUser(Long todoId);
    Page<TodoCustomResponse> findByTitleWithManagerAndDate(String title, LocalDateTime createdAt, String nickname, Pageable pageable);
}
