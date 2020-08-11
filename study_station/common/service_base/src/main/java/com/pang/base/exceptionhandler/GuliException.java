package com.pang.base.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //有参构造方法
@NoArgsConstructor //无参构造方法
public class GuliException extends RuntimeException {

    /** 状态码*/
    private Integer code;

    /** 错误信息*/
    private String msg;
}
