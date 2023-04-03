package org.sq.zbnss.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.sq.zbnss.dao.MessageDao;
import org.sq.zbnss.entity.UserMessage;
import org.sq.zbnss.service.UserMessageService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class UserMessageServiceImpl implements UserMessageService {

    private MessageDao messageDao;

    @Override
    public boolean saveBatch(Collection<UserMessage> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<UserMessage> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<UserMessage> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(UserMessage entity) {
        return false;
    }

    @Override
    public UserMessage getOne(Wrapper<UserMessage> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<UserMessage> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<UserMessage> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public BaseMapper<UserMessage> getBaseMapper() {
        return null;
    }

    @Override
    public Class<UserMessage> getEntityClass() {
        return null;
    }

    @Override
    public int register(UserMessage userMessage) {
        return messageDao.insert(userMessage);
    }

    @Override
    public ArrayList<UserMessage>  getMessageList(){
        QueryWrapper<UserMessage> wrapper = new QueryWrapper<>();
        wrapper.eq("status",1);
        return (ArrayList<UserMessage>) messageDao.selectList(wrapper);
    }

    @Override
    public UserMessage  getMessageList(int id){
        QueryWrapper<UserMessage> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        return  messageDao.selectOne(wrapper);
    }
}
