package com.jeramtough.randl2.config.db;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *     配置业务类的事务管理
 * Created on 2020-08-02 19:59
 * by @author JeramTough
 * </pre>
 */
@Aspect
@Configuration
public class DbTransactionConfig {

    private static final String AOP_POINTCUT_EXPRESSION =
            "execution ( * com.jeramtough.randl2.service.*.*(..))";

    private final PlatformTransactionManager transactionManager;

    @Autowired
    public DbTransactionConfig(
            PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Bean
    public TransactionInterceptor txAdvice() {
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

        ///只读事务，不做更新操作/
        RuleBasedTransactionAttribute readOnlyTransactionAttribute = new RuleBasedTransactionAttribute();
        readOnlyTransactionAttribute.setReadOnly(true);
        readOnlyTransactionAttribute.setPropagationBehavior(
                TransactionDefinition.PROPAGATION_NOT_SUPPORTED);

        //当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务/
        RuleBasedTransactionAttribute operatedTransactionAttribute = new RuleBasedTransactionAttribute();
        operatedTransactionAttribute.setRollbackRules(
                Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        operatedTransactionAttribute.setPropagationBehavior(
                TransactionDefinition.PROPAGATION_REQUIRED);

        Map<String, TransactionAttribute> txMap = new HashMap<>(16);

        ///配置事务方法的前缀/
        txMap.put("add*", operatedTransactionAttribute);
        txMap.put("save*", operatedTransactionAttribute);
        txMap.put("insert*", operatedTransactionAttribute);
        txMap.put("create*", operatedTransactionAttribute);
        txMap.put("batch*", operatedTransactionAttribute);
        txMap.put("update*", operatedTransactionAttribute);
        txMap.put("modify*", operatedTransactionAttribute);
        txMap.put("delete*", operatedTransactionAttribute);

        ///配置只读事务方法的前缀/
        txMap.put("get*", readOnlyTransactionAttribute);
        txMap.put("query*", readOnlyTransactionAttribute);
        txMap.put("find*", readOnlyTransactionAttribute);
        txMap.put("select*", readOnlyTransactionAttribute);

        ///其余方法添加事务/
        //txMap.put("*", operatedTransactionAttribute);
        source.setNameMap(txMap);

        return new TransactionInterceptor(transactionManager, source);
    }

    /**
     * 注入切面Advisor去开启事务
     */
    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}
