package com.pang.sta.controller;


import com.pang.commonutils.R;
import com.pang.sta.service.StatisticsDailyService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author pang
 * @since 2020-08-10
 */
@RestController
@RequestMapping("/staService/statistics-daily")
public class StatisticsDailyController {

    private final StatisticsDailyService statisticsDailyService;

    public StatisticsDailyController(StatisticsDailyService statisticsDailyService) {
        this.statisticsDailyService = statisticsDailyService;
    }

    @PostMapping("{day}")
    public R createStatisticsByDate(@PathVariable String day) {
        statisticsDailyService.createStatisticsByDay(day);
        return R.ok();
    }

    @GetMapping("show-chart/{begin}/{end}/{type}")
    public R showChart(@PathVariable String begin,@PathVariable String end,@PathVariable String type){
        Map<String, Object> map = statisticsDailyService.getChartData(begin, end, type);
        return R.ok().data(map);
    }

}

