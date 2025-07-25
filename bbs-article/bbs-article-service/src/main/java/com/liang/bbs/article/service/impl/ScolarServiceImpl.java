package com.liang.bbs.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liang.bbs.article.facade.dto.ScolarDTO;
import com.liang.bbs.article.facade.dto.ScolarSearchDTO;
import com.liang.bbs.article.facade.server.ScolarService;
import com.liang.bbs.article.persistence.entity.ScolarPo;
import com.liang.bbs.article.persistence.entity.ScolarPoWithBLOBs;
import com.liang.bbs.article.persistence.mapper.ScolarPoMapper;
import com.liang.bbs.article.persistence.mapper.ScolarPoExMapper;
import com.liang.bbs.article.service.mapstruct.ScolarMS;
import com.liang.nansheng.common.auth.UserSsoDTO;
import com.liang.nansheng.common.enums.ResponseCode;
import com.liang.nansheng.common.web.exception.BusinessException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScolarServiceImpl implements ScolarService {
    @Autowired
    private ScolarPoMapper scolarPoMapper;
    @Autowired
    private ScolarPoExMapper ScolarPoExMapper;

    @Override
    public ScolarDTO getById(Integer id) {
        ScolarPoWithBLOBs po = scolarPoMapper.selectByPrimaryKey(id);
        if (po == null) {
            throw BusinessException.build(ResponseCode.DATA_ILLEGAL, "学者不存在");
        }
        ScolarDTO scolar = ScolarMS.INSTANCE.toDTO(po);
        scolar.setAuthorpos(po.getAuthorpos());
        return scolar;
    }

 

    @Override
    public Integer create(ScolarDTO dto) {
        ScolarPoWithBLOBs po = ScolarMS.INSTANCE.toPo(dto);
        
        po.setCreateTime(LocalDateTime.now());
        po.setUpdateTime(LocalDateTime.now());
        if (scolarPoMapper.insertSelective(po) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "创建学者失败");
        }
        return po.getId();
    }

    @Override
    public Boolean update(ScolarDTO dto) {
        ScolarPoWithBLOBs po = ScolarMS.INSTANCE.toPo(dto);
        
        po.setUpdateTime(LocalDateTime.now());
        if (scolarPoMapper.updateByPrimaryKeySelective(po) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "更新学者失败");
        }
        return true;
    }

    @Override
    public Boolean delete(Integer id) {
        if (scolarPoMapper.deleteByPrimaryKey(id) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "删除学者失败");
        }
        return true;
    }
    
    @Override
    public List<ScolarDTO> getRandom(Integer count) {
        return scolarPoMapper.selectRandom(count).stream()
                .map(ScolarMS.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }
    @Override
    public Boolean addrate(Integer score, Integer id) {

        // 校验评分范围（1-5分）
        if (score < 1 || score > 5) {
            throw BusinessException.build(ResponseCode.DATA_ILLEGAL, "评分值必须在1-5之间");
        }
        
        // 获取论文数据
        ScolarPoWithBLOBs sPo = scolarPoMapper.selectByPrimaryKey(id);
        if (sPo == null) {
            throw BusinessException.build(ResponseCode.DATA_ILLEGAL, "论文不存在");
        }
        
        // 解析当前评分数组
        List<Integer> scores = JSON.parseArray(sPo.getTotalrate(), Integer.class);
        
        // 初始化评分数组（如果为空）
        if (CollectionUtils.isEmpty(scores)) {
            scores = Arrays.asList(0, 0, 0, 0, 0);
        }
        
        // 更新对应评分项（score-1转换为数组索引）
        int index = score - 1;
        scores.set(index, scores.get(index) + 1);
        
        // 更新总评分次数
        sPo.setRatenum(sPo.getRatenum() + 1);
        sPo.setTotalrate(JSON.toJSONString(scores));
        sPo.setUpdateTime(LocalDateTime.now());
        
        if (scolarPoMapper.updateByPrimaryKeySelective(sPo) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "更新评分失败");
        }
        return true;
    }
    @Override
    public PageInfo<ScolarDTO> searchfuzz(ScolarSearchDTO searchDTO) {

        List<ScolarDTO> list = ScolarPoExMapper.searchfuzz(
        searchDTO.getName(),
        searchDTO.getInstitution(),
        searchDTO.getBio()
    );
    
    // 包装分页结果
    return new PageInfo<>(list);

    }
}