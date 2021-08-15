package org.heyu.homework0802.service;

import org.heyu.homework0802.model.Order;

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

	/**
	 * 修改订单
	 * @return
	 */
	int modifyOrder();

	/**
	 * 删除订单
	 * @return
	 */
	int deleteOrder();

}
