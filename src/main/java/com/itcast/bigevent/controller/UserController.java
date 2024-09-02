package com.itcast.bigevent.controller;

import com.itcast.bigevent.pojo.Result;
import com.itcast.bigevent.pojo.User;
import com.itcast.bigevent.service.UserService;
import com.itcast.bigevent.utils.JwtUtil;
import com.itcast.bigevent.utils.MD5;
import com.itcast.bigevent.utils.ThreadLocalUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password){
        //检查username是否被占用
        List<User> user = userService.findUserByUsername(username);
        if(user.size() == 1){
            return Result.error("创建失败，用户名被占用。");
        } else {
            //如果不存在就为他注册
            System.out.println(password);
            Boolean ifCreateSuccess = userService.addUser(username, password);
            if(ifCreateSuccess){
                return Result.success();
            } else {
                return Result.error("创建失败，请稍后重试。");
            }
        }
    }

    /**
     * 用户登录成功后，系统会自动下发jwt令牌，然后在后续的请求中需要再请求头的header中的Authorization中携带jwt的值
     * 如果检测到未携带，则会返回401, jwt令牌本质上就是一个字符串，所以返回String
     * * 承载业务数据，减少后续请求查询数据库的次数
     * * 防篡改，保证信息的合法性和有效性
     * @param username 用户名
     * @param password 密码
     * @return jwt
     */
    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){
        String truePassword = userService.login(username, password);
        if(truePassword == null){
            return Result.error("用户名输入错误！");
        }
        String loginPassword = MD5.encrypByMd5(password);
        if(loginPassword.equals(truePassword)){
            Integer id = userService.finIdByUsername(username);
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("id",id);
            claims.put("username",username);
            String token = JwtUtil.genToken(claims);
            ValueOperations<String, String> op = stringRedisTemplate.opsForValue();
            op.set(token,token,1, TimeUnit.HOURS);
            return Result.success(token);
        } else {
            return Result.error("密码错误！");
        }
    }

    @GetMapping("/userInfo")
    public Result<User> getUserInfo(/*@RequestHeader(name = "Authorization") String token*/){
        //Map<String, Object> claims = JwtUtil.parseToken(token);
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer)map.get("id");
        User user = userService.findUserById(id);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer)map.get("id");
        user.setId(id);
        userService.updateUserInfoById(user);
        return Result.success();
    }

    //patch的目的是对资源数据打补丁或局部更新
    @PatchMapping("/updateAvatar")
    public Result updateAvatar (@RequestParam @URL String url){
        userService.updateAvatar(url);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> param, @RequestHeader("Authorization")String token){
        String oldPwd = param.get("oldPwd");
        String newPwd = param.get("newPwd");
        String rePwd = param.get("rePwd");

        if(!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("请输入有效长度的密码!");
        }

        if(!newPwd.equals(rePwd)){
            return Result.error("两次密码输入错误");
        }
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer)map.get("id");
        String pwd = userService.findPwdById(id);
        if(!MD5.encrypByMd5(oldPwd).equals(pwd)){
            return Result.error("原密码输入错误");
        }
        userService.updatePwd(newPwd,id);

        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.getOperations().delete(token);
        return Result.success("修改成功！");
    }

}
