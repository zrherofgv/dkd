package com.dkd.manage.service;

import java.util.List;
import com.dkd.manage.domain.Sku;

/**
 * 商品管理Service接口
 * 
 * @author zzr
 * @date 2024-08-18
 */
public interface ISkuService 
{
    /**
     * 查询商品管理
     * 
     * @param skuId 商品管理主键
     * @return 商品管理
     */
    public Sku selectSkuBySkuId(Long skuId);

    /**
     * 查询商品管理列表
     * 
     * @param sku 商品管理
     * @return 商品管理集合
     */
    public List<Sku> selectSkuList(Sku sku);

    /**
     * 新增商品管理
     * 
     * @param sku 商品管理
     * @return 结果
     */
    public int insertSku(Sku sku);

    /**
     * 修改商品管理
     * 
     * @param sku 商品管理
     * @return 结果
     */
    public int updateSku(Sku sku);

    /**
     * 批量删除商品管理
     * 
     * @param skuIds 需要删除的商品管理主键集合
     * @return 结果
     */
    public int deleteSkuBySkuIds(Long[] skuIds);

    /**
     * 删除商品管理信息
     * 
     * @param skuId 商品管理主键
     * @return 结果
     */
    public int deleteSkuBySkuId(Long skuId);
    /**
     * 批量新增商品管理
     * @Param skuList
     * @return 结果
     */

    public int batchInsertSku(List<Sku> skuList);

}
