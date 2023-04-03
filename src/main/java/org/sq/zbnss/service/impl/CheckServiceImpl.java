package org.sq.zbnss.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.shiro.SecurityUtils;
import org.sq.zbnss.entity.Check;
import org.sq.zbnss.dao.CheckDao;
import org.sq.zbnss.entity.User;
import org.sq.zbnss.service.CheckService;
import org.springframework.stereotype.Service;
import org.sq.zbnss.uitl.Pagination;
import org.sq.zbnss.uitl.UUIDUtil;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.sq.zbnss.uitl.DateUtil.getAssignedDateFirstDayOfMonth;

/**
 * 检查结果记录(TbCheck)表服务实现类
 *
 * @author makejava
 * @since 2023-03-06 20:14:18
 */
@Service("tbCheckService")
public class CheckServiceImpl implements CheckService {
    @Resource
    private CheckDao checkDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Check queryById(Integer id) {
        return this.checkDao.queryById(id);
    }

    @Override
    public IPage<Check> queryByPage(Check check, Integer pageNumber, Integer pageSize) {
        IPage<Check> page =  new Pagination<>(pageNumber, pageSize);
        return this.checkDao.selectCheck(page, check);
    }

    /**
     * 分页查询
     *
     * @param tbCheck 筛选条件
     * @return 查询结果
     */
    @Override
    public ArrayList<Check> queryByPage(Check tbCheck) {
        return this.checkDao.queryAllByLimit(tbCheck);
    }

    @Override
    public ArrayList<Check> queryByPage() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String firstDateTime = getAssignedDateFirstDayOfMonth(new Date())+" 00:00:00";
        Date firstDate = format.parse(firstDateTime);
        return this.checkDao.getMonthData(firstDate);
    }

    /**
     * 新增数据
     *
     * @param tbCheck 实例对象
     * @return 实例对象
     */
    @Override
    public Check insert(Check tbCheck) {
        tbCheck.setCheckId(UUIDUtil.getUniqueIdByUUId());
        int num = this.checkDao.insert(tbCheck);
        if(num == 0){
            return null;
        }
        return this.checkDao.queryAllByLimit(tbCheck).get(0);
    }

    /**
     * 修改数据
     *
     * @param check 实例对象
     * @return 实例对象
     */
    @Override
    public Check update(Check check) {
        User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
        check.setUpdateUser(loginUser);
        int num = this.checkDao.update(check);
        if(num == 0){
            return null;
        }
        return this.queryById(check.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.checkDao.deleteById(id) > 0;
    }
}
