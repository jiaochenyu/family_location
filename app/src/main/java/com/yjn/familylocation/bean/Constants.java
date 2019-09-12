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

    //--------------sp key----------------//
    //sp name
    public static final String SP_NAME = "family_location.xml";
    //目标设备INSTALLATIONID
    public static final String TARGET_INSTALLATIONID_SP = "target_installationid_sp";
    //本设备INSTALLATIONID
    public static final String INSTALLATIONID_SP = "my_installationid_sp";
}
