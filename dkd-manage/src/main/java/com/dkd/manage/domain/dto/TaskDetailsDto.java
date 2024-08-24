package com.dkd.manage.domain.dto;

import lombok.Data;

@Data
public class TaskDetailsDto {
    private String channelCode;//货道编号
    private long expectCapacity;//期望补货数量
    private long skuId;//商品id
    private String skuName;//商品名称
    private String skuImage;//商品图片
}
