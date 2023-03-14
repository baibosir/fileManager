package org.sq.zbnss.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.shiro.SecurityUtils;
import org.sq.zbnss.entity.Machineroom;
import org.sq.zbnss.dao.MachineroomDao;
import org.sq.zbnss.entity.User;
import org.sq.zbnss.service.MachineroomService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.sq.zbnss.uitl.Pagination;
import org.sq.zbnss.uitl.UUIDUtil;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * 机房(TbMachineroom)表服务实现类
 *
 * @author makejava
 * @since 2023-03-06 20:14:26
 */
@Service("tbMachineroomService")
public class MachineroomServiceImpl implements MachineroomService {
    @Resource
    private MachineroomDao machineroomDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Machineroom queryById(Integer id) {
        return this.machineroomDao.queryById(id);
    }

    @Override
    public IPage<Machineroom> queryByPage(Machineroom machineroom, Integer pageNumber, Integer pageSize){
        IPage<Machineroom> page =  new Pagination<>(pageNumber, pageSize);
        return machineroomDao.queryMRoom(page, machineroom);
    }

    /**
     * 分页查询
     *
     * @param machineroom 筛选条件
     * @return 查询结果
     */
    @Override
    public ArrayList<Machineroom> queryByPage(Machineroom machineroom) {
        return this.machineroomDao.queryAllByLimit(machineroom);
    }

    /**
     * 新增数据
     *
     * @param machineroom 实例对象
     * @return 实例对象
     */
    @Override
    public Machineroom insert(Machineroom machineroom) {
        User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
        machineroom.setMachineId(UUIDUtil.getUniqueIdByUUId());
        int num = this.machineroomDao.insert(machineroom);
        if(num == 0){
            return null;
        }
        return this.machineroomDao.queryAllByLimit(machineroom).get(0);
    }

    /**
     * 修改数据
     *
     * @param machineroom 实例对象
     * @return 实例对象
     */
    @Override
    public Machineroom update(Machineroom machineroom) {
       int num = this.machineroomDao.update(machineroom);
       if(num == 0){
            return null;
       }
        return this.queryById(machineroom.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.machineroomDao.deleteById(id) > 0;
    }
}
