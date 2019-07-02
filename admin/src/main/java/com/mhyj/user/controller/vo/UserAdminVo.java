package com.mhyj.user.controller.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author MHYJ
 * @date 2019/7/1 14:46
 **/
@Data
public class UserAdminVo implements Serializable{
    @ApiModelProperty(value = "user-name")
    private String userName;
    @ApiModelProperty(value = "create time")
    private Date createTime;
}
