package com.cyantree.check.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class FileExtension {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FixFileVM {
        @Schema(description = "파일 확장자명")
        private String extensionName;
        
        @Schema(description = "차단여부")
        private boolean isBlocked;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomFileVM {
        @Schema(description = "파일 확장자명")
        private String extensionName;
        
        @Schema(description = "차단여부")
        private boolean isBlocked;
    }
}
