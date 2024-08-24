package com.dkd.manage.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.dkd.common.annotation.Excel;
import com.dkd.common.core.domain.BaseEntity;

/**
 * 工单详情对象 tb_task_details
 * 
 * @author zzr
 * @date 2024-08-20
 */
@ApiModel(description = "工单详情模型")
public class TaskDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "工单详情ID")
    private Long detailsId;

    @ApiModelProperty(value = "工单ID")
    @Excel(name = "工单Id")
    private Long taskId;

    @ApiModelProperty(value = "货道编号")
    @Excel(name = "货道编号")
    private String channelCode;

    @ApiModelProperty(value = "补货期望容量")
    @Excel(name = "补货期望容量")
    private Long expectCapacity;

    @ApiModelProperty(value = "商品ID")
    @Excel(name = "商品Id")
    private Long skuId;

    @ApiModelProperty(value = "商品名称")
    @Excel(name = "${comment}", readConverterExp = "${column.readConverterExp()}")
    private String skuName;

    @ApiModelProperty(value = "商品图片")
    @Excel(name = "${comment}", readConverterExp = "${column.readConverterExp()}")
    private String skuImage;
    public void setDetailsId(Long detailsId) 
    {
        this.detailsId = detailsId;
    }

    public Long getDetailsId() 
    {
        return detailsId;
    }
    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }
    public void setChannelCode(String channelCode) 
    {
        this.channelCode = channelCode;
    }

    public String getChannelCode() 
    {
        return channelCode;
    }
    public void setExpectCapacity(Long expectCapacity) 
    {
        this.expectCapacity = expectCapacity;
    }

    public Long getExpectCapacity() 
    {
        return expectCapacity;
    }
    public void setSkuId(Long skuId) 
    {
        this.skuId = skuId;
    }

    public Long getSkuId() 
    {
        return skuId;
    }
    public void setSkuName(String skuName) 
    {
        this.skuName = skuName;
    }

    public String getSkuName() 
    {
        return skuName;
    }
    public void setSkuImage(String skuImage) 
    {
        this.skuImage = skuImage;
    }

    public String getSkuImage() 
    {
        return skuImage;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("detailsId", getDetailsId())
            .append("taskId", getTaskId())
            .append("channelCode", getChannelCode())
            .append("expectCapacity", getExpectCapacity())
            .append("skuId", getSkuId())
            .append("skuName", getSkuName())
            .append("skuImage", getSkuImage())
            .toString();
    }
}
