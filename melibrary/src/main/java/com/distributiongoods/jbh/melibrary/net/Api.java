package com.distributiongoods.jbh.melibrary.net;

/**
 * Created by zbsdata on 2017/9/11.
 */

public class Api {

    /**正式地址*/
    public static final String HOST="http://zz.inte.zbs6.com/";
    /**测试地址*/
//    public static final String HOST="http://192.168.1.5:9031";

    public interface method{
        /**国际报价*/
        String GetDiamondReport="Data.svc/GetDiamondReport";
        /**发送验证短信*/
        String SendSms="Common.svc/SendSms";
        /**验证验证码*/
        String CheckSms="Common.svc/CheckSms";
        /**提交注册*/
        String AddUser="Account.svc/AddUser";
        /**检测登陆账号*/
        String ChenckUserName="Account.svc/ChenckUserName";
        /**找回密码*/
        String GetUserPassword="Account.svc/GetUserPassword";
        /**获取令牌*/
        String GetToken="Common.svc/GetToken";
        /**用户登录*/
        String UserLogin="Account.svc/UserLogin";
        /**裸石数据*/
        String GetDiamondData="Data.svc/GetDiamondData";
        /**添加购物车*/
        String AddCart="Order.svc/AddCart";
        /**获取购物车列表*/
        String GetListCart="Order.svc/GetListCart";
        /**编辑购物车*/
        String EditCart="Order.svc/EditCart";
        /**修改用户资料*/
        String EditUser="Account.svc/EditUser";
        /**获取收货地址列表*/
        String GetListAddress="Account.svc/GetListAddress";
        /**新增收货地址*/
        String AddAddress="Account.svc/AddAddress";
        /**编辑收货地址*/
        String EditAddress="Account.svc/EditAddress";
        /**添加订单*/
        String  AddOrder="Order.svc/AddOrder";
        /**获取订单列表*/
        String GetListOrder="Order.svc/GetListOrder";
        /**订单编辑*/
        String EditOrder="Order.svc/EditOrder";
        /**订单详情*/
        String GetOrderGoods="Order.svc/GetOrderGoods";
        /**搜索条件*/
        String GetDiamondQuery="Data.svc/GetDiamondQuery";
        /**获取资讯列表*/
        String GetListNews="Data.svc/GetListNews";
        /**获取资讯详细*/
        String GetNews="Data.svc/GetNews";
        /**帮助中心*/
        String GetListHelp="Data.svc/GetListHelp";
        /**帮助中心详细*/
        String GetHelp="Data.svc/GetHelp";
        /**获取购物车数量*/
        String  GetCartQuantity="Order.svc/GetCartQuantity";
        /**获取订单数量*/
        String GetOrderQuantity="Order.svc/GetOrderQuantity";
        /**意见反馈*/
        String SaveOpinion="Common.svc/SaveOpinion";
        /**版本检测*/
        String CheckVersion="Common.svc/CheckVersion";
        /**证书查询*/
        String Certificate="Common.svc/Certificate";
    }
}
