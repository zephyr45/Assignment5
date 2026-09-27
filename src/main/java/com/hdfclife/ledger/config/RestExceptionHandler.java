package com.hdfclife.ledger.config;
import com.hdfclife.ledger.dto.ErrorResponse; import com.hdfclife.ledger.exception.*; import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import java.util.*; import java.util.function.Function; import java.util.stream.Collectors;
@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ErrorResponse> validation(MethodArgumentNotValidException ex) { Map<String,String> fields = ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(e -> e.getField(), e -> Optional.ofNullable(e.getDefaultMessage()).orElse("Invalid value"), (a,b) -> a, LinkedHashMap::new)); return response(HttpStatus.BAD_REQUEST, "Validation failed", fields); }
    @ExceptionHandler({PolicyNotFoundException.class, ClaimNotFoundException.class}) ResponseEntity<ErrorResponse> notFound(DeskException ex) { return response(HttpStatus.NOT_FOUND, ex.getMessage(), Map.of()); }
    @ExceptionHandler(DuplicatePolicyException.class) ResponseEntity<ErrorResponse> duplicate(DuplicatePolicyException ex) { return response(HttpStatus.CONFLICT, ex.getMessage(), Map.of()); }
    @ExceptionHandler(InvalidRequestException.class) ResponseEntity<ErrorResponse> invalid(InvalidRequestException ex) { return response(HttpStatus.BAD_REQUEST, ex.getMessage(), Map.of()); }
    private ResponseEntity<ErrorResponse> response(HttpStatus status, String message, Map<String,String> fields) { return ResponseEntity.status(status).body(new ErrorResponse(status.value(), status.getReasonPhrase(), message, fields)); }
}
