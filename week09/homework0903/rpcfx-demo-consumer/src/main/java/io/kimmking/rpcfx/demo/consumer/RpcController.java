package io.kimmking.rpcfx.demo.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kimmking.rpcfx.demo.api.User;
import io.kimmking.rpcfx.demo.api.UserService;

/**
 * @author heyu
 * @date 2021/8/22
 */
@RestController
@RequestMapping("/rpc")
public class RpcController {

    @Autowired
    private UserService userService;

    @GetMapping("/1")
    public User test1(){
        return userService.findById(1);
    }

}
