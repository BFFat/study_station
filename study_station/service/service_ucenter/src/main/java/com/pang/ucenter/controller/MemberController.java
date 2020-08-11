package com.pang.ucenter.controller;


import com.pang.base.exceptionhandler.GuliException;
import com.pang.commonutils.JwtUtils;
import com.pang.commonutils.R;
import com.pang.ucenter.entity.Member;
import com.pang.ucenter.entity.vo.LoginVo;
import com.pang.ucenter.entity.vo.RegisterVo;
import com.pang.ucenter.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author pang
 * @since 2020-08-08
 */
@RestController
@RequestMapping("/ucenterService/member")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo) {
        String token = memberService.login(loginVo);
        return R.ok().data("token", token);
    }

    @ApiOperation(value = "会员注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("auth/getLoginInfo")
    public R getLoginInfo(HttpServletRequest request){
        try {
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            LoginVo loginVo = memberService.getLoginInfo(memberId);
            return R.ok().data("item", loginVo);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"error");
        }
    }

    //根据token字符串获取用户信息
    @PostMapping("getInfoUc/{id}")
    public com.pang.commonutils.UcenterMember getInfo(@PathVariable String id) {
        //根据用户id获取用户信息
        Member member = memberService.getById(id);
        com.pang.commonutils.UcenterMember ucenterMember = new com.pang.commonutils.UcenterMember();
        BeanUtils.copyProperties(member,ucenterMember);
        return ucenterMember;
    }

    @GetMapping(value = "countRegister/{day}")
    public R registerCount(
            @PathVariable String day){
        Integer count = memberService.countRegisterByDay(day);
        return R.ok().data("countRegister", count);
    }
}

