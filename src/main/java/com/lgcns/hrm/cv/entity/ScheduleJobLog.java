package com.lgcns.hrm.cv.entity;
import com.lgcns.hrm.cv.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "schedule_job_log", indexes = {
        @Index(name = "idx_sch_job_log_id_idx",columnList = "job_id"),
        @Index(name = "idx_sch_job_log_key_idx",columnList = "job_id"),
})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "schedule_job_log")
public class ScheduleJobLog extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    /**
     * task id
     */
    @NotNull(message = "Task id cannot be empty")
    @Column(name = "job_id")
    private String jobId;

    @Column(name = "job_key")
    private String jobKey;

    /**
     * mission name
     */
    @NotNull(message = "Task name cannot be empty")
    @Column(name = "job_name",length = 50)
    private String jobName;

    /**
     * execute expression
     */
    @NotNull(message = "Execution expression cannot be empty")
    @Column(name = "cron",length = 100)
    private String cron;

    /**
     * parameter json string
     */
    @Column(name = "param_json")
    private String paramJson;

    /**
     * Starting time
     */
    @Column(name = "start_time")
    private LocalDateTime startTime;

    /**
     * End Time
     */
    @Column(name = "end_time")
    private LocalDateTime endTime;

    /**
     * time spent (seconds)
     */
    @Column(name = "elapsed_seconds")
    private Long elapsedSeconds;

    /**
     * execution status
     */
    @Column(name = "run_status")
    private String runStatus;

    /**
     * Number of data executions
     */
    @Column(name = "data_count")
    private Integer dataCount;

    /**
     * Execution result information
     */
    @Column(name = "execute_msg", columnDefinition = "TEXT")
    private String executeMsg;
}
