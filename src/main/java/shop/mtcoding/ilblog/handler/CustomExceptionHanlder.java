package shop.mtcoding.ilblog.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import shop.mtcoding.ilblog.handler.ex.CustomException;
import shop.mtcoding.ilblog.util.Script;

@RestController
public class CustomExceptionHanlder {
    

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customException(CustomException e) {
        String responseBody = Script.back(e.getMessage());
        return new ResponseEntity<>(responseBody, e.getStatus());
    }
}
