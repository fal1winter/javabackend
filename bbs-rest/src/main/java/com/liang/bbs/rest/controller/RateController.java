package com.liang.bbs.rest.controller;

import com.liang.bbs.article.facade.dto.RateDTO;
import com.liang.bbs.article.facade.server.PaperService;
import com.liang.bbs.article.facade.server.RateService;
import com.liang.bbs.rest.config.login.NoNeedLogin;
import com.liang.bbs.rest.config.swagger.ApiVersion;
import com.liang.bbs.rest.config.swagger.ApiVersionConstant;
import com.liang.nansheng.common.auth.UserContextUtils;
import com.liang.nansheng.common.auth.UserSsoDTO;
import com.liang.nansheng.common.web.basic.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bbs/rate/")  // 修改路径为评分接口
@Api(tags = "评分接口")          // 修改Swagger标签
public class RateController {
    @Reference
    private RateService rateService;  // 替换为评分服务
    @Reference
    private PaperService paperService;

    @NoNeedLogin
    @GetMapping("get/{id}")
    @ApiOperation(value = "获取评分详情")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<RateDTO> get(@PathVariable Integer id) {
        return ResponseResult.success(rateService.getById(id));
    }
    @NoNeedLogin
    @PostMapping("create")
    @ApiOperation(value = "创建评分")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> create(@RequestBody RateDTO rateDTO) {
        Boolean createResult = rateService.create(rateDTO, UserContextUtils.currentUser());
        
        // 当满足条件时触发论文评分逻辑
        if (rateDTO.getTarget() == 0 && rateDTO.getParentId() == null) {
            paperService.addrate(rateDTO.getRating(), rateDTO.getRateId(), UserContextUtils.currentUser());
        }
        
        return ResponseResult.success(createResult);
    }

    @PostMapping("update")
    @ApiOperation(value = "更新评分")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> update(@RequestBody RateDTO rateDTO) {
        return ResponseResult.success(rateService.update(rateDTO, UserContextUtils.currentUser()));
    }

    @PostMapping("delete/{id}")
    @ApiOperation(value = "删除评分")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> delete(@PathVariable Integer id) {
        return ResponseResult.success(rateService.delete(id, UserContextUtils.currentUser()));
    }

    // 新增根据ID和目标查询接口
    @NoNeedLogin
    @GetMapping("getByIdAndTarget/{id}/{target}")
    @ApiOperation(value = "根据ID和目标获取评分")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<List<RateDTO>> getByIdAndTarget(
        @PathVariable Integer id,
        @PathVariable String target
    ) {
        return ResponseResult.success(rateService.getByRateIdAndTarget(id, target));
    }
}