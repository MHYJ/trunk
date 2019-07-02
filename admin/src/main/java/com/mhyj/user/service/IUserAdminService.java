package com.mhyj.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mhyj.entity.UserAdmin;
import com.mhyj.user.controller.vo.UserAdminVo;

/**
 * <p>
 * 管理员用户 服务类
 * </p>
 *
 * @author mhyj
 * @since 2019-07-01
 */
public interface IUserAdminService extends IService<UserAdmin> {

    /**
     *
     * @param userId
     * @return
     */
    UserAdminVo selectById(Integer userId);
}
