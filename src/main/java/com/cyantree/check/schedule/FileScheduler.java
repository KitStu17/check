package com.cyantree.check.schedule;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cyantree.check.api.service.FileApiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "file_scheduler")
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "cyantree.schedule.use", havingValue = "true")
public class FileScheduler {
    private final FileApiService fileApiService;

    @Scheduled(cron = "${cyantree.remove.extension.cron:- }")
    public void removeExtension() {
        log.info("커스텀 확장자 제거 스케줄러 실행");
        fileApiService.removeCustomExtension();
    }
}
