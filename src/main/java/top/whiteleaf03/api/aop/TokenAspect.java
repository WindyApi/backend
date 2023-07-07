package top.whiteleaf03.api.aop;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.whiteleaf03.api.modal.entity.User;
import top.whiteleaf03.api.util.RedisCache;
import top.whiteleaf03.api.util.ResponseResult;
import top.whiteleaf03.api.util.TokenUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author WhiteLeaf03
 */
@Slf4j
@Aspect
@Component
public class TokenAspect {
    private final RedisCache redisCache;

    @Autowired
    public TokenAspect(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Pointcut("@annotation(top.whiteleaf03.api.aop.TokenCheck)")
    public void tokenRequiredMethods() {}

    @Around("tokenRequiredMethods() && @annotation(tokenCheck)")
    public Object checkToken(ProceedingJoinPoint joinPoint, TokenCheck tokenCheck) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");

        //token判空
        if (StrUtil.isBlank(token)) {
            return ResponseResult.authFailed("token为空");
        }

        //获取id
        String id;
        try {
            id = TokenUtils.parseToken(token);
        } catch (RuntimeException e) {
            return ResponseResult.authFailed("token非法");
        }

        //判断是否过期
        String validToken = redisCache.getCacheObject("[OnlineUserToken]" + id);
        if (StrUtil.isBlank(validToken) || !validToken.equals(token)) {
            return ResponseResult.authFailed("token已过期");
        }

        //判断用户是否存在
        User user = redisCache.getCacheObject("[OnlineUserInfo]" + id);
        if (ObjectUtil.isNull(user)) {
            return ResponseResult.authFailed("用户不存在");
        }

        //鉴权
        if (StrUtil.isNotBlank(tokenCheck.value()) && !tokenCheck.value().equals(user.getRole())) {
            return ResponseResult.authFailed("无权限访问");
        }

        //检验通过 将用户信息存入上下文
        RequestContextHolder.getRequestAttributes().setAttribute("UserInfo", user, RequestAttributes.SCOPE_REQUEST);

        return joinPoint.proceed();
    }
}
