package org.heyu.common.api.good;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * @author heyu
 * @date 2021/8/15
 */
@Data
public class OrderGoodItemDto {

    private Long goodId;

    private BigDecimal quantity;

    private Date createTime;

    private Date updateTime;

}
