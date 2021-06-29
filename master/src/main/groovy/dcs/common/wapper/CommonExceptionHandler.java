package dcs.common.wapper;


import com.funtester.base.bean.Result;
import com.funtester.base.exception.FailException;
import com.funtester.config.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CommonExceptionHandler {

    public static Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result defaultErrorHandler(HttpServletRequest req, Exception e) {
        /**参数错误*/
        if (e instanceof MethodArgumentNotValidException) {
            String defaultMessage = ((MethodArgumentNotValidException) e).getBindingResult().getFieldError().getDefaultMessage();
            logger.error("参数异常", e);
            return Result.fail(defaultMessage);
        }
        if (e instanceof FailException) {
            logger.error("捕获自定义异常", e);
            return Result.fail(e.getMessage());
        }
        logger.warn("未记录异常类", e);
        return Result.fail(Constant.TEST_ERROR_CODE);
    }


}
