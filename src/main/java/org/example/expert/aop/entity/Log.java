package org.example.expert.aop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.expert.domain.common.entity.Timestamped;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "log")
public class Log extends Timestamped { //Lv3-11.Transaction 심화

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ActionType actionType;  // "CREATE", "UPDATE", "DELETE"
    private String targetTable;     // "user", "manager"
    private Long targetId;          // 1L
    private Long actorId;           // 현재 로그인한 userId
    private String oldValue;        // 변경 전 내용
    private String newValue;        // 변경 후 내용
    @Setter
    @Enumerated(EnumType.STRING)
    private ResultType result;      // "SUCCESS", "FAIL"

    public Log(ActionType actionType, String targetTable, Long targetId, Long actorId, String oldValue, String newValue, ResultType result) {
        this.actionType = actionType;
        this.targetTable = targetTable;
        this.targetId = targetId;
        this.actorId = actorId;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.result = result;
    }
}
