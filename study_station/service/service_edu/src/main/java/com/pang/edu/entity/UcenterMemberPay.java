package com.pang.edu.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Api
@Data
public class UcenterMemberPay {

    @ApiModelProperty(value = "会员id")
    private String memberId;

    @ApiModelProperty(value = "会员昵称")
    private String nickname;

    @ApiModelProperty(value = "会员头像")
    private String avatar;
}
