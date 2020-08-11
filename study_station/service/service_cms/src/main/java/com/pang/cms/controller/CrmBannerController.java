package com.pang.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pang.cms.entity.CrmBanner;
import com.pang.cms.service.CrmBannerService;
import com.pang.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author pang
 * @since 2020-08-06
 */
@Api(description = "banner后台管理接口")
@RestController
@RequestMapping("/cmsService/banner")
@CrossOrigin
public class CrmBannerController {
    private final CrmBannerService crmBannerService;

    public CrmBannerController(CrmBannerService crmBannerService) {
        this.crmBannerService = crmBannerService;
    }

    @ApiOperation(value = "获取Banner分页列表")
    @GetMapping("/{page}/{limit}")
    public R index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {

        Page<CrmBanner> pageParam = new Page<>(page, limit);
        crmBannerService.pageBanner(pageParam,null);
        return R.ok().data("items", pageParam.getRecords()).data("total", pageParam.getTotal());
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("/get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = crmBannerService.getBannerById(id);
        return R.ok().data("item", banner);
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("/save")
    public R save(@RequestBody CrmBanner banner) {
        crmBannerService.saveBanner(banner);
        return R.ok();
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("/update")
    public R updateById(@RequestBody CrmBanner banner) {
        crmBannerService.updateBannerById(banner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable String id) {
        crmBannerService.removeBannerById(id);
        return R.ok();
    }
}

