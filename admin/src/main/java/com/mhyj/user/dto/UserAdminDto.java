package com.mhyj.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author MHYJ
 * @date 2019/7/1 12:05
 **/
@Data
public class UserAdminDto implements Serializable {
    @ApiModelProperty(value = "user id")
    private Integer id;
    @ApiModelProperty(value = "username", required = true)
    @NotBlank(message = "username is not allowed blank")
    private String userName;
    @ApiModelProperty(value = "user password", required = true)
    private String userPass;
    @ApiModelProperty(value = "test")
    private LocalDateTime signTime;
}
