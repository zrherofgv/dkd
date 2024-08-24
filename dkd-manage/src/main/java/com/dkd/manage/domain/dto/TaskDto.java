package com.dkd.manage.domain.dto;


import lombok.Data;

import java.util.List;

@Data
public class TaskDto {
    private long createType;//创建类型
    private String innerCode;//关联设备编号
    private long userId;//任务执行人id
    private long assignorid;//用户创建人id
    private long productTypeId;//工单类型
    private String desc;//描述信息
    private List<TaskDetailsDto> details;//工单详情（只有补货工单才涉及）
}
