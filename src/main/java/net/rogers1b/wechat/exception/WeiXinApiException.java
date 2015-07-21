package net.rogers1b.wechat.exception;

/**
 * Created by Rogers on 14-6-11.
 */


/**
 * API call exception
 */
public class WeiXinApiException extends Exception{
    private int errorCode;
    private String errorMessage;
    
    public WeiXinApiException() {
        super();
    }

    public WeiXinApiException(String message, int code) {
        super(message);
        this.errorCode = code;
        this.errorMessage = message;
    }

    @Override
    public String getMessage(){
       return String.format("[errcode: %d, errmsg: %s]", this.errorCode, this.errorMessage);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    
    
}
