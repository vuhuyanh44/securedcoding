package com.lgcns.hrm.cv.transform;

import com.lgcns.hrm.cv.common.utils.BeanUtil;
import com.lgcns.hrm.cv.entity.User;
import com.lgcns.hrm.cv.model.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserTransform implements BaseTransform<User, UserVo> {
    @Override
    public User toEntity(UserVo model) {
        return BeanUtil.copy(model, User.class);
    }

    @Override
    public UserVo toModel(User entity) {
        return BeanUtil.copy(entity, UserVo.class);
    }
}
