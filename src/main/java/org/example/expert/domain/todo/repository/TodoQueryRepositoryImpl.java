package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.entity.QComment;
import org.example.expert.domain.manager.entity.QManager;
import org.example.expert.domain.todo.dto.response.TodoCustomResponse;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class TodoQueryRepositoryImpl implements TodoQueryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    //Lv2-8. QueryDSL
    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        QTodo todo = QTodo.todo;

        Todo result = jpaQueryFactory
                .selectFrom(todo)
                .leftJoin(todo.user)
                .where(todo.id.eq(todoId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    //Lv3-10. QueryDSL 을 사용하여 검색 기능 만들기
    @Override
    public Page<TodoCustomResponse> findByTitleWithManagerAndDate(String title, LocalDateTime createdAt, String nickname, Pageable pageable) {
        QTodo todo = QTodo.todo;
        QManager manager = QManager.manager;
        QComment comment = QComment.comment;

        List<TodoCustomResponse> result = jpaQueryFactory
                .select(Projections.fields(TodoCustomResponse.class,
                        todo.title.as("title"),
                        comment.count().as("commentCount"),
                        manager.count().as("managerCount")
                ))
                .from(todo)
                .leftJoin(todo.comments, comment)
                .leftJoin(todo.managers, manager)
                .where(
                        eqTitle(title),
                        eqCreated(createdAt),
                        eqNickname(nickname)
                )
                .groupBy(todo.title)
                .orderBy(todo.createdAt.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .select(todo.count())
                .from(todo)
                .where(
                        eqTitle(title),
                        eqCreated(createdAt),
                        eqNickname(nickname)
                )
                .fetchOne();

        return new PageImpl<>(result, pageable, total);
    }

    private BooleanExpression eqTitle(String title) {
        QTodo todo = QTodo.todo;

        return StringUtils.hasText(title) ? todo.title.like(title) : null;
    }

    private BooleanExpression eqCreated(LocalDateTime createdAt) {
        QTodo todo = QTodo.todo;

        if(createdAt == null) {
            return null;
        }

        LocalDateTime startedAt = createdAt.toLocalDate().atStartOfDay();
        LocalDateTime endedAt = startedAt.plusDays(1).minusSeconds(1);

        return todo.createdAt.between(startedAt, endedAt);
    }

    private BooleanExpression eqNickname(String nickname) {
        QTodo todo = QTodo.todo;

        return StringUtils.hasText(nickname) ? todo.title.like(nickname) : null;
    }


}
