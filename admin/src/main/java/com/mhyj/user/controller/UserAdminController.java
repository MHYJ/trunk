package com.mhyj.user.controller;


import com.mhyj.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@Api(tags = "test")
public class UserAdminController {

    @GetMapping("hello")
    @ApiOperation(value = "test", response = String.class, notes = "lgy")
    public Result<String> hello() {
        return Result.ok("hello");
    }

}
