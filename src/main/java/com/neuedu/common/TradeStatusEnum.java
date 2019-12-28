package com.neuedu.common;

public enum TradeStatusEnum {

    WAIT_BUYER_PAY("WAIT_BUYER_PAY", 10),	//交易创建，等待买家付款 ; ;
    TRADE_CLOSED("TRADE_CLOSED", 60),	//未付款交易超时关闭，或支付完成后全额退款
    TRADE_SUCCESS("TRADE_SUCCESS", 20),	//交易支付成功
    TRADE_FINISHED("TRADE_FINISHED", 50),	//交易结束，不可退款
    ;
    private String trade;
    private int status;

    TradeStatusEnum(String trade, int status) {
        this.trade = trade;
        this.status = status;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    //遍历枚举,传入支付交易返回的trade
    public static int statusOf(String trade){

        for(TradeStatusEnum statusEnum:values()){
            if(trade.equals(statusEnum.getTrade())){
                return statusEnum.getStatus();
            }
        }
        return 0;
    }
}
