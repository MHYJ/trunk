package com.mhyj.user.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

/**
 * @author MHYJ
 * @date 2019/7/1 12:05
 **/
@Data
public class UserAdminDto implements Serializable {
    @ApiModelProperty(value = "user id")
    private Integer id;
    @ApiModelProperty(value = "username", required = true)
    private String userName;
    @ApiModelProperty(value = "user password", required = true)
    private String userPass;
}
