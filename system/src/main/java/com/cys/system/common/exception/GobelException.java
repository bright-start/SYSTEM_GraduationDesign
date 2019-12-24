package com.cys.system.common.exception;

import com.cys.system.common.common.pojo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GobelException {
    private static final Logger logger = LoggerFactory.getLogger(GobelException.class);

    /**
     * 400
     */
    @ExceptionHandler(value = {InvalidRequestException.class})
    public ResponseEntity<Result> errorHandler(InvalidRequestException e) {
        logger.debug("InvalidRequestException error code:400" + e);
        return ResponseEntity.status(400).body(new Result().error(400, e.getMessage()));
    }

    /**
     * 401
     */
    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<Result> errorHandler(UnauthorizedException e) {
        logger.debug("UnauthorizedException error code 401" + e);
        return ResponseEntity.status(401).body(new Result().error(401, e.getMessage()));
    }

    /**
     * 403
     */
    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<Result> errorHandler(ForbiddenException e) {
        logger.debug("ForbiddenException error code 403 " + e);
        if (e.getData() != null) {
            return ResponseEntity.status(403).body(new Result().error(403, e.getMessage(), e.getData()));
        }
        return ResponseEntity.status(403).body(new Result().error(403, e.getMessage()));
    }

    /**
     * 404
     */
    @ExceptionHandler(value = {NotFoundException.class,})
    public ResponseEntity<Result> errorHandler(NotFoundException e) {
        logger.debug("NotFoundException code:404 ", e);
        return ResponseEntity.status(404).body(new Result().error(404, e.getMessage()));
    }

    /**
     * 500 - 服务器发生错误
     */
    @ExceptionHandler
    public ResponseEntity<Result> errorHandler(Throwable e) {
        logger.error("{} code:500", e.getClass(), e);
        return ResponseEntity.status(500).body(new Result().error(500, "An error occurred on the server. Please try again later"));
    }

}
