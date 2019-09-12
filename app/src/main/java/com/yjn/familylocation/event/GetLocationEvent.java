package com.yjn.familylocation.event;

/**
 * <pre>
 *     author: Bruce_Yang
 *     blog  : https://yangjianan.gitee.io
 *     time  : 2019/9/12
 *     desc  : 获取定位Event
 * </pre>
 */
public class GetLocationEvent {
    public enum Type {
        定位,
        返回定位,
    }

    private Type type;
    private String requestInstallationId;//发起方id
    private String lanlon;//经纬度,","分割

    public GetLocationEvent(Type type, String requestInstallationId) {
        this.type = type;
        this.requestInstallationId = requestInstallationId;
    }

    public GetLocationEvent(Type type, String requestInstallationId, String lanlon) {
        this.type = type;
        this.requestInstallationId = requestInstallationId;
        this.lanlon = lanlon;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getRequestInstallationId() {
        return requestInstallationId == null ? "" : requestInstallationId;
    }

    public void setRequestInstallationId(String requestInstallationId) {
        this.requestInstallationId = requestInstallationId;
    }

    public String getLanlon() {
        return lanlon == null ? "" : lanlon;
    }

    public void setLanlon(String lanlon) {
        this.lanlon = lanlon;
    }
}
