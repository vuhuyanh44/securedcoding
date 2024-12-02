package com.lgcns.hrm.cv.entity;

import com.lgcns.hrm.cv.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "log")
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class Log extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "function_name")
    private String functionName;

    private String message;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "ID")
    private User user;

    @Column(name = "class_name")
    private String className;

    @Column(name = "thread_name")
    private String threadName;

    @Column(name = "thread_id")
    private Long threadId;

    @Column(name = "method_name")
    private Long methodName;
}
