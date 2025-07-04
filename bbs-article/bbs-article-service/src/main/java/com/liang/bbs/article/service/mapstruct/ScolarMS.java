package com.liang.bbs.article.service.mapstruct;

import com.liang.bbs.article.facade.dto.ScolarDTO;
import com.liang.bbs.article.persistence.entity.ScolarPo;
import com.liang.bbs.article.persistence.entity.ScolarPoWithBLOBs;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ScolarMS extends CommonMS<ScolarPoWithBLOBs, ScolarDTO> {
    ScolarMS INSTANCE = Mappers.getMapper(ScolarMS.class);

  
}