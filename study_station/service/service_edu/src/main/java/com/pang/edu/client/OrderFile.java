package com.pang.edu.client;

import org.springframework.stereotype.Component;

@Component
public class OrderFile implements OrderClient{
    @Override
    public boolean isBuyCourse(String memberId, String id) {
        return false;
    }
}
