package com.pang.msm.controller;

import com.pang.commonutils.R;
import com.pang.msm.service.MsmService;
import com.pang.msm.utils.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/eduMsm")
@CrossOrigin //跨域
public class MsmApiController {


    private final MsmService msmService;


    private final RedisTemplate<String, String> redisTemplate;

    public MsmApiController(MsmService msmService, RedisTemplate<String, String> redisTemplate) {
        this.msmService = msmService;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping(value = "/send/{phone}")
    public R code(@PathVariable String phone) {
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) return R.ok();

        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code", code);
        boolean isSend = msmService.send(phone, "SMS_180051135", param);
        if(isSend) {
            redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("发送短信失败");
        }
    }
}
