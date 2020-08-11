package com.pang.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pang.base.exceptionhandler.GuliException;
import com.pang.commonutils.JwtUtils;
import com.pang.ucenter.entity.Member;
import com.pang.ucenter.entity.vo.LoginVo;
import com.pang.ucenter.entity.vo.RegisterVo;
import com.pang.ucenter.mapper.MemberMapper;
import com.pang.ucenter.service.MemberService;
import com.pang.ucenter.utils.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author pang
 * @since 2020-08-08
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    private final RedisTemplate<String, String> redisTemplate;

    public MemberServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 会员登录
     * @param loginVo
     * @return
     */
    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        //校验参数
        if(StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(mobile)) {
            throw new GuliException(20001,"error");
        }

        //获取会员
        Member member = baseMapper.selectOne(new QueryWrapper<Member>().eq("mobile", mobile));
        if(null == member) {
            throw new GuliException(20001,"error");
        }

        //校验密码
        if(!MD5.encrypt(password).equals(member.getPassword())) {
            throw new GuliException(20001,"error");
        }

        //校验是否被禁用
        if(member.getIsDisabled()) {
            throw new GuliException(20001,"error");
        }

        //使用JWT生成token字符串
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return token;
    }

    /**
     * 会员注册
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if(StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            throw new GuliException(20001, "注册失败");
        }

        //校验校验验证码
        //从redis获取发送的验证码
        String mobleCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(mobleCode)) {
            throw new GuliException(20001, "验证码不相等");
        }

        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<Member>().eq("mobile", mobile));
        if(count.intValue() > 0) {
            throw new GuliException(20001, "存在相同的手机号码");
        }

        //添加注册信息到数据库
        Member member = new Member();
        member.setNickname(nickname);
        member.setMobile(registerVo.getMobile());
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        this.save(member);
    }

    @Override
    public LoginVo getLoginInfo(String memberId) {
        Member member = baseMapper.selectById(memberId);
        LoginVo loginInfoVo = new LoginVo();
        BeanUtils.copyProperties(member, loginInfoVo);
        return loginInfoVo;
    }

    @Override
    public Member getByOpenid(String openid) {

        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);

        Member member = baseMapper.selectOne(queryWrapper);
        return member;
    }

    @Override
    public Integer countRegisterByDay(String day) {
        return baseMapper.selectRegisterCount(day);
    }
}
