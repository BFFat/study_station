package com.pang.edu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-order", fallback = OrderFile.class)
public interface OrderClient {
    //查询订单信息
    @GetMapping("/orderService/order/isBuyCourse/{memberId}/{id}")
    public boolean isBuyCourse(@PathVariable("memberId") String memberId, @PathVariable("id") String id);
}
