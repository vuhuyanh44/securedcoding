package com.lgcns.hrm.cv.repository;

import com.lgcns.hrm.cv.entity.Token;
import com.lgcns.hrm.cv.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author pigx
 */
public interface TokenRepository extends BaseRepository<Token, String> {
    @Query("""
            select t from Token t inner join User u on t.user.id = u.id
            where u.id = :userId and t.revoked = false and t.expiredDate > CURRENT_TIMESTAMP
            """)
    List<Token> findAllValidTokensByUser(Integer userId);

    Optional<Token> findByToken(String token);
}
