package com.pang.ucenter.service;

import com.pang.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pang.ucenter.entity.vo.LoginVo;
import com.pang.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author pang
 * @since 2020-08-08
 */
public interface MemberService extends IService<Member> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    LoginVo getLoginInfo(String memberId);

    Member getByOpenid(String openid);

    Integer countRegisterByDay(String day);
}
