package com.wms.common;

import lombok.Data;

@Data
public class Result {

    private int code; // 状态码

    private String message; // 响应消息

    private Long total; // 总记录数

    private Object data; // 响应数据

    private static Result returnResult(int code, String message, Object data, Long total) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        result.setTotal(total);
        return result;
    }

    public static Result fail(){
        return returnResult(500, "操作失败", null, 0L);
    }

    public static Result success() {
        return returnResult(200, "操作成功", null, 0L);
    }

    public static Result success(Object data) {
        return returnResult(200, "操作成功", data, 0L);
    }
    public static Result success(Object data, Long total) {
        return returnResult(200, "操作成功", data, total);
    }
}
