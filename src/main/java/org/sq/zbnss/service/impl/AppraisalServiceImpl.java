package org.sq.zbnss.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.shiro.SecurityUtils;
import org.sq.zbnss.dao.AppraisalDao;
import org.sq.zbnss.entity.Appraisal;
import org.sq.zbnss.entity.User;
import org.sq.zbnss.service.AppraisalService;
import org.springframework.stereotype.Service;
import org.sq.zbnss.uitl.Pagination;
import org.sq.zbnss.uitl.UUIDUtil;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * 测评信息(TbAppraisal)表服务实现类
 *
 * @author makejava
 * @since 2023-03-06 20:14:13
 */
@Service("tbAppraisalService")
public class AppraisalServiceImpl implements AppraisalService {
    @Resource
    private AppraisalDao appraisalDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Appraisal queryById(Integer id) {
        return this.appraisalDao.queryById(id);
    }


    @Override
    public IPage<Appraisal> queryByPage(Appraisal appraisal, Integer pageNumber, Integer pageSize) {
        IPage<Appraisal> page =  new Pagination<>(pageNumber, pageSize);
        return appraisalDao.selectAppraisal(page, appraisal);
    }

    /**
     * 分页查询
     *
     * @param appraisal 筛选条件
     * @return 查询结果
     */
    @Override
    public ArrayList<Appraisal> queryByPage(Appraisal appraisal) {
        return  this.appraisalDao.queryAllByLimit(appraisal);
    }

    /**
     * 新增数据
     *
     * @param appraisal 实例对象
     * @return 实例对象
     */
    @Override
    public Appraisal insert(Appraisal appraisal) {
        User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
        appraisal.setInsertUser(loginUser);
        appraisal.setUpdateUser(loginUser);
        appraisal.setAppraisal(UUIDUtil.getUniqueIdByUUId());
        int num = this.appraisalDao.insert(appraisal);
        if(num == 0){
            return null;
        }
        return appraisal;
    }

    /**
     * 修改数据
     *
     * @param appraisal 实例对象
     * @return 实例对象
     */
    @Override
    public Appraisal update(Appraisal appraisal) {
        int num = this.appraisalDao.update(appraisal);
        if(num == 0){
            return null;
        }
        return this.queryById(appraisal.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.appraisalDao.deleteById(id) > 0;
    }
}
