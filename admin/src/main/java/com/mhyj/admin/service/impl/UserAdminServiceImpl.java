package com.mhyj.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mhyj.model.entity.UserAdmin;
import com.mhyj.admin.mapper.UserAdminMapper;
import com.mhyj.admin.service.IUserAdminService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员用户 服务实现类
 * </p>
 *
 * @author mhyj
 * @since 2019-06-28
 */
@Service
public class UserAdminServiceImpl extends ServiceImpl<UserAdminMapper, UserAdmin> implements IUserAdminService {

}
