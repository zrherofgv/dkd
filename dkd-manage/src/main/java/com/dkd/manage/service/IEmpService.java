package com.dkd.manage.service;

import java.util.List;
import com.dkd.manage.domain.Emp;

/**
 * 人员列表Service接口
 * 
 * @author ruoyi
 * @date 2024-08-08
 */
public interface IEmpService
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
     * 批量删除人员列表
     * 
     * @param ids 需要删除的人员列表主键集合
     * @return 结果
     */
    public int deleteTbEmpByIds(Long[] ids);

    /**
     * 删除人员列表信息
     * 
     * @param id 人员列表主键
     * @return 结果
     */
    public int deleteTbEmpById(Long id);
}
