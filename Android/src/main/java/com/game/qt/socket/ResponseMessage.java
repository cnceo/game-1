package com.game.qt.socket;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by heshangqiu on 2017/3/22 13:49
 */
public class ResponseMessage<T> implements Serializable {
    private static final long serialVersionUID = 6521038957594015424L;
    //命令
    private int command;
    private T data;//返回数据
    private String message = ResultCodeEnum.SYS_ERROR.getResultMsg();   //返回消息提示
    private int code = ResultCodeEnum.SYS_ERROR.getResultCode();  //返回消息编码
    private boolean success = false;

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * 错误返回
     * @param responseMessage
     * @param result
     * @return
     */
    public static ResponseMessage errorMessage(ResponseMessage responseMessage, Result result, int command) {
        responseMessage.setSuccess(false);
        //responseMessage.setCode(result.getCode());
        //responseMessage.setMessage(result.getMessage());
        responseMessage.setCommand(command);
        return responseMessage;
    }

    /**
     * 错误返回
     * @param responseMessage
     * @param itemResultEnum
     * @return
     */
    public static ResponseMessage errorMessage(ResponseMessage responseMessage, ResultCodeEnum itemResultEnum, int command) {
        responseMessage.setSuccess(false);
        responseMessage.setCode(itemResultEnum.getResultCode());
        responseMessage.setMessage(itemResultEnum.getResultMsg());
        responseMessage.setCommand(command);
        return responseMessage;
    }

    /**
     * 成功返回
     * @param responseMessage
     * @param command
     * @param data
     * @return
     */
    public static ResponseMessage successMessage(ResponseMessage responseMessage, int command, Object data) {
        responseMessage.setSuccess(true);
        responseMessage.setCode(ResultCodeEnum.SYS_SUCCESS.getResultCode());
        responseMessage.setMessage(ResultCodeEnum.SYS_SUCCESS.getResultMsg());
        responseMessage.setData(data);
        responseMessage.setCommand(command);
        return responseMessage;
    }
}
