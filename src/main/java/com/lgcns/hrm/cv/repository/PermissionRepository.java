package com.lgcns.hrm.cv.repository;

import com.lgcns.hrm.cv.entity.Permission;
import com.lgcns.hrm.cv.repository.base.BaseRepository;

import java.util.Optional;

public interface PermissionRepository extends BaseRepository<Permission, Integer> {
    Optional<Permission> findByComponentNameAndAction(String name, String action);

    Optional<Permission> findById(Integer id);
}
