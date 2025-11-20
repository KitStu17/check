package com.cyantree.check.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomResponse<T> {
    @Schema(description = "응답 결과(성공 : true, 실패 : false)", example = "true")
    private boolean result;
    @Schema(description = "에러 메세지", example = "에러 메세지 입니다.")
    private String error;
    @Schema(description = "데이터", example = "데이터", nullable = true)
    private T data;

    public CustomResponse(boolean result, T data) {
        this(result, null, data);
    }

    public static <T> CustomResponse<T> success(T data) {
        return new CustomResponse<>(true, data);
    }
}
