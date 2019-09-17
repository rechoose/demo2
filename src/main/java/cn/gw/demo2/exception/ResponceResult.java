package cn.gw.demo2.exception;

import lombok.Data;

/**
 * 统一异常返回对象
 * @param <T>
 */
@Data
public class ResponceResult<T> {

    private String errorMsg;
    private boolean success;
    private Integer code;
    private T data;

    public static ResponceResult success(Object data) {
        return new ResponceResult<>(null, true, 1, data);
    }

    public static ResponceResult fale(String errorMsg) {
        return new ResponceResult<>(errorMsg, false, 0, null);
    }

    private ResponceResult(String errorMsg, boolean success, Integer code, T data) {
        this.errorMsg = errorMsg;
        this.success = success;
        this.code = code;
        this.data = data;
    }
}
