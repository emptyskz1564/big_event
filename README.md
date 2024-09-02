# big_event

## JWT

* 全程：JSON Web Token
* 定义了一种简洁的、自包含的格式，用于通信双方一json数据格式安全的传输信息。
* 组成：
  * 第一部分：Header(头)，记录令牌类型、签名算法等。
  * 第二部分：Payload(有效载荷)，携带一些自定义信息、默认信息等。如{"id":"1","username":"Tom"}
  * 第三部分：Signature(签名)，防止Token被篡改、确保安全性。将header、payload，并加入指定秘钥，通过指定签名算法计算而来。

![1724237219372](.\img\1724237219372.png)

**注意事项**

* JWT校验时使用的签名秘钥，必须和生成JWT令牌时使用的秘钥是配套的。
* 如果JWT令牌解析校验时报错，就说明JWT令牌被篡改或失效了，令牌非法。

## ThreadLocal

提供线程局部变量

* 用来存取数据：set()、get()
* 使用ThreadLocal存储的数据，线程安全

![1724247796066](.\img\1724247796066.png)

* 减少了参数的传递
* 同一个线程中可以共享数据

## 分组校验

把校验项进行归类分组，在完成不同的功能的时候，校验指定组中校验项

1. 定义分组
2. 定义校验项时指定归属的分组
3. 校验时指定要校验的分组

![1724409389466](.\img\1724409389466.png)

* **如果说某个校验项没有指定分组，则属于Default分组**
* **分组之间可以继承，A extends B，那么A中拥有B中所有的校验规则**

## 自定义校验

已有的注解不能满足所有的校验需求，特殊的情况需要自定义校验（自定义校验注解）

1. 自定义注解State
2. 自定义校验数据的类StateValidation，实现ConstrainValidator接口
3. 在需要校验的地方使用自定义注解

```java
@Target(ElementType.FIELD)
@Retention(RetentionPolicay)
@Constraint(validateBy = StateValidation.class)
public @interface State {
    String message() default "文章状态只能是：已发布或者草稿";
    
    class[] groups() default {};
    
    class<? extends Payload>[] payload() default{};
}
```

```java
public class StateValidation implements ConstrainValidator<State,String>{
    @Override
    public boolean isValid(Sting value, ConstraintValidatorContext context){
        if(value == null){
            return false;
        }
        
        if(value.queals("已发布") || value.queals("草稿")){
            return true;
        }
        
        return false;
    }
}
```

**或者将state本身设置为枚举类就行了**



## 使用Redis来让旧令牌主动失效

* 登录成功后，给浏览器相应令牌的同时，把该令牌存储到redis中
* `LoginInterceptor`拦截器中，需要验证浏览器携带的令牌，并同时需要获取到redis中存储的与之相同的令牌
* 当用户修改密码成功后，删除redis中存储的旧令牌



## 项目部署

1. 如何生成jar包？

   执行maven中的package命令

2. 如何运行jar包？

   Java -jar jar包位置

3. Jar包部署对服务器有什么要求？

   必须有jre环境



## 跨域

由于浏览器的同源策略限制，向不同源(不同协议，不同域名，不同端口)发送ajax请求会失败