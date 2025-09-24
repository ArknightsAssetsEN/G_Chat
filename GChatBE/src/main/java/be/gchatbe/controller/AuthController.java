package be.gchatbe.controller;

import be.gchatbe.dto.ApiResponseDto;
import be.gchatbe.dto.FieldDTO;
import be.gchatbe.dto.LoginDTO;
import be.gchatbe.service.JwtService;
import be.gchatbe.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;

    @PostMapping("/login")
    public Mono<ResponseEntity<ApiResponseDto<Object>>> login(@RequestBody LoginDTO loginDTO) {
        return userService.findByUsername(loginDTO.getUsername())
                .flatMap(account -> {
                    if (!account.getPassword().equals(loginDTO.getPassword()))
                        return Mono.just(ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .body(ApiResponseDto.builder()
                                        .status("FAIL")
                                        .message("Invalid username or password")
                                        .build()));
                    String token = jwtService.generateAccessToken(account);
                    FieldDTO oneFieldDTO = new FieldDTO(token);
                    return Mono.just(ResponseEntity
                            .ok(ApiResponseDto.builder()
                                    .status("SUCCESS")
                                    .message("Login successful")
                                    .response(oneFieldDTO)
                                    .build()));
                })
                .switchIfEmpty(Mono.just(ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.builder()
                                .status("FAIL")
                                .message("User not found")
                                .build())))
                .onErrorReturn(ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.builder()
                        .status("FAIL")
                        .message("Login failed")
                        .build()));
    }
}
