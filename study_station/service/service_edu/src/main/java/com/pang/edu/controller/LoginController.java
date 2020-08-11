package com.pang.edu.controller;

import com.pang.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/eduUser/user")
@CrossOrigin //解决跨域
public class LoginController {

    @PostMapping(value = "/login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    @GetMapping(value = "/info")
    public R info() {
        return R.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

}
