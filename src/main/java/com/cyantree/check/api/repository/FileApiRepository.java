package com.cyantree.check.api.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cyantree.check.api.model.FileExtension.UpsertCustomFileVM;
import com.cyantree.check.api.model.FileExtension.RemoveCustomFileVM;
import com.cyantree.check.api.model.FileExtension.UpdateFixFileVM;

@Mapper
public interface FileApiRepository {

    public void updateFixedExtension(UpdateFixFileVM param);

    public void upsertCustomExtension(UpsertCustomFileVM param);

    public List<RemoveCustomFileVM> selectRemoveTargetList();

    public int removeCustomExtension(RemoveCustomFileVM param);
}
