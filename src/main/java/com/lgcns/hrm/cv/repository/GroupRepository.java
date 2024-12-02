package com.lgcns.hrm.cv.repository;

import com.lgcns.hrm.cv.entity.Group;
import com.lgcns.hrm.cv.repository.base.BaseRepository;

import java.util.Optional;

public interface GroupRepository extends BaseRepository<Group, Integer> {
    Group findByName(String name);
    Optional<Group> findById(Integer id);
}
