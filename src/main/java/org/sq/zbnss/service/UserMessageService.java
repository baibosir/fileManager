package org.sq.zbnss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.sq.zbnss.entity.User;
import org.sq.zbnss.entity.UserMessage;

import java.util.ArrayList;

public interface UserMessageService extends IService<UserMessage> {

    int register(UserMessage userMessage);

    ArrayList<UserMessage> getMessageList();

    UserMessage  getMessageList(int id);
}
