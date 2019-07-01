package com.mhyj.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * response json
 * @author MHYJ
 * @date 2019/6/28 17:26
 **/
@Data
@Builder
public class Result<T> {
    @ApiModelProperty(value = "response code")
    @Builder.Default
    private String code = "0";
    @ApiModelProperty(value = "tips")
    @Builder.Default
    private String msg = "success";
    @ApiModelProperty(value = "business data")
    private T data;

    private Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result ok() {
        return Result.builder().build();
    }

    public static <T> Result<T> ok(T data) {
        ResultBuilder<T> rs = Result.builder();
        return rs.data(data).build();
    }

    public static Result error(String code, String msg) {
        return Result.builder().code(code).msg(msg).build();
    }

    public static <T> Result<T> error(String code, String msg, T data) {
        ResultBuilder<T> rs = Result.builder();
        return rs.code(code).msg(msg).data(data).build();
    }

}
