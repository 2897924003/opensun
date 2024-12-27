package article.usecase.exception;

public class ApiCONotPermittedException extends BusinessException {
    public ApiCONotPermittedException(String msg) {
        super(msg);
    }
}
