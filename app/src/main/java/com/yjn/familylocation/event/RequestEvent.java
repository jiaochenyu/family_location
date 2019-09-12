package com.yjn.familylocation.event;

/**
 * <pre>
 *     author: Bruce_Yang
 *     blog  : https://yangjianan.gitee.io
 *     time  : 2019/9/12
 *     desc  : 请求Event
 * </pre>
 */
public class RequestEvent {
    private String targetInstallationId;//目标方id

    public RequestEvent(String targetInstallationId) {
        this.targetInstallationId = targetInstallationId;
    }

    public String getTargetInstallationId() {
        return targetInstallationId == null ? "" : targetInstallationId;
    }

    public void setTargetInstallationId(String targetInstallationId) {
        this.targetInstallationId = targetInstallationId;
    }
}
