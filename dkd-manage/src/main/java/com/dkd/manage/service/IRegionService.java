package com.dkd.manage.service;

import java.util.List;
import com.dkd.manage.domain.Region;
import com.dkd.manage.domain.vo.RegionVo;

/**
 * 区域管理Service接口
 * 
 * @author zzr
 * @date 2024-08-05
 */
public interface IRegionService 
{
    /**
     * 查询区域管理
     * 
     * @param id 区域管理主键
     * @return 区域管理
     */
    public Region selectRegionById(Long id);

    /**
     * 查询区域管理列表
     * 
     * @param region 区域管理
     * @return 区域管理集合
     */
    public List<Region> selectRegionList(Region region);

    /**
     * 新增区域管理
     * 
     * @param region 区域管理
     * @return 结果
     */
    public int insertRegion(Region region);

    /**
     * 修改区域管理
     * 
     * @param region 区域管理
     * @return 结果
     */
    public int updateRegion(Region region);

    /**
     * 批量删除区域管理
     * 
     * @param ids 需要删除的区域管理主键集合
     * @return 结果
     */
    public int deleteRegionByIds(Long[] ids);

    /**
     * 删除区域管理信息
     * 
     * @param id 区域管理主键
     * @return 结果
     */
    public int deleteRegionById(Long id);
    /**
     * 查询区域列表
     *
     * @param region 区域管理
     * @return 区域管理(RegionVo)集合
     */
    public List<RegionVo> selectRegionVoList(Region region);
}

