package com.pang.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pang.commonutils.JwtUtils;
import com.pang.commonutils.R;
import com.pang.order.entity.TOrder;
import com.pang.order.service.TOrderService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author pang
 * @since 2020-08-10
 */
@CrossOrigin
@RestController
@RequestMapping("/order/t-order")
public class TOrderController {

    private final TOrderService orderService;

    public TOrderController(TOrderService orderService) {
        this.orderService = orderService;
    }

    //根据课程id和用户id创建订单，返回订单id
    @PostMapping("createOrder/{courseId}")
    public R save(@PathVariable String courseId, HttpServletRequest request) {
        String orderId = orderService.saveOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId", orderId);
    }

    @GetMapping("getOrder/{orderId}")
    public R get(@PathVariable String orderId) {
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        TOrder order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }

    /**
     * @Author: SmallPang
     * @Description: 根据用户id和课程id查询订单信息
     * @Date: 2020/8/10
     * @Param memberId:
     * @Param id:
     * @return: boolean
     **/
    @GetMapping("isBuyCourse/{memberId}/{id}")
    public boolean isBuyCourse(@PathVariable String memberId,
                               @PathVariable String id) {
        //订单状态是1表示支付成功
        int count = orderService.count(new QueryWrapper<TOrder>().eq("member_id", memberId).eq("course_id", id).eq("status", 1));
        if(count>0) {
            return true;
        } else {
            return false;
        }
    }
}

