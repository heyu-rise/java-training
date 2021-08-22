package org.heyu.account2.dao;

import org.heyu.account2.model.Freeze;

public interface FreezeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Freeze record);

    int insertSelective(Freeze record);

    Freeze selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Freeze record);

    int updateByPrimaryKey(Freeze record);
}