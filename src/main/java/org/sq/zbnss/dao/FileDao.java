package org.sq.zbnss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.sq.zbnss.entity.FilePo;

import java.util.ArrayList;

@Mapper
public interface FileDao extends BaseMapper<FilePo> {

    FilePo queryById(int id);
    ArrayList<FilePo> selectAttachment(FilePo filePo);
    int insert(FilePo filePo);
    int deleteById(int id);
}
