package com.yjn.familylocation.bean;

/**
 * <pre>
 *     author: Bruce_Yang
 *     blog  : https://yangjianan.gitee.io
 *     time  : 2019/9/12
 *     desc  : 应用更新实体类
 * </pre>
 */
public class UpdateBean {

    /**
     * outputType : {"type":"APK"}
     * apkData : {"type":"MAIN","splits":[],"versionCode":2,"versionName":"2.0","enabled":true,"outputFile":"familykeep_v2.0_2019-09-12.apk","fullName":"release","baseName":"release"}
     * path : familykeep_v2.0_2019-09-12.apk
     * properties : {}
     */

    private OutputTypeBean outputType;
    private ApkDataBean apkData;
    private String path;
    private PropertiesBean properties;

    public OutputTypeBean getOutputType() {
        return outputType;
    }

    public void setOutputType(OutputTypeBean outputType) {
        this.outputType = outputType;
    }

    public ApkDataBean getApkData() {
        return apkData;
    }

    public void setApkData(ApkDataBean apkData) {
        this.apkData = apkData;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public PropertiesBean getProperties() {
        return properties;
    }

    public void setProperties(PropertiesBean properties) {
        this.properties = properties;
    }

    public static class OutputTypeBean {
        /**
         * type : APK
         */

        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class ApkDataBean {
        /**
         * type : MAIN
         * splits : []
         * versionCode : 2
         * versionName : 2.0
         * enabled : true
         * outputFile : familykeep_v2.0_2019-09-12.apk
         * fullName : release
         * baseName : release
         */

        private String type;
        private int versionCode;
        private String versionName;
        private boolean enabled;
        private String outputFile;
        private String fullName;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getOutputFile() {
            return outputFile;
        }

        public void setOutputFile(String outputFile) {
            this.outputFile = outputFile;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    }

    public static class PropertiesBean {
    }
}
