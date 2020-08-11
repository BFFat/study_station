package com.pang.sta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pang.sta.client.UcenterClient;
import com.pang.sta.entity.StatisticsDaily;
import com.pang.sta.mapper.StatisticsDailyMapper;
import com.pang.sta.service.StatisticsDailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author pang
 * @since 2020-08-10
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    private final UcenterClient ucenterClient;

    public StatisticsDailyServiceImpl(UcenterClient ucenterClient) {
        this.ucenterClient = ucenterClient;
    }

    public void createStatisticsByDay(String day) {
        //删除已存在的统计对象
        QueryWrapper<StatisticsDaily> dayQueryWrapper = new QueryWrapper<>();
        dayQueryWrapper.eq("date_calculated", day);
        baseMapper.delete(dayQueryWrapper);


        //获取统计信息
        Integer registerNum = (Integer) ucenterClient.registerCount(day).getData().get("countRegister");
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO

        //创建统计对象
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getChartData(String begin, String end, String type) {

        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(type, "date_calculated");
        queryWrapper.between("date_calculated", begin, end);

        List<StatisticsDaily> dayList = baseMapper.selectList(queryWrapper);

        Map<String, Object> map = new HashMap<>();
        List<Integer> dataList = new ArrayList<Integer>();
        List<String> dateList = new ArrayList<String>();
        map.put("dataList", dataList);
        map.put("dateList", dateList);


        for (int i = 0; i < dayList.size(); i++) {
            StatisticsDaily statisticsDaily = dayList.get(i);
            dateList.add(statisticsDaily.getDateCalculated());
            switch (type) {
                case "register_num":
                    dataList.add(statisticsDaily.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(statisticsDaily.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(statisticsDaily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        return map;
    }
}
