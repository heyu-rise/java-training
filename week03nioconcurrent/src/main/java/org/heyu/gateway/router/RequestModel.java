package org.heyu.gateway.router;

import lombok.Data;

/**
 * @author heyu
 * @date 2021/7/10
 */
@Data
public class RequestModel {

    private String host;

    private int port;

    private String uri;

}
