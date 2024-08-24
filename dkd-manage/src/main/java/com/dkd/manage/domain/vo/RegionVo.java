package com.dkd.manage.domain.vo;

import com.dkd.manage.domain.Region;
import lombok.Data;
// lombok 自动生成构造方法等
@Data
public class RegionVo extends Region {
    //点位数量
    private Integer nodeCount;
}
