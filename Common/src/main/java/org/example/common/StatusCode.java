package org.example.common;

public enum StatusCode {

    /**
     * 系統 0
     */
    Success(0, "成功"),

    SystemError(-1, "失敗"),
    FilePathNotFund(1, "文件不存在或路径无效"),

    ValidationError(2,"绑定参数验证失败"),


    /**
     * 推流 100
     */
    CreatePushMp4Error(100, "创建推流MP4失败"),

    /**
     * 檔案 400
     */

    NeedFile(400,"需要檔案"),
    NonSupportExt(401,"不支援的檔案類型"),

    ;

    public final int code;

    public final String msg;


    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static StatusCode getByCode(int code) {
        for (StatusCode e : StatusCode.values()) {
            if (e.code == code) {
                return e;
            }
        }

        return null;
    }


}
