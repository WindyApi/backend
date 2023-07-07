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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.whiteleaf03.api.modal.entity.User;
import top.whiteleaf03.api.util.RedisCache;
import top.whiteleaf03.api.util.ResponseResult;
import top.whiteleaf03.api.util.TokenUtils;

import javax.servlet.http.HttpServletRequest;

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
        if (StrUtil.isNotBlank(token)) {
            //获取到token，校验合法性
            String id = TokenUtils.parseToken(token);
            String validToken = redisCache.getCacheObject("[OnlineUserToken]" + id);
            if (validToken.equals(token)) {
                //token合法，获取用户
                User user = redisCache.getCacheObject("[OnlineUserInfo]" + id);
                if (ObjectUtil.isNotNull(user)) {
                    //获取到用户，鉴权
                    if (StrUtil.isNotBlank(tokenCheck.value())) {
                        //对身份有要求
                        if (tokenCheck.value().equals(user.getRole())) {
                            //是管理员，放行
                            return joinPoint.proceed();
                        }
                    } else {
                        //对身份无要求
                        return joinPoint.proceed();
                    }
                }
            }
        }
        return ResponseResult.authFailed("Auth Failed");
    }
}
