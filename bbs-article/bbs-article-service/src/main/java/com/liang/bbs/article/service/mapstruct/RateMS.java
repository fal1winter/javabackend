package com.liang.bbs.article.service.mapstruct;

import com.liang.bbs.article.facade.dto.RateDTO;
import com.liang.bbs.article.persistence.entity.RatePo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface RateMS extends CommonMS<RatePo, RateDTO> {
    RateMS INSTANCE = Mappers.getMapper(RateMS.class);
}