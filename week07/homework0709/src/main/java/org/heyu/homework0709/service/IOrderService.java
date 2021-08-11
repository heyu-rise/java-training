package org.heyu.homework0709.service;

import org.heyu.homework0709.model.Order;

/**
 * @author heyu
 * @date 2021/8/10
 */
public interface IOrderService {

	/**
	 * 新增商品
	 * 
	 * @return number
	 */
	int addOrder();

	/**
	 * 获取商品
	 * 
	 * @param id
	 *            商品id
	 * @return 商品
	 */
	Order getOrder(long id);

}
