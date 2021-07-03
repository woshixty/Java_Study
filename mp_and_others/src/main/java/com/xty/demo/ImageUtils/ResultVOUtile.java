package com.xty.demo.ImageUtils;

/**
 * @author qyyzxty@icloud.com
 * 2020/10/9
 **/
public class ResultVOUtile {
    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }
}
