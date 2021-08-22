package io.kimmking.rpcfx.demo.consumer;

import org.springframework.stereotype.Service;

import io.kimmking.rpcfx.demo.api.User;
import io.kimmking.rpcfx.demo.api.UserService;
import io.kimmking.rpcfx.demo.consumer.aop.RpcAnno;

/**
 * @author heyu
 * @date 2021/8/22
 */
@Service
public class UserServiceProxyImpl implements UserService {

    @RpcAnno(url = "http://localhost:8080/")
    @Override
    public User findById(int id) {
        return new User();
    }
}
