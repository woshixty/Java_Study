package com.xty.demo.ImageUtils;

import lombok.Data;

/**
 * @author qyyzxty@icloud.com
 * 2020/10/9
 **/

@Data
public class ResultVO<T> {

    //错误码
    private Integer code;

    //提示信息
    private String msg;

    //具体内容
    private T data;

}
