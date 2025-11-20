package com.cyantree.check.web.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cyantree.check.web.model.FileExtension.CustomFileVM;
import com.cyantree.check.web.model.FileExtension.FixFileVM;

@Mapper
public interface FileWebRepository {
    // 고정 확장자 정보 조회
    public List<FixFileVM> selectFixedExtensionList();

    // 커스텀 확장자 정보 조회
    public List<CustomFileVM> selectCustomExtensionList();
}
