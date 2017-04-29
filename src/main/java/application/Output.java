package application;

/**
 * Created by jiezhang on 29/4/17.
 */
public class Output {
    private boolean success;
    private String message;
    private String data;

    public Output() {
        this.success = false;
        this.message = "";
        this.data = "";
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("{\"success\":%b, \"message\":\"%s\", \"data\":%s}",
                success, message, data);
    }
}
