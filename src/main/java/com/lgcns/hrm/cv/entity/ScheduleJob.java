package com.lgcns.hrm.cv.entity;

import com.lgcns.hrm.cv.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@Table(
        name = "schedule_job",
        indexes = {@Index(name = "job_key_idx", columnList = "job_key")}
)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "schedule_job")
public class ScheduleJob extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @NotNull(message = "Task cannot be empty")
    @Column(name = "job_key")
    private String jobKey;

    /**
     * job name
     */
    @NotNull(message = "Name cannot be empty")
    @Column(name = "job_name", length = 50)
    private String jobName;

    /**
     * Timed execution expression
     */
    @NotNull(message = "Timed expression cannot be empty")
    @Column(name = "cron", length = 100)
    private String cron;

    /**
     * parameter json string
     */
    @Column(name = "param_json")
    private String paramJson;

    /**
     * Initial strategy
     */
    @Column(name = "init_strategy")
    private String initStrategy;

    /**
     * state
     */
    @NotNull(message = "Status cannot be empty")
    @Column(name = "job_status", length = 10)
    private String jobStatus;

    /**
     * Whether to save the log, the default is true
     */
    @Column(name = "save_log")
    private Boolean saveLog;

    /**
     * Remark
     */
    @Column(name = "job_comment", length = 200)
    private String jobComment;

}
