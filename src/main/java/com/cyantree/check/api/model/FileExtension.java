package com.cyantree.check.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class FileExtension {

    @Getter
    @Setter
    @Data
    public static class UpdateFixFileVM {
        @Schema(description = "일련 번호")
        private long seqno;

        @Schema(description = "파일 확장자명")
        private String extensionName;

        @Schema(description = "차단여부")
        private Boolean isBlocked;
    }

    @Getter
    @Setter
    @Data
    public static class UpsertCustomFileVM {
        @Schema(description = "파일 확장자명")
        @Size(max = 20)
        private String extensionName;

        @Schema(description = "차단여부")
        private Boolean isBlocked;
    }

    @Getter
    @Setter
    @Data
    public static class RemoveCustomFileVM {
        @Schema(description = "일련 번호")
        private long seqno;

        @Schema(description = "파일 확장자명")
        @Size(max = 20)
        private String extensionName;
    }
}
