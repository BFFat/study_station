package com.pang.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    //根据课程id查询课程信息
    @PostMapping("/ucenterService/member/getInfoUc/{id}")
    public com.pang.commonutils.UcenterMember getInfo(@PathVariable("id") String id);
}
