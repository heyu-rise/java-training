package org.heyu.account1.dao;

import org.heyu.account1.model.Dollar;

public interface DollarMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Dollar record);

    int insertSelective(Dollar record);

    Dollar selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Dollar record);

    int updateByPrimaryKey(Dollar record);
}