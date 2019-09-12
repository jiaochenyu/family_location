package com.yjn.familylocation.bean;

/**
 * <pre>
 *     author: Bruce_Yang
 *     blog  : https://yangjianan.gitee.io
 *     time  : 2019/9/12
 *     desc  : 说明
 * </pre>
 */
public class MsgBean {
    //请求定位
    public static final int REQUEST_LOCATION = 0;
    //定位信息返回
    public static final int RETURN_LOCATION = 1;
    //普通消息
    public static final int MSG = 1;

    private int type;
    private String targetInstallationId;//目标方id
    private String requestInstallationId;//发起方id
    //内容可为空（定位返回，普通消息需要传递）
    private String content;

    public MsgBean(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTargetInstallationId() {
        return targetInstallationId == null ? "" : targetInstallationId;
    }

    public void setTargetInstallationId(String targetInstallationId) {
        this.targetInstallationId = targetInstallationId;
    }

    public String getRequestInstallationId() {
        return requestInstallationId == null ? "" : requestInstallationId;
    }

    public void setRequestInstallationId(String requestInstallationId) {
        this.requestInstallationId = requestInstallationId;
    }
}
