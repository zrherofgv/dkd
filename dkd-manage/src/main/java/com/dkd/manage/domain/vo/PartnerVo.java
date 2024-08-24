package com.dkd.manage.domain.vo;

import com.dkd.manage.domain.Partner;
import lombok.Data;
import org.springframework.security.core.parameters.P;

@Data
public class PartnerVo extends Partner {
    private Integer nodeCount;
}
