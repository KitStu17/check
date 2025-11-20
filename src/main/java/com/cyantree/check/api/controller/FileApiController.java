package com.cyantree.check.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyantree.check.api.model.CustomResponse;
import com.cyantree.check.api.model.FileExtension.UpdateFixFileVM;
import com.cyantree.check.api.model.FileExtension.UpsertCustomFileVM;
import com.cyantree.check.api.service.FileApiService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "FileController", description = "파일 확장자 상태 변경 API")
public class FileApiController {

    private final FileApiService fileApiService;

    @Operation(description = "고정 확장자 상태 변경 API")
    @PostMapping("/update/fix")
    public ResponseEntity<CustomResponse<Boolean>> updateFixedExtension(HttpServletRequest request,
            HttpServletResponse response, @RequestBody UpdateFixFileVM reqDto) {
        fileApiService.updateFixedExtension(reqDto);

        CustomResponse<Boolean> res = CustomResponse.success(true);
        return ResponseEntity.ok(res);
    }

    @Operation(description = "커스텀 확장자 차단 목록 추가 API")
    @PostMapping("/insert/custom")
    public ResponseEntity<CustomResponse<Boolean>> insertCustomExtension(HttpServletRequest request,
            HttpServletResponse response, @Valid @RequestBody UpsertCustomFileVM reqDto) {
        fileApiService.insertCustomExtension(reqDto);

        CustomResponse<Boolean> res = CustomResponse.success(true);
        return ResponseEntity.ok(res);
    }

    @Operation(description = "커스텀 확장자 차단 해제 API")
    @PostMapping("/del/custom")
    public ResponseEntity<CustomResponse<Boolean>> deleteCustomExtension(HttpServletRequest request,
            HttpServletResponse response, @Valid @RequestBody UpsertCustomFileVM reqDto) {
        fileApiService.deleteCustomExtension(reqDto);

        CustomResponse<Boolean> res = CustomResponse.success(true);
        return ResponseEntity.ok(res);
    }

}
