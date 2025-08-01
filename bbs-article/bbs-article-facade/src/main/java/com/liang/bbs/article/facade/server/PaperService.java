package com.liang.bbs.article.facade.server;

import java.util.List;

import com.liang.bbs.article.facade.dto.PaperDTO;
import com.liang.nansheng.common.auth.UserSsoDTO;

public interface PaperService {
    PaperDTO getById(Integer id);
    List<PaperDTO> getList(Integer num);
    List<PaperDTO> searchPapers(String keyword);
    Boolean create(PaperDTO paperDTO, UserSsoDTO currentUser);
    Boolean update(PaperDTO paperDTO, UserSsoDTO currentUser);
    Boolean delete(Integer id, UserSsoDTO currentUser);
    Boolean addrate(Integer score, Integer id,UserSsoDTO currentUser);
    // List<Integer> getauthbyId(Integer id);
}