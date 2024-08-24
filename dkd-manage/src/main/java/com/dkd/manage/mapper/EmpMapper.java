package com.dkd.manage.mapper;

import java.util.List;
import com.dkd.manage.domain.Emp;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 人员列表Mapper接口
 * 
 * @author ruoyi
 * @date 2024-08-08
 */
public interface EmpMapper
{
    /**
     * 查询人员列表
     * 
     * @param id 人员列表主键
     * @return 人员列表
     */
    public Emp selectTbEmpById(Long id);

    /**
     * 查询人员列表列表
     * 
     * @param tbEmp 人员列表
     * @return 人员列表集合
     */
    public List<Emp> selectTbEmpList(Emp tbEmp);

    /**
     * 新增人员列表
     * 
     * @param tbEmp 人员列表
     * @return 结果
     */
    public int insertTbEmp(Emp tbEmp);

    /**
     * 修改人员列表
     * 
     * @param tbEmp 人员列表
     * @return 结果
     */
    public int updateTbEmp(Emp tbEmp);

    /**
     * 删除人员列表
     * 
     * @param id 人员列表主键
     * @return 结果
     */
    public int deleteTbEmpById(Long id);

    /**
     * 批量删除人员列表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbEmpByIds(Long[] ids);
    /**
     *
     * 根据区域id修改区域名称
     *@param regionId,regionName
     *      * @return 结果
     * */
     @Update("update tb_emp set region_name=#{regionName} where region_id=#{regionId}")
    public int updateByRegionId(@Param("regionName") String regionName, @Param("regionId") Long regionId);
}
