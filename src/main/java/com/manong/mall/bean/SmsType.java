package com.manong.mall.bean;

/**
 * Created by qjy on 2017/1/13.
 */
public enum SmsType {
    TYPE_Login("Login", "登录短信"),
    TYPE_Reg("Reg", "注册"),
    TYPE_CreditSuccess("CreditSuccess","授信成功"),
    TYPE_CreditFailed("CreditFailed", "授信失败"),
    TYPE_ResetPaypass("ResetPaypass", "充值支付密码"),
    TYPE_InstalSuccess("InstalSuccess", "分期成功"),
    TYPE_PayNotice_WILL("RepayNotice1", "待还款提醒"),
    TYPE_PaySuccess_ALL("RepaySuccess", "结清分期"),
    TYPE_PaySuccess_ONE("RepaySuccess2", "还清某一个分期"),
    TYPE_PayNotice_TODAY("RepayNotice2", "当日还款提醒"),
    TYPE_PayNotice_OVERDUE("RepayNotice3", "逾期还款提醒"),
    TYPE_MerLogin("MerLogin","商户登录验证码"),
    TYPE_MerResetPass("MerResetPwd", "商户重置密码"),
    TYPE_FaceManualAudit("FaceManualAudit", "人脸人工审核"),
    TYPE_MobileManualAudit("MobileManualAudit", "运营商人工审核"),
    TYPE_RefundConfirm("RefundConfirm","店长退货确认");

    private String code;
    private String name;
    SmsType(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public String getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
}
