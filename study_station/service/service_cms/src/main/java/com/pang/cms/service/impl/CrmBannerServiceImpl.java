package com.pang.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pang.cms.entity.CrmBanner;
import com.pang.cms.mapper.CrmBannerMapper;
import com.pang.cms.service.CrmBannerService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author pang
 * @since 2020-08-06
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    /**
     * @Author: SmallPang
     * @Description: 获取Banner分页列表
     * @Date: 2020/8/7
     * @Param pageParam:
     * @Param o:
     * @return: void
     **/
    @Override
    public void pageBanner(Page<CrmBanner> pageParam, Object o) {
        baseMapper.selectPage(pageParam, null);
    }

    @Override
    public CrmBanner getBannerById(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    @CacheEvict(value = "banner", allEntries=true)
    public void saveBanner(CrmBanner banner) {
        baseMapper.insert(banner);
    }

    @Override
    @CacheEvict(value = "banner", allEntries=true)
    public void updateBannerById(CrmBanner banner) {
        baseMapper.updateById(banner);
    }

    @Override
    @CacheEvict(value = "banner", allEntries=true)
    public void removeBannerById(String id) {
        baseMapper.deleteById(id);
    }


    @Override
    @Cacheable(value = "banner", key = "'selectIndexList'")
    public List<CrmBanner> selectIndexList() {
        List<CrmBanner> list = baseMapper.selectList(new QueryWrapper<CrmBanner>().orderByDesc("sort"));
        return list;
    }
}
