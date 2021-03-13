package hello;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 切面处理
 * 缓存热点数据
 */
@Aspect
@Configuration
public class CatchAspect {
    //内存缓存
    Map<String, Object> MemoryCatch = new HashMap<>();

    @Around("@annotation(hello.anno.Catch)")
    public Object Catch(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        //拿到调用的方法名与参数  进行反射
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        String methodName = signature.getName();

        //从缓存中拿到参数方法名 判断是否存在  存在则返回缓存中的  不存在则从缓存中创建 并返回
        Object catchValue = MemoryCatch.get(methodName);

        if (catchValue != null) {
            System.out.println("catch value !");
            return catchValue;
        } else {
            System.out.println("real value !");
            Object realValue = proceedingJoinPoint.proceed();
            MemoryCatch.put(methodName, realValue);
            return realValue;
        }



    }
}
