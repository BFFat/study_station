package com.pang.order.controller;


import com.pang.commonutils.R;
import com.pang.order.service.TPayLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author pang
 * @since 2020-08-10
 */
@RestController
@RequestMapping("/order/t-pay-log")
public class TPayLogController {

    private final TPayLogService tPayLogService;

    public TPayLogController(TPayLogService tPayLogService) {
        this.tPayLogService = tPayLogService;
    }

    /**
     * 生成二维码
     *
     * @return
     */
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {
        Map map = tPayLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    /**
     * @Author: SmallPang
     * @Description: 获取支付状态
     * @Date: 2020/8/10
     * @Param orderNo:
     * @return: com.pang.commonutils.R
     **/
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        //调用查询接口
        Map<String, String> map = tPayLogService.queryPayStatus(orderNo);
        if (map == null) {//出错
            return R.error().message("支付出错");
        }
        if (map.get("trade_state").equals("SUCCESS")) {//如果成功
            //更改订单状态
            tPayLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }

        return R.ok().code(25000).message("支付中");
    }
}

