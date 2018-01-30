package com.game.qt.socket;

/**
 * 返回结果类
 *
 * @author Administrator
 */
public class Result<T> {

        private String message = ResultCodeEnum.SYS_ERROR.getResultMsg();   //返回消息提示
        private int code = ResultCodeEnum.SYS_ERROR.getResultCode();  //返回消息编码
        private boolean success = false;
        private T model;     //具体数据对象
        /**
         * 如果是列表,返回总条数
         */
        private int totalRecord;

        /**
         * 创建一个result
         */
        public Result() {
        }
        /**
         * 创建一个result。
         *
         * @param success 是否成功
         */
        public Result(boolean success) {
                this.success = success;
        }

        public Result(boolean success, int code, String message){
                this.success = success;
                this.code = code;
                this.message = message;
        }

        public Result(T model){
                this.model = model;
        }

        public String getMessage() {
                return message;
        }

        public void setMessage(String message) {
                this.message = message;
        }

        public int getCode() {
                return code;
        }

        public void setCode(int code) {
                this.code = code;
        }

        public T getModel() {
                return model;
        }

        public void setModel(T model) {
                this.model = model;
        }

        public boolean isSuccess() {
                return success;
        }

        public void setSuccess(boolean success) {
                this.success = success;
        }

        public int getTotalRecord() {
                return totalRecord;
        }

        public void setTotalRecord(int totalRecord) {
                this.totalRecord = totalRecord;
        }

        public void setSuccess(boolean success,int resultCode,String resultMsg){
                this.success = success;
                this.code = resultCode;
                this.message = resultMsg;
        }

        /**
         * 错误返回
         * @param result
         * @param itemResultEnum
         * @return
         */
        public static Result errorResult(Result result, ResultCodeEnum itemResultEnum) {
                result.setSuccess(false);
                result.setCode(itemResultEnum.getResultCode());
                result.setMessage(itemResultEnum.getResultMsg());
                return result;
        }

        /**
         * 成功返回
         * @param result
         * @param data
         * @return
         */
        public static Result successResult(Result result, Object data) {
                result.setSuccess(true);
                result.setCode(ResultCodeEnum.SYS_SUCCESS.getResultCode());
                result.setMessage(ResultCodeEnum.SYS_SUCCESS.getResultMsg());
                result.setModel(data);
                return result;
        }

        /**
         * 成功返回
         * @param result
         * @param data
         * @param totalCount
         * @return
         */
        public static Result successResult(Result result, Object data, int totalCount) {

                result.setSuccess(true);
                result.setCode(ResultCodeEnum.SYS_SUCCESS.getResultCode());
                result.setMessage(ResultCodeEnum.SYS_SUCCESS.getResultMsg());
                result.setModel(data);
                result.setTotalRecord(totalCount);
                return result;
        }

        public Result<T> defaultSuccess(){
                this.success = true;
                this.code = ResultCodeEnum.SYS_SUCCESS.getResultCode();
                this.message = ResultCodeEnum.SYS_SUCCESS.getResultMsg();
                return this;
        }


}
