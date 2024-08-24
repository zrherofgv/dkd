package com.dkd.manage.service.impl;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.ServiceException;
import com.dkd.common.constant.DkdContants;
import com.dkd.common.utils.DateUtils;
import com.dkd.manage.domain.Emp;
import com.dkd.manage.domain.TaskDetails;
import com.dkd.manage.domain.VendingMachine;
import com.dkd.manage.domain.dto.TaskDetailsDto;
import com.dkd.manage.domain.dto.TaskDto;
import com.dkd.manage.domain.vo.TaskVo;
import com.dkd.manage.service.IEmpService;
import com.dkd.manage.service.ITaskDetailsService;
import com.dkd.manage.service.IVendingMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.dkd.manage.mapper.TaskMapper;
import com.dkd.manage.domain.Task;
import com.dkd.manage.service.ITaskService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 工单Service业务层处理
 * 
 * @author zzr
 * @date 2024-08-20
 */
@Service
public class TaskServiceImpl implements ITaskService 
{
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private IVendingMachineService vendingMachineService;
    @Autowired
    private IEmpService empService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ITaskDetailsService taskDetailsService;

    /**
     * 查询工单
     * 
     * @param taskId 工单主键
     * @return 工单
     */
    @Override
    public Task selectTaskByTaskId(Long taskId)
    {
        return taskMapper.selectTaskByTaskId(taskId);
    }

    /**
     * 查询工单列表
     * 
     * @param task 工单
     * @return 工单
     */
    @Override
    public List<Task> selectTaskList(Task task)
    {
        return taskMapper.selectTaskList(task);
    }

    /**
     * 新增工单
     * 
     * @param task 工单
     * @return 结果
     */
    @Override
    public int insertTask(Task task)
    {
        task.setCreateTime(DateUtils.getNowDate());
        return taskMapper.insertTask(task);
    }

    /**
     * 修改工单
     * 
     * @param task 工单
     * @return 结果
     */
    @Override
    public int updateTask(Task task)
    {
        task.setUpdateTime(DateUtils.getNowDate());
        return taskMapper.updateTask(task);
    }

    /**
     * 批量删除工单
     * 
     * @param taskIds 需要删除的工单主键
     * @return 结果
     */
    @Override
    public int deleteTaskByTaskIds(Long[] taskIds)
    {
        return taskMapper.deleteTaskByTaskIds(taskIds);
    }

    /**
     * 删除工单信息
     * 
     * @param taskId 工单主键
     * @return 结果
     */
    @Override
    public int deleteTaskByTaskId(Long taskId)
    {
        return taskMapper.deleteTaskByTaskId(taskId);
    }

    /**
     * 查询工单列表
     * @param task
     * @return List<TaskVo>
     * */
    @Override
    public List<TaskVo> selectTaskVoList(Task task){return taskMapper.selectTaskVoList(task);}

    /**
     * 新增运营，运维工单
     * @param taskDto
     * @return 结果
     */
    @Transactional
    @Override
    public int insertTaskDto(TaskDto taskDto) {
        //1.查询售货机是否存在
        VendingMachine vm = vendingMachineService.selectVendingMachineByInnerCode(taskDto.getInnerCode());
        if(vm==null){
            throw new ServiceException("设备不存在");
        }
        //2.校验售货机状态与工单类型是否相符
       checkCreateTask(vm.getVmStatus(), taskDto.getProductTypeId());
        //3.检查设备是否有未完成的同类型工单
        hasTask(taskDto);
        //4.查询并且校验员工是否存在
       Emp emp =  empService.selectTbEmpById(taskDto.getUserId());
        if(emp==null){
            throw new ServiceException("员工不存在");
        }
        //5. 校验员工区域是否匹配
        if((emp.getRegionId().equals(vm.getRegionId()))!=true){
            throw new ServiceException("员工区域与设备区域不匹配");
        }
        //6.将Dto对象转换为po
        Task task = BeanUtil.copyProperties(taskDto,Task.class);//属性复制
        task.setTaskStatus(DkdContants.TASK_STATUS_CREATE);//创建工单
        task.setUserName(emp.getUserName());//执行人名称
        task.setAddr(vm.getAddr());//地址
        task.setCreateTime(DateUtils.getNowDate());//创建时间
        task.setTaskCode(generateTaskCode());//工单编号
        int taskResult = taskMapper.insertTask(task);
        //判断是否为补货工单
        if(taskDto.getProductTypeId()==DkdContants.TASK_TYPE_SUPPLY){
            //获取补货工单详情
            List<TaskDetailsDto> details = taskDto.getDetails();
            if(CollUtil.isEmpty(details)){
                throw new ServiceException("补货工单详情不能为空");
            }
            //将dto转为po补充属性
            List taskDetailsList = details.stream().map(dto ->{
                TaskDetails taskDetails = BeanUtil.copyProperties(dto,TaskDetails.class);
                taskDetails.setTaskId(task.getTaskId());
                return taskDetails;
            } ).collect(Collectors.toList());
            //批量新增
            taskDetailsService.insertTaskDetailsBatch(taskDetailsList);
        }
        return taskResult;
    }



    //创建task条件对象，并设置设备编号和工单类型，以及工单状态为进行中
    private void hasTask(TaskDto taskDto){

        Task taskParam = new Task();
        taskParam.setInnerCode(taskDto.getInnerCode());
        taskParam.setProductTypeId(taskDto.getProductTypeId());
        taskParam.setTaskStatus(DkdContants.TASK_STATUS_PROGRESS);
        //调用taskMapper查询数据库查看是否有符合条件的工单列表
        List<Task> taskList = taskMapper.selectTaskList(taskParam);
        //如果有未完成的同类型工单，抛出异常
        if(taskList!=null&&taskList.size()>0){
            throw new ServiceException("设备有未完成的同类型工单，不能进行此工单");
        }

    }
    //生成并获取当天工单编号
    private String generateTaskCode(){
        //获取当前日期并格式化为”yyyyMMdd“
        String dateStr = DateUtils.getDate().replaceAll("-","");
        //根据日期生成redis的键
        String key ="dkd.task.code"+dateStr;
        //判断key是否存在
        if(!redisTemplate.hasKey(key)){
            //如果key存在，设置初始值为1，并指定过期时间为1天
            redisTemplate.opsForValue().set(key,1, Duration.ofDays(1));
            //返回工单编号
            return dateStr+"0001";
        }
        //如果key存在，计数器+1，确保字符串长度为4
        return dateStr+StrUtil.padPre(redisTemplate.opsForValue().increment(key).toString(),4,'0');
    }
    //校验售货机状态与工单类型是否相符
    private void checkCreateTask(Long vmStatus,Long productTypeId){
        //如果是投放工单，设备在运行中，抛出异常
        if(productTypeId== DkdContants.TASK_TYPE_DEPLOY&&vmStatus==DkdContants.VM_STATUS_RUNNING){
            throw new ServiceException("设备在运行中，不能进行投放工单");
        }
        //如果是维修工单，设备不在运行中，抛出异常
        if(productTypeId== DkdContants.TASK_TYPE_REPAIR&&vmStatus!=DkdContants.VM_STATUS_RUNNING){
            throw new ServiceException("设备不在运行中，不能进行维修工单");
        }
        //如果是补货工单，设备不在运行中，抛出异常
        if(productTypeId== DkdContants.TASK_TYPE_SUPPLY&&vmStatus!=DkdContants.VM_STATUS_RUNNING){
            throw new ServiceException("设备不在运行中，不能进行补货工单");
        }
        //如果是撤机工单，设备不在运行中，抛出异常
        if(productTypeId== DkdContants.TASK_TYPE_REVOKE&&vmStatus!=DkdContants.VM_STATUS_RUNNING){
            throw new ServiceException("设备不在运行中，不能进行撤机工单");
        }
    }
    /**
     * 取消工单
     * @param task
     * @return 结果
     */
    @Override
    public int cancelTask(Task task) {
    //判断工单状态是否可以取消
        //根据工单id查询数据库
        Task taskParam = taskMapper.selectTaskByTaskId(task.getTaskId());
        //判断工单状态是否为已取消，如果是则抛出异常
        if(taskParam.getTaskStatus()==DkdContants.TASK_STATUS_CANCEL){
            throw new ServiceException("工单已取消，不能重复取消");
        }
        //判断工单状态是否为已完成，如果是，则抛出异常
        if(taskParam.getTaskStatus()==DkdContants.TASK_STATUS_FINISH){
            throw new ServiceException("工单已完成，不能取消");
        }
        //2.设置更新字段
        task.setTaskStatus(DkdContants.TASK_STATUS_CANCEL);//设置工单状态为已取消
        task.setUpdateTime(DateUtils.getNowDate());//设置更新时间
        //3.更新工单
        return taskMapper.updateTask(task);//注意别传错了，这里是前端的task参数
}
}
