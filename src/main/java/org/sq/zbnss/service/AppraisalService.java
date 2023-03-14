package org.sq.zbnss.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.sq.zbnss.entity.Appraisal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;

/**
 * 测评信息(TbAppraisal)表服务接口
 *
 * @author makejava
 * @since 2023-03-06 20:14:12
 */
public interface AppraisalService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Appraisal queryById(Integer id);

    IPage<Appraisal> queryByPage(Appraisal company, Integer pageNumber, Integer pageSize);

    /**
     * 分页查询
     *
     * @param appraisal 筛选条件
     * @return 查询结果
     */
    ArrayList<Appraisal> queryByPage(Appraisal appraisal);

    /**
     * 新增数据
     *
     * @param appraisal 实例对象
     * @return 实例对象
     */
    Appraisal insert(Appraisal appraisal);

    /**
     * 修改数据
     *
     * @param appraisal 实例对象
     * @return 实例对象
     */
    Appraisal update(Appraisal appraisal);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
