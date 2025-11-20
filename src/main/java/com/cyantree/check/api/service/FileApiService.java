package com.cyantree.check.api.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cyantree.check.api.model.FileExtension.RemoveCustomFileVM;
import com.cyantree.check.api.model.FileExtension.UpdateFixFileVM;
import com.cyantree.check.api.model.FileExtension.UpsertCustomFileVM;
import com.cyantree.check.api.repository.FileApiRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileApiService {

    private final FileApiRepository fileApiRepository;
    private static final Logger schedulerLogger = LoggerFactory.getLogger("file_scheduler");

    /**
     * 고정 확장자 상태 변경
     * 
     * @param reqDto
     */
    public void updateFixedExtension(UpdateFixFileVM reqDto) {
        // 고정 확장자의 경우, 확장자명 처리 과정 생략
        fileApiRepository.updateFixedExtension(reqDto);
    }

    /**
     * 커스텀 확장자 차단 목록 등록
     * 
     * @param reqDto
     */
    public void insertCustomExtension(UpsertCustomFileVM reqDto) {
        // 확장자명 공백 제거 및 대소문자 처리
        // 무조건 소문자로 변경
        String extensionName = reqDto.getExtensionName();
        extensionName = extensionName.trim();
        extensionName = extensionName.toLowerCase();

        reqDto.setExtensionName(extensionName);

        fileApiRepository.upsertCustomExtension(reqDto);
    }

    /**
     * 커스텀 확장자 차단 해제
     * 
     * @param reqDto
     */
    public void deleteCustomExtension(UpsertCustomFileVM reqDto) {
        // 확장자명 공백 제거 및 대소문자 처리
        // 무조건 소문자로 변경
        String extensionName = reqDto.getExtensionName();
        extensionName = extensionName.trim();
        extensionName = extensionName.toLowerCase();

        reqDto.setExtensionName(extensionName);

        fileApiRepository.upsertCustomExtension(reqDto);
    }

    public void removeCustomExtension() {
        // 삭제할 커스텀 확장자 조회
        List<RemoveCustomFileVM> targetList = fileApiRepository.selectRemoveTargetList();

        if (!CollectionUtils.isEmpty(targetList)) {
            schedulerLogger.info("start custom extension remove...");

            int success = 0;
            int failed = 0;

            // 타겟 하나씩 제거
            for (RemoveCustomFileVM target : targetList) {
                try {
                    int result = fileApiRepository.removeCustomExtension(target);

                    if (result == 0) {
                        failed++;
                    } else {
                        success++;
                    }
                } catch (DataAccessException e) {
                    // DB 관련 에러 발생 시 failed 증가
                    failed++;
                } catch (Exception e) {
                    // 그 외 에러는 failed 증가와 error 로그 생성
                    failed++;
                    schedulerLogger.error(e.getMessage());
                }
            }

            schedulerLogger.info("custom extension remove done : success={}, failed={}", success, failed);
        }
    }
}
