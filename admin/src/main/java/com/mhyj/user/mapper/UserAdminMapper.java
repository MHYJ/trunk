package com.mhyj.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mhyj.entity.UserAdmin;
import com.mhyj.user.vo.UserAdminVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 管理员用户 Mapper 接口
 * </p>
 *
 * @author mhyj
 * @since 2019-07-01
 */
public interface UserAdminMapper extends BaseMapper<UserAdmin> {

    /**
     *
     * @param userId
     * @return
     */
    UserAdminVo selectUserById(@Param("userId") Integer userId);
}
