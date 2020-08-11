package com.pang.ucenter.mapper;

import com.pang.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author pang
 * @since 2020-08-08
 */
public interface MemberMapper extends BaseMapper<Member> {

    Integer selectRegisterCount(String day);
}
