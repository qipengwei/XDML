package hello.entity;

public class Result {
    String status;
    String msg;
    boolean isLogin;
    Object data;

    public static Result failure(String msg) {
        return new Result("fail", false, msg);
    }

    public static Result success(boolean isLogin, String msg) {
        return new Result("ok", isLogin, msg);
    }

    public static Result success(boolean isLogin, String msg, Object filling) {
        return new Result("ok", isLogin, msg, filling);
    }

    private Result(String status, boolean isLogin, String msg) {
        this.status = status;
        this.msg = msg;
        this.isLogin = isLogin;
        this.data = null;
    }

    private Result(String status, boolean isLogin, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.isLogin = isLogin;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean login) {
        isLogin = login;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", isLogin=" + isLogin +
                ", data=" + data +
                '}';
    }
}