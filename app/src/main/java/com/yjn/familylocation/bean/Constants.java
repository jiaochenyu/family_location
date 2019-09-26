package com.yjn.familylocation.bean;

/**
 * <pre>
 *     author: Bruce_Yang
 *     blog  : https://yangjianan.gitee.io
 *     time  : 2019/9/12
 *     desc  : 常量
 * </pre>
 */
public class Constants {
    public static final String UPDATE_URL_BASE = "https://raw.githubusercontent.com/yangxiaoge/family_location/master/app/build/outputs/apk/release/";
    public static final String UPDATE_URL_CHECK = UPDATE_URL_BASE + "output.json";
    public static final String INSTALLATIONID_BARCODE = "http://apis.juhe.cn/qrcode/api?key=b7feb5a7342448f4d9cba2bd844f8991&type=2&fgcolor=6f7972&w=300&m=5&text=";

    //--------------sp key----------------//
    //sp name
    public static final String SP_NAME = "family_location.xml";
    //目标设备INSTALLATIONID
    public static final String TARGET_INSTALLATIONID_SP = "target_installationid_sp";
    //本设备INSTALLATIONID
    public static final String INSTALLATIONID_SP = "my_installationid_sp";
}
