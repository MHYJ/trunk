package com.mhyj.user.service.impl;

import com.mhyj.entity.UserAdmin;
import com.mhyj.user.vo.UserAdminVo;
import com.mhyj.user.mapper.UserAdminMapper;
import com.mhyj.user.service.IUserAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 管理员用户 服务实现类
 * </p>
 *
 * @author mhyj
 * @since 2019-07-01
 */
@Service
public class UserAdminServiceImpl extends ServiceImpl<UserAdminMapper, UserAdmin> implements IUserAdminService {

    @Resource
    private UserAdminMapper userAdminMapper;

    @Override
    public UserAdminVo selectById(Integer userId) {
        UserAdmin userAdmin = userAdminMapper.selectById(userId);
        return userAdminMapper.selectUserById(userId);
    }
}
