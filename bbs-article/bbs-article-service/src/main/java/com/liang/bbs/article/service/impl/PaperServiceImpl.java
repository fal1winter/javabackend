package com.liang.bbs.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.liang.bbs.article.facade.dto.PaperDTO;
import com.liang.bbs.article.facade.server.PaperService;
import com.liang.bbs.article.persistence.entity.PaperPo;
import com.liang.bbs.article.persistence.entity.PaperPoExample;
import com.liang.bbs.article.persistence.mapper.PaperPoMapper;
import com.liang.bbs.article.persistence.mapper.PaperPoExMapper;
import com.liang.nansheng.common.auth.UserSsoDTO;
import com.liang.nansheng.common.enums.ResponseCode;
import com.liang.nansheng.common.web.exception.BusinessException;
import java.util.HashMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    private PaperPoMapper paperPoMapper;
    @Autowired
    private PaperPoExMapper PaperPoExMapper;
    @Override
    public PaperDTO getById(Integer id) {
        PaperPo paperPo = PaperPoExMapper.selectPaperWithAuthors(id);
        if (paperPo == null) {
            throw BusinessException.build(ResponseCode.DATA_ILLEGAL, "论文不存在");
        }
        return convertToDTO(paperPo);
    }

    @Override
    public List<PaperDTO> getList(Integer num) {
        // List<PaperPo> paperPos = PaperPoExMapper.selectRandom(num);
        
        // // 保持原有的DTO转换逻辑
        // return paperPos.stream()
        //     .map(po -> {
        //         PaperDTO dto = convertToDTO(po);
        //         return dto;
        //     })
        //     .collect(Collectors.toList());
        List<Integer> ids = PaperPoExMapper.selectValidIds(num);
        List<PaperPo> paperPos = PaperPoExMapper.selectbyList(ids) ;
            return paperPos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean create(PaperDTO paperDTO, UserSsoDTO currentUser) {
        PaperPo paperPo = convertToPo(paperDTO);
        paperPo.setCreateTime(LocalDateTime.now());
        paperPo.setUpdateTime(LocalDateTime.now());
        if (paperPoMapper.insertSelective(paperPo) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "创建论文失败");
        }
        return true;
    }

    @Override
    public Boolean update(PaperDTO paperDTO, UserSsoDTO currentUser) {
        PaperPo paperPo = convertToPo(paperDTO);
        paperPo.setUpdateTime(LocalDateTime.now());
        if (paperPoMapper.updateByPrimaryKeySelective(paperPo) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "更新论文失败");
        }
        return true;
    }

    @Override
    public Boolean delete(Integer id, UserSsoDTO currentUser) {
        if (paperPoMapper.deleteByPrimaryKey(id) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "删除论文失败");
        }
        return true;
    }

    @Override
    public Boolean addrate(Integer score, Integer id, UserSsoDTO currentUser) {
        // 校验评分范围（1-5分）
        if (score < 1 || score > 5) {
            throw BusinessException.build(ResponseCode.DATA_ILLEGAL, "评分值必须在1-5之间");
        }
        
        // 获取论文数据
        PaperPo paperPo = paperPoMapper.selectByPrimaryKey(id);
        if (paperPo == null) {
            throw BusinessException.build(ResponseCode.DATA_ILLEGAL, "论文不存在");
        }
        
        // 解析当前评分数组
        List<Integer> scores = JSON.parseArray(paperPo.getTotalScore(), Integer.class);
        
        // 初始化评分数组（如果为空）
        if (CollectionUtils.isEmpty(scores)) {
            scores = Arrays.asList(0, 0, 0, 0, 0);
        }
        
        // 更新对应评分项（score-1转换为数组索引）
        int index = score - 1;
        scores.set(index, scores.get(index) + 1);
        
        // 更新总评分次数
        paperPo.setRatingCount(paperPo.getRatingCount() + 1);
        paperPo.setTotalScore(JSON.toJSONString(scores));
        paperPo.setUpdateTime(LocalDateTime.now());
        
        if (paperPoMapper.updateByPrimaryKeySelective(paperPo) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "更新评分失败");
        }
        return true;
    }
    

    private PaperDTO convertToDTO(PaperPo po) {
        if (po == null) {
            return null;
        }
        
        PaperDTO dto = new PaperDTO();
        dto.setId(po.getId());
        dto.setTitle(po.getTitle());
        dto.setInstitution(po.getInstitution());
        dto.setConference(po.getConference());
        dto.setCitations(po.getCitations());
        dto.setFavorites(po.getFavorites());
        dto.setAbstractText(po.getAbstracttext());
        dto.setPaperUrl(po.getPaperUrl());
        dto.setTotalScore(JSON.parseArray(po.getTotalScore(), Integer.class));
        dto.setRatingCount(po.getRatingCount());
        dto.setCreateTime(po.getCreateTime());
        dto.setUpdateTime(po.getUpdateTime());
        dto.setPicUrl(po.getPicUrl());
        dto.setBio(po.getBio());
        dto.setAuthors(JSON.parseArray(po.getAuthors(), String.class));
        dto.setKeywords(JSON.parseArray(po.getKeywords(), String.class));
        dto.setAuthorpos(po.getAuthorpos());
        return dto;
    }

    private PaperPo convertToPo(PaperDTO dto) {
        if (dto == null) {
            return null;
        }
        
        PaperPo po = new PaperPo();
        po.setId(dto.getId());
        po.setTitle(dto.getTitle());
        po.setInstitution(dto.getInstitution());
        po.setConference(dto.getConference());
        po.setCitations(dto.getCitations());
        po.setFavorites(dto.getFavorites());
        po.setAbstracttext(dto.getAbstractText());
        po.setPaperUrl(dto.getPaperUrl());
        po.setTotalScore(JSON.toJSONString(dto.getTotalScore()));
        po.setRatingCount(dto.getRatingCount());
        po.setCreateTime(dto.getCreateTime());
        po.setUpdateTime(dto.getUpdateTime());
        po.setPicUrl(dto.getPicUrl());
        po.setBio(dto.getBio());
        po.setAuthors(JSON.toJSONString(dto.getAuthors()));
        po.setKeywords(JSON.toJSONString(dto.getKeywords()));
        
        return po;
    }

    @Override
    public List<PaperDTO> searchPapers(String keyword) {
        PaperPoExample example = new PaperPoExample();
        
        // 创建第一个条件对象
        PaperPoExample.Criteria criteria1 = example.createCriteria();
        criteria1.andTitleLike("%" + keyword + "%");
        
        // 创建第二个条件对象
        PaperPoExample.Criteria criteria2 = example.or();
        criteria2.andAuthorsLike("%" + keyword + "%");
        
        // 创建第三个条件对象
        PaperPoExample.Criteria criteria3 = example.or();
        criteria3.andAbstracttextLike("%" + keyword + "%");
        
        // 创建第四个条件对象
        PaperPoExample.Criteria criteria4 = example.or();
        criteria4.andKeywordsLike("%" + keyword + "%");
        
        return paperPoMapper.selectByExample(example).stream()
            .map(this::convertToDTO)  // 替换PaperMS.INSTANCE::toDTO
            .collect(Collectors.toList());
    }

    // @Override
    // public List<Integer> getauthbyId(Integer id) {
    //     // TODO Auto-generated method stub
    //     return paperPoMapper.getauthbyId(id);
    // }
}