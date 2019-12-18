package com.neuedu.common;
//假设其含有public构造器，那么在类的外部就能够通过这个构造器来新建实例，
//显然这时实例的数量和值就不固定了，这与定义枚举类的初衷相矛盾，
//为了避免这样的形象。就对枚举类的构造器默认使用private修饰。
//假设为枚举类的构造器显式指定其他訪问控制符，则会编译出错。
public enum ResponseCode {
    USERNAME_NOT_EMPTY(1,"用户名不能为空"),
    PASSWORD_NOT_EMPTY(2,"密码不能为空"),
    USERNAME_NOT_EXIST(3,"用户名不存在"),
    PASSWORD_ERROR(4,"密码错误"),
    PARAMETER_NOT_NULL(5,"参数不能为空"),
    EMAIL_NOT_NULL(6,"邮箱不能为空"),
    PHONE_NOT_NULL(7,"联系方式不能为空"),
    QUESTION_NOT_NULL(8,"密保问题不能为空"),
    ANSWER_NOT_NULL(9,"答案不能为空"),
    USERNAME_EXIST(10,"用户名存在"),
    EMAIL_EXIST(11,"邮箱存在"),
    REGISTER_ERROR(12,"注册失败"),
    ;
    private int code;
    private String msg;
    ResponseCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
