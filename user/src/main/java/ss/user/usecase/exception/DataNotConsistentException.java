package ss.user.usecase.exception;

/**
 * 致命，发生此问题必须解决
 */
public class DataNotConsistentException extends BusinessException {
    public DataNotConsistentException(String msg) {
        super(msg);
    }

    public static DataNotConsistentException msg(String msg) {
        return new DataNotConsistentException(msg);
    }
}
