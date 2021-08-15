package org.heyu.common.api.good;

import java.util.List;

import lombok.Data;

/**
 * @author heyu
 * @date 2021/8/15
 */
@Data
public class OrderGoodDTO {

	private Long orderId;

	private List<OrderGoodItemDto> goodList;

}
