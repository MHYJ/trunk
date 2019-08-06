package com.mhyj.user.controller.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
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
//    @JSONField(format = "yyyy-MM-dd")
    private LocalDateTime createTime;
}
