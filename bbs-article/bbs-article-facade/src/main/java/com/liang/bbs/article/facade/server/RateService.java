package com.liang.bbs.article.facade.server;

import java.util.List;

import com.liang.bbs.article.facade.dto.RateDTO;
import com.liang.nansheng.common.auth.UserSsoDTO;

public interface RateService {
    RateDTO getById(Integer id);
    List<RateDTO> getByIdandTar(Integer id, String target);
    
    Boolean create(RateDTO rateDTO, UserSsoDTO currentUser); // 参数名改为rateDTO
    Boolean update(RateDTO rateDTO, UserSsoDTO currentUser); // 参数名改为rateDTO
    Boolean delete(Integer id, UserSsoDTO currentUser);
    
    List<RateDTO> getByRateIdAndTarget( Integer rateId, String target);
    
}