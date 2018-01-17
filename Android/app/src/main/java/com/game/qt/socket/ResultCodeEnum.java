package com.game.qt.socket;

/**
 * 返回状态枚举
 */
public enum ResultCodeEnum implements IResultCode {
    SYS_SUCCESS(200, "成功"),
    SYS_ERROR(100000, "系统错误"),
    SYS_PARAMS_ERROR(100002, "参数错误"),
    SIGN_ERROR(100003, "验证签名失败"),
    ROOM_FULL(100004, "房间已满"),
    VERIFY_CODE_ERROR(100005, "验证码错误"),
    LOGIN_USERNAME_OR_PWD_ERROR(100006, "帐号或密码有误"),
    LOGIN_USER_DISABLED(100007, "该帐号已被禁用了哦"),
    PASSWORD_IS_NOT_VALID(100008, "密码8到32位之间"),
    ROLE_IS_NOT_VALID(100009, "无效的角色"),
    USERNAME_IS_NOT_EXIST(100010, "用户名已存在"),
    USER_IS_NOT_EXIST(100011, "该用户不存在"),
    CAN_NOT_ALLOCATE(100012, "不能为该用户分配卡"),
    NOT_ENOUGH_CARD_NUM(100013, "没有足够的卡"),
    ADMIN_NOT_TO_CANCEL(100014, "管理员无须被取消"),
    MARK_REPEAT_ERROR(100015, "标识已经被使用"),
    USER_NOT_ENOUGH(100016, "人数不足"),
    NOT_ALL_READY(100017, "没有全部准备"),
    NO_ONE_STRIVE_BANKER(100018, "没有人抢庄");

    int resultCode;

    String resultMsg;

    ResultCodeEnum(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public int getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }

}