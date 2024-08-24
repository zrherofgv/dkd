package com.dkd.manage.service.impl;

import java.util.List;
import com.dkd.common.utils.DateUtils;
import com.dkd.manage.domain.Role;
import com.dkd.manage.mapper.RegionMapper;
import com.dkd.manage.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dkd.manage.mapper.EmpMapper;
import com.dkd.manage.domain.Emp;
import com.dkd.manage.service.IEmpService;

/**
 * 人员列表Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-08-08
 */
@Service
public class EmpServiceImpl implements IEmpService
{
    @Autowired
    private EmpMapper EmpMapper;
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 查询人员列表
     * 
     * @param id 人员列表主键
     * @return 人员列表
     */
    @Override
    public Emp selectTbEmpById(Long id)
    {
        return EmpMapper.selectTbEmpById(id);
    }

    /**
     * 查询人员列表列表
     * 
     * @param tbEmp 人员列表
     * @return 人员列表
     */
    @Override
    public List<Emp> selectTbEmpList(Emp tbEmp)
    {
        return EmpMapper.selectTbEmpList(tbEmp);
    }

    /**
     * 新增人员列表
     * 
     * @param tbEmp 人员列表
     * @return 结果
     */
    @Override
    public int insertTbEmp(Emp tbEmp)
    {
        tbEmp.setCreateTime(DateUtils.getNowDate());
        //补充区域名称
        tbEmp.setRegionName(regionMapper.selectRegionById(tbEmp.getRegionId()).getName());
        //补充角色信息
        Role role = roleMapper.selectRoleByRoleId(tbEmp.getRoleId());
        tbEmp.setRoleName(role.getRoleName());
        tbEmp.setRoleCode(role.getRoleCode());
        return EmpMapper.insertTbEmp(tbEmp);
    }

    /**
     * 修改人员列表
     * 
     * @param tbEmp 人员列表
     * @return 结果
     */
    @Override
    public int updateTbEmp(Emp tbEmp)
    {
        tbEmp.setUpdateTime(DateUtils.getNowDate());
        //补充区域名称
        tbEmp.setRegionName(regionMapper.selectRegionById(tbEmp.getRegionId()).getName());
        //补充角色信息
        Role role = roleMapper.selectRoleByRoleId(tbEmp.getRoleId());
        tbEmp.setRoleName(role.getRoleName());
        tbEmp.setRoleCode(role.getRoleCode());
        return EmpMapper.updateTbEmp(tbEmp);
    }

    /**
     * 批量删除人员列表
     * 
     * @param ids 需要删除的人员列表主键
     * @return 结果
     */
    @Override
    public int deleteTbEmpByIds(Long[] ids)
    {
        return EmpMapper.deleteTbEmpByIds(ids);
    }

    /**
     * 删除人员列表信息
     * 
     * @param id 人员列表主键
     * @return 结果
     */
    @Override
    public int deleteTbEmpById(Long id)
    {
        return EmpMapper.deleteTbEmpById(id);
    }
}
