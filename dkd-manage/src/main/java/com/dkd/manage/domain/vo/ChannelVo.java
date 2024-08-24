package com.dkd.manage.domain.vo;

import com.dkd.manage.domain.Sku;
import com.dkd.smanage.domain.Channel;
import lombok.Data;

@Data
public class ChannelVo extends Channel {
    //商品对象
    private Sku sku;
}
