package org.sq.zbnss.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.sq.zbnss.entity.Appraisal;
import org.sq.zbnss.entity.Dic;
import org.sq.zbnss.entity.RecordSystem;
import org.sq.zbnss.dao.SystemDao;
import org.sq.zbnss.service.AppraisalService;
import org.sq.zbnss.service.SystemService;
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
 * 系统信息(TbSystem)表服务实现类
 *
 * @author makejava
 * @since 2023-03-06 20:14:28
 */
@Service("tbSystemService")
public class SystemServiceImpl implements SystemService {
    @Resource
    private SystemDao systemDao;

    @Resource
    private AppraisalService appraisalService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public RecordSystem queryById(Integer id) {
        return this.systemDao.queryById(id);
    }



    @Override
    public IPage<RecordSystem> queryByPage(RecordSystem company, Integer pageNumber, Integer pageSize) {
        IPage<RecordSystem> page =  new Pagination<>(pageNumber, pageSize);
        return systemDao.selectRecord(page, company);
    }

    /**
     * 分页查询
     *
     * @param recordSystem 筛选条件
     * @return 查询结果
     */
    @Override
    public ArrayList<RecordSystem> queryByPage(RecordSystem recordSystem) {
        return (ArrayList<RecordSystem>) this.systemDao.queryAllByLimit(recordSystem);
    }

    @Override
    public ArrayList<RecordSystem> getMonthDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String firstDateTime = getAssignedDateFirstDayOfMonth(new Date())+" 00:00:00";
        try {
            Date firstDate = format.parse(firstDateTime);
            return this.systemDao.getMonthData(firstDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ArrayList<RecordSystem>();
    }

    /**
     * 新增数据
     *
     * @param recordSystem 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(RecordSystem recordSystem) {
        recordSystem.setSystemId(UUIDUtil.getUniqueIdByUUId());
        Dic dic = new Dic();
        dic.setId(9);
        recordSystem.setTestStatus(dic);
        int result = this.systemDao.insert(recordSystem);
        if(result == 1){
            //新增测评信息
            RecordSystem recordSystem1 = this.systemDao.queryAllByLimit(recordSystem).get(0);
            Appraisal appraisal = new Appraisal();
            appraisal.setSystemId(recordSystem1.getId());
            appraisalService.insert(appraisal);
        }
        return result;
    }

    /**
     * 修改数据
     *
     * @param recordSystem 实例对象
     * @return 实例对象
     */
    @Override
    public RecordSystem update(RecordSystem recordSystem) {
        int num = this.systemDao.update(recordSystem);
        if(num == 0){
            return null;
        }
        return this.queryById(recordSystem.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.systemDao.deleteById(id) > 0;
    }
}
