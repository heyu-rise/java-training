package org.heyu.gateway.router;

import lombok.Data;

import java.util.List;

/**
 * @author heyu
 * @date 2021/7/8
 */
@Data
public class RouteDefinition {

    /**
     * 路由标识
     */
    private String id;

    /**
     * 转发url
     */
    private List<String> uri;

    /**
     * 匹配路径
     */
    private String path;

    /**
     * 路径修复
     */
    private Integer stripPrefix;

}
