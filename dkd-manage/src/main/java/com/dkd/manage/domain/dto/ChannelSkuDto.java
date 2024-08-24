package com.dkd.manage.domain.dto;

import lombok.Data;

//数据传输对象
@Data
public class ChannelSkuDto {
    private String innerCode; //售货机编号
    private String channelCode;//货道编号
    private Long skuId;//商品id
}
