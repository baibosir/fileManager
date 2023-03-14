package org.sq.zbnss.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.sq.zbnss.entity.Company;
import org.sq.zbnss.entity.User;

/**
 * 单位信息(TbCompany)表服务接口
 *
 * @author makejava
 * @since 2023-03-06 20:14:20
 */
public interface CompanyService extends IService<Company> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Company queryById(Integer id);


    /**
     * 分页查询
     *
     * @param company 筛选条件
     * @return 查询结果
     */
    IPage<Company> queryByPage(Company company, Integer pageNumber, Integer pageSize);

    Company queryAllByLimit(Company company);

    /**
     * 新增数据
     *
     * @param company 实例对象
     * @return 实例对象
     */
    int insert(Company company);

    /**
     * 修改数据
     *
     * @param company 实例对象
     * @return 实例对象
     */
    Company update(Company company);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
