package com.lgcns.hrm.cv.entity;

import com.lgcns.hrm.cv.entity.base.BaseEntity;
import com.lgcns.hrm.cv.model.enums.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "notification")
@Getter
@Setter
@Accessors(chain = true)
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "ID")
    private User user;

    private String message;

    @Column(name = "notification_type")
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Column(name = "reference_id")
    private Integer referenceId;

    @Column(name = "is_read")
    private Boolean isRead;
}
