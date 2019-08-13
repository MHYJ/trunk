package com.mhyj.user.controller;


import com.mhyj.entity.UserAdmin;
import com.mhyj.user.dto.UserAdminDto;
import com.mhyj.user.vo.UserAdminVo;
import com.mhyj.user.service.IUserAdminService;
import com.mhyj.core.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 管理员用户 前端控制器
 * </p>
 *
 * @author mhyj
 * @since 2019-07-01
 */
@RestController
@RequestMapping("/user/user-admin")
@Api(tags = "admin")
@Validated
public class UserAdminController {

    @Resource
    private IUserAdminService userAdminService;

    @PostMapping("save")
    @ApiOperation(value = "user-save", response = Integer.class, notes = "mhyj")
    public Result save(@Valid UserAdminDto userAdminDto) {
        UserAdmin userAdmin = new UserAdmin();
        BeanUtils.copyProperties(userAdminDto, userAdmin);
        userAdminService.save(userAdmin);
        return Result.ok(userAdmin.getId());
    }

    @GetMapping("get")
    @ApiOperation(value = "user-get", response = UserAdminVo.class, notes = "mhyj")
    public Result get(@ApiParam(value = "user-id", required = true)@NotNull Integer userId) {
        UserAdminVo userAdmin = userAdminService.selectById(userId);
        return Result.ok(userAdmin);
    }

    @GetMapping("test")
    @ApiOperation(value = "user-test", response = Integer.class, notes = "mhyj")
    public Result test() {
        return Result.ok("yes");
    }
}
