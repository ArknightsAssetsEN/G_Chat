package be.gchatbe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class ApiResponseDto<T> {
    private int code;           // HTTP status code (200, 400, 401, 404, 500, ...)
    private String status;      // "success" | "error" | "fail"
    private String message;     // Thông báo ngắn gọn
    private T data;             // Dữ liệu trả về (có thể null)
    private Instant timestamp;  // Thời điểm response
    private String path;        // Endpoint được gọi
}
