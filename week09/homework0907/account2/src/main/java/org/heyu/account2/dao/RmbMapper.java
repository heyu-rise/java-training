package org.heyu.account2.dao;

import org.heyu.account2.model.Rmb;

public interface RmbMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Rmb record);

    int insertSelective(Rmb record);

    Rmb selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Rmb record);

    int updateByPrimaryKey(Rmb record);
}