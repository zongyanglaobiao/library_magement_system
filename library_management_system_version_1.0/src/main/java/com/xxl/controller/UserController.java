package com.xxl.controller;

import com.mysql.cj.Session;
import com.xxl.pojo.User;
import com.xxl.service.user.UserService;
import com.xxl.util.ConstantUtil;
import com.xxl.util.Jsonresult.JsonResultImpl;
import com.xxl.util.LogUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.*;

/**
 * @author: xxl
 * @date: 2023/2/19
 * @ProjectDescription: 登录，注册请求
 */

@RestController
@RequestMapping(value = "user", produces = "application/json;charset=utf-8")
public class UserController {
    private Logger log = LogUtil.getLogger(UserController.class);
    @Resource
    UserService userService;

    /**
     * 用户登录
     * @param phoneNumber 手机号码
     * @param password    密码
     * @return String
     * @author xxl
     */
    @GetMapping(value = "login")
    public String login(@RequestParam("phoneNumber") String phoneNumber,
                        @RequestParam("password") String password,
                        HttpServletRequest request, HttpServletResponse response) {
        List<User> users = userService.queryUser(phoneNumber, null, 0, null);
        if (users.size() == 0) {
            return JsonResultImpl.failResult("用户不存在");
        }
        //开始登录
        User user = userService.userLogin(phoneNumber, password);
        if (user != null) {
            //存取用户信息到session
            HttpSession session = request.getSession();
            log.info("控制器sessionId" + session.getId());

            //移除之前的session存的用户信息
            Object attribute = session.getAttribute(ConstantUtil.USER_INFO);
            if (attribute != null) {
                session.removeAttribute(ConstantUtil.USER_INFO);
            }
            session.setAttribute(ConstantUtil.USER_INFO, user);
            return JsonResultImpl.successResult("登录成功");
        }
        return JsonResultImpl.failResult("登录失败，请重新输入");
    }

    /**
     * 用户注册
     * @param phoneNumber
     * @param userPassword
     * @param userEmail
     * @return String
     * @author xxl
     */
    @GetMapping(value = "register")
    public String register(@RequestParam("phoneNumber") String phoneNumber,
                           @RequestParam("userPassword") String userPassword,
                           @RequestParam("userEmail") String userEmail, HttpSession session) {
        //等于空继续往下走
        String s = checkNumber(phoneNumber);
        if ( s !=null) {
            return s;
        }
        List<User> users = userService.queryUser(phoneNumber, null, 0, null);
        //如果查询的集合为0表示没有这个用户，可以注册
        if (users.size() == 0) {
            userService.addUser(new User(phoneNumber, userPassword, userEmail));
            return JsonResultImpl.successResult("注册成功");
        }
        return JsonResultImpl.failResult("注册失败，用户已存在");
    }

    /**
     * 返回用户信息
     * @return User
     * @author xxl
     */
    @GetMapping("info")
    public String returnUserInfo(HttpSession session) {
        HashMap<String, Object> map;
        try {
            User user = (User) session.getAttribute(ConstantUtil.USER_INFO);
            map = new HashMap<>();
            map.put("user", user);
        } catch (Exception e) {
            return JsonResultImpl.failResult("出现错误，原因：" + e);
        }
        return JsonResultImpl.successResult(map);
    }

    /**
     * 注销账户
     * @return String
     * @author xxl
     */
    @GetMapping("clear")
    public String clearUserInfo(HttpSession session) {
        session.removeAttribute(ConstantUtil.USER_INFO);
        return JsonResultImpl.successResult("注销账户成功");
    }

    /**
     * 修改用户
     * @param session
     * @param map
     * @return String
     * @author xxl
     */
    @PostMapping("modify")
    public String modifyUser(@RequestBody Map<String, Object> map, HttpSession session) {
        log.info("前端传递的参数=" + map);
        try {
            User oldUser = (User) session.getAttribute(ConstantUtil.USER_INFO);
            //主键不可修改，所以前端的参数一旦有主键提示错误
            if (map.containsKey(oldUser.getUserPhone())) {
                return JsonResultImpl.failResult("手机号码不可修改");
            }
            log.info("[modifyUser][未改变用户之前=" + oldUser + "]");
            //得到类实例
            Class<? extends User> aClass = oldUser.getClass();
            //判断前端传递的参数是否存在用户属性，存在就设置相对字段名的属性
            //注意的用户类的属性类型
            for (Field declaredField : aClass.getDeclaredFields()) {
                String fieldName = declaredField.getName();
                if (map.containsKey(fieldName)) {
                    declaredField.setAccessible(true);
                    declaredField.set(oldUser, map.get(fieldName));
                }
            }
            log.info("[modifyUser][改变用户之后=" + oldUser + "]");
            int i = userService.updateUserByUserPhone(oldUser, oldUser.getUserPhone());
            if (i > 0) {
                //修改成功移除session用户信息，重新登录
                session.removeAttribute(ConstantUtil.USER_INFO);
                return JsonResultImpl.successResult("修改成功");
            }
        } catch (IllegalAccessException e) {
            return JsonResultImpl.failResult("修改失败，具体原因" + e);

        }
        return JsonResultImpl.failResult("修改失败，原请求体：" + map);
    }


    /**
     * 需要权限，返回所有的用户信息
     * @param
     * @return String
     * @author xxl
     */
    @GetMapping("permissions/getAllUser")
    public String getAllUsersInfo() {
        //查询全部信息
        List<User> users = userService.queryUser(null, null, 0, null);
        try {
            if (users.size() != 0) {
                return JsonResultImpl.successResult(users);
            }
        } catch (Exception e) {
            //集合为空底层查询出现问题
            return JsonResultImpl.failResult("查询信息为空,具体原因；" + e);
        }
        //集合的大小为0
        return JsonResultImpl.failResult("查询信息为空");
    }
    /**
     * 需要权限，删除用户
     * @author xxl
     * @param  userPhone 用户手机号码
     * @return String
     */
    @RequestMapping(value = "permissions/deleteUser",method = RequestMethod.GET)
    public String deleteUserByPhoneNumber(@RequestParam("userPhone")String userPhone) {
        //等于空继续往下走，空表示通过验证
        String s = checkNumber(userPhone);
        if ( s !=null) {
            return s;
        }
        int i = userService.deleteUser(userPhone);
        if (i > 0) {
            return JsonResultImpl.successResult("删除成功");
        }
        return JsonResultImpl.failResult("删除失败，可能原因：没有此用户");
    }

    /**
     * 检查号码是否合格
     * @author xxl
     * @param
     * @return
     */
    private String checkNumber(String phoneNumber) {
        try {
            //查看数字是否合法
            new BigInteger(phoneNumber);
            int phoneNumberLength = 11;

            //检查数字是否为11位
            if (phoneNumber.length() != phoneNumberLength) {
                log.info(phoneNumber.length());
                return JsonResultImpl.failResult("注册失败,原因：手机号不正确");
            }else {
                //通过验证返回null
                return null;
            }
        } catch (NumberFormatException e) {
            log.error(e);
            return JsonResultImpl.failResult("注册失败,原因：手机号码格式不对");
        }
    }
}
