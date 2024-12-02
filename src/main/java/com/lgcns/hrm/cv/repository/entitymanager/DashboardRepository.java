package com.lgcns.hrm.cv.repository.entitymanager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgcns.hrm.cv.common.utils.JsonUtil;
import com.lgcns.hrm.cv.model.vo.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @author pigx
 */
@Slf4j
@Repository
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class DashboardRepository {
    private final EntityManager entityManager;
    private final ObjectMapper objectMapper;

    public List<DashboardTypeVo> getNumberOfCVSentByPartnersOverTime(String startTime, String endTime) {
        String sql = """
                WITH recursive
                    MONTH_TABLE(dt, init) as (select :startTime as dt, 0 as init
                                             union all
                                             select dt + interval 1 MONTH, 0
                                             from MONTH_TABLE
                                             where dt + interval 1 MONTH <= :endTime),
                    TYPE AS (SELECT sp.id as type, sp.code as code
                            FROM sys_param sp
                                     INNER JOIN sys_type st ON sp.sys_type_id = st.id
                            WHERE st.code = 'CV_TYPE'),
                    FORMAT_MONTH as (SELECT date_format(M.dt, '%Y%m') as receive_cv_time, M.init, T.type, T.code
                                    FROM MONTH_TABLE as M,
                                         TYPE as T),
                    DATA as (SELECT type, count(1) as total, F.receive_cv_time
                            FROM (SELECT ifnull(type, 'Other')                                     as type,
                                         date_format(ifnull(receive_cv_date, create_time), '%Y%m') as receive_cv_time
                                  FROM candidates
                                  WHERE create_time >= :startTime
                                    AND create_time < :endTime
                                  order by receive_cv_time) as F
                            GROUP BY F.type, F.receive_cv_time)
                                
                SELECT A.code as type, GROUP_CONCAT(A.total ORDER BY A.receive_cv_time) as total
                FROM (SELECT F.type, F.receive_cv_time, F.init + IFNULL(D.total, 0) as total, F.code
                 FROM FORMAT_MONTH as F
                          LEFT JOIN DATA as D ON D.receive_cv_time = F.receive_cv_time AND F.type = D.type) AS A
                GROUP BY A.code;
                """;
        Query query = entityManager.createNativeQuery(sql, Tuple.class)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime);
        List<Tuple> data = query.getResultList();

        return data.stream()
                .map(p -> {
                    var maps = new HashMap<String, Object>();
                    p.getElements().forEach(te -> maps.put(te.getAlias(), p.get(te.getAlias())));
                    return JsonUtil.getInstance().convertValue(maps, new TypeReference<DashboardTypeVo>() {
                    });
                }).toList();
    }

    public List<DashboardPassFailRateVo> getPassFailRate(String startTime, String endTime) {
        String sql = """
                           
                SELECT S.status,
                     IFNULL(S.total + F.total, 0) as total
                FROM (SELECT 'PASS' as status, 0 as total
                    UNION ALL
                    SELECT 'FAIL' as status, 0 as total
                    UNION ALL
                    SELECT 'NEW' as status, 0 as total) AS S
                       LEFT JOIN (SELECT CASE
                                             WHEN rst.code = 'REJECT' THEN 'FAIL'
                                             WHEN rst.code is null THEN 'NEW'
                                             ELSE 'PASS' END as status,
                                         count(1)            as total
                                  FROM candidates c
                                           LEFT JOIN (SELECT sp.id, sp.code
                                                      FROM sys_param sp
                                                               INNER JOIN sys_type st ON st.id = sp.sys_type_id
                                                      WHERE st.code = 'CV_STATUS') as rst ON c.status_review = rst.id
                                
                                  WHERE c.update_time >= :startTime
                                    AND c.update_time < :endTime
                                  GROUP BY status_review) AS F ON S.status = F.status;
                """;
        Query query = entityManager.createNativeQuery(sql, Tuple.class)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime);
        List<Tuple> data = query.getResultList();

        return data.stream()
                .map(p -> {
                    var maps = new HashMap<String, Object>();
                    p.getElements().forEach(te -> maps.put(te.getAlias(), p.get(te.getAlias())));
                    return JsonUtil.getInstance().convertValue(maps, new TypeReference<DashboardPassFailRateVo>() {
                    });
                }).toList();
    }

    public List<DashboardCountPassFailVo> countNumberOfCVAccordingToFailedPass(String startTime, String endTime) {
        String sql = """
                WITH
                    recursive
                    MONTH_TABLE(dt, init) as (select :startTime as dt, 0 as init
                                              union all
                                              select dt + interval 1 MONTH, 0
                                              from MONTH_TABLE
                                              where dt + interval 1 MONTH <= :endTime),
                    STATUS as (SELECT 'PASS' as status, 0 as total
                               UNION ALL
                               SELECT 'FAIL' as status, 0 as total
                               UNION ALL
                               SELECT 'NEW' as type, 0 as total),
                    MONTH_STATUS AS (SELECT date_format(M.dt, '%Y%m') as receive_cv_time, M.init, T.status
                                     FROM MONTH_TABLE as M,
                                          STATUS as T),
                    DATA AS (SELECT F.status, count(1) as total, F.receive_cv_time
                             FROM (SELECT CASE
                                              WHEN rst.code = 'REJECT' THEN 'FAIL'
                                              WHEN rst.code is null THEN 'NEW'
                                              ELSE 'PASS' END                                       as status,
                                          date_format(ifnull(receive_cv_date, update_time), '%Y%m') as receive_cv_time
                                   FROM candidates c
                                            LEFT JOIN (SELECT sp.id, sp.code
                                                       FROM sys_param sp
                                                                INNER JOIN sys_type st ON st.id = sp.sys_type_id
                                                       WHERE st.code = 'CV_STATUS') as rst ON c.status_review = rst.id
                                   WHERE c.update_time >= :startTime
                                     AND c.update_time < :endTime) AS F
                             GROUP BY F.status, F.receive_cv_time),
                    SELECT_DATA AS (SELECT S.status, S.receive_cv_time, IFNULL(S.init + D.total, 0) as total
                                    FROM MONTH_STATUS as S
                                             LEFT JOIN DATA as D ON S.status = D.status AND S.receive_cv_time = D.receive_cv_time)
                SELECT MONTHNAME(str_to_date(P.receive_cv_time, '%Y%m')) as month,
                       CONCAT_WS(',', P.total, F.total, N.total)         as total
                FROM (SELECT * FROM SELECT_DATA WHERE status = 'PASS') AS P
                         INNER JOIN
                         (SELECT * FROM SELECT_DATA WHERE status = 'FAIL') AS F ON P.receive_cv_time = F.receive_cv_time
                         INNER JOIN (SELECT * FROM SELECT_DATA WHERE status = 'NEW') AS N ON N.receive_cv_time = F.receive_cv_time;
                """;
        Query query = entityManager.createNativeQuery(sql, Tuple.class)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime);
        List<Tuple> data = query.getResultList();

        return data.stream()
                .map(p -> {
                    var maps = new HashMap<String, Object>();
                    p.getElements().forEach(te -> maps.put(te.getAlias(), p.get(te.getAlias())));
                    return JsonUtil.getInstance().convertValue(maps, new TypeReference<DashboardCountPassFailVo>() {
                    });
                }).toList();
    }

    public List<DashboardStatusAndResourceVo> getStatusAndResourceCv(String startTime, String endTime) {
        String sql = """
                WITH DATA as (SELECT SUM(IF(F.code IS NULL, 1, 0)) as NEW_CV,
                                               SUM(IF(F.code = 'REJECT', 1, 0)) as REJECT_CV,
                                               SUM(IF(F.code IS NULL OR F.code ='REJECT', 0, 1)) as PROCESSED_CV,
                                               SUM(IF(F.reference IS NULL, 0, 1))     as REFERRAL_CV
                                        FROM (SELECT rst.code, reference
                                              FROM candidates c
                                                       LEFT JOIN (SELECT sp.id, sp.code
                                                                  FROM sys_param sp
                                                                           INNER JOIN sys_type st ON st.id = sp.sys_type_id
                                                                  WHERE st.code = 'CV_STATUS') as rst ON c.status_review = rst.id
                                              WHERE c.update_time >= :startTime
                                                AND c.update_time < :endTime) as F)
                          SELECT 'NEW_CV' as label, NEW_CV as total
                          FROM DATA
                          UNION
                          SELECT 'REJECT_CV' as label, REJECT_CV as total
                          FROM DATA
                          UNION
                          SELECT 'PROCESSED_CV' as label, PROCESSED_CV as total
                          FROM DATA
                          UNION
                          SELECT 'REFERRAL_CV' as label, REFERRAL_CV as total
                          FROM DATA;
                """;
        Query query = entityManager.createNativeQuery(sql, Tuple.class)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime);
        List<Tuple> data = query.getResultList();

        return data.stream()
                .map(p -> {
                    var maps = new HashMap<String, Object>();
                    p.getElements().forEach(te -> maps.put(te.getAlias(), p.get(te.getAlias())));
                    return JsonUtil.getInstance().convertValue(maps, new TypeReference<DashboardStatusAndResourceVo>() {
                    });
                }).toList();
    }

    public List<DashboardCountInterviewerVo> countNumberInterviewsByInterviewer(String startTime, String endTime) {
        var sql = """
                SELECT name, if(code is null, '', code) as code, count(1) as total
                FROM (SELECT e.name, e.code, ci.id as ci_id, cs.id as cs_id
                      FROM user e
                               INNER JOIN candidates_interviewer ci ON e.id = ci.interviewer
                               INNER JOIN candidates_status cs ON ci.candidates_status_id = cs.id
                      WHERE cs.date_of_interview > :startTime
                        AND cs.date_of_interview <= :endTime) AS A
                GROUP BY A.code, A.name;
                """;
        Query query = entityManager.createNativeQuery(sql, Tuple.class)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime);
        List<Tuple> data = query.getResultList();
        return data.stream()
                .map(p -> {
                    var maps = new HashMap<String, Object>();
                    p.getElements().forEach(te -> maps.put(te.getAlias(), p.get(te.getAlias())));
                    return JsonUtil.getInstance().convertValue(maps, new TypeReference<DashboardCountInterviewerVo>() {
                    });
                }).toList();
    }
}
