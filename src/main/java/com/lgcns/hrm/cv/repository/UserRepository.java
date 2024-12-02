package com.lgcns.hrm.cv.repository;

import com.lgcns.hrm.cv.entity.User;
import com.lgcns.hrm.cv.repository.base.BaseRepository;

import java.util.Optional;

/**
 * @author pigx
 */
public interface UserRepository extends BaseRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
