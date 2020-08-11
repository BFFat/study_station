package com.pang.cms.controller;

import com.pang.cms.entity.CrmBanner;
import com.pang.cms.service.CrmBannerService;
import com.pang.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cmsService/front")
@Api(description = "网站首页Banner列表")
@CrossOrigin //跨域
public class BannerApiController {

    private final CrmBannerService crmBannerService;

    public BannerApiController(CrmBannerService crmBannerService) {
        this.crmBannerService = crmBannerService;
    }


    @ApiOperation(value = "获取首页banner")
    @GetMapping("/getAllBanner")
    public R index() {
        List<CrmBanner> list = crmBannerService.selectIndexList();
        return R.ok().data("bannerList", list);
    }

}
