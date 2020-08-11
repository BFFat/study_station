package com.pang.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pang.commonutils.CourseInfoForm;
import com.pang.commonutils.UcenterMember;
import com.pang.order.client.EduClient;
import com.pang.order.client.UcenterClient;
import com.pang.order.entity.TOrder;
import com.pang.order.mapper.TOrderMapper;
import com.pang.order.service.TOrderService;
import com.pang.order.utils.OrderNoUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author pang
 * @since 2020-08-10
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    private final EduClient eduClient;

    private final UcenterClient ucenterClient;

    public TOrderServiceImpl(EduClient eduClient, UcenterClient ucenterClient) {
        this.eduClient = eduClient;
        this.ucenterClient = ucenterClient;
    }

    //创建订单
    @Override
    public String saveOrder(String courseId, String memberId) {
        //远程调用课程服务，根据课程id获取课程信息
        CourseInfoForm courseDto = eduClient.getCourseInfoDto(courseId);

        //远程调用用户服务，根据用户id获取用户信息
        UcenterMember ucenterMember = ucenterClient.getInfo(memberId);

        //创建订单
        TOrder order = new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseDto.getTitle());
        order.setCourseCover(courseDto.getCover());
        order.setTeacherName("test");
        order.setTotalFee(courseDto.getPrice());
        order.setMemberId(memberId);
        order.setMobile(ucenterMember.getMobile());
        order.setNickname(ucenterMember.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
