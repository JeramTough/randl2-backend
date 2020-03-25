package com.jeramtough.test.db;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.jeramtough.jtlog.facade.L;
import org.junit.Test;

/**
 * <pre>
 * Created on 2020/1/26 10:12
 * by @author JeramTough
 * </pre>
 */
public class CodeTest {

    @Test
    public void test1() {
        // 代码生成器
        AutoGenerator codeAutoGenerator = new AutoGenerator();

        GlobalConfig globalConfig = new GlobalConfig();
//        globalConfig.setOutputDir(System.getProperty("user.dir") + "/src/main/java");
        globalConfig.setOutputDir("E:\\Codes\\IdeaCodes\\randl2-backend\\webapp\\src\\test" +
                "\\code");
        globalConfig.setAuthor("JeramTough");
        globalConfig.setOpen(true);
        //实体属性 Swagger2 注解
        globalConfig.setSwagger2(true);
        globalConfig.setServiceName("%sService");
        System.out.println(globalConfig.getServiceName());
        codeAutoGenerator.setGlobalConfig(globalConfig);

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/randl2_db?useUnicode=true" +
                "&useSSL=false&characterEncoding=utf8");
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("123456");
        codeAutoGenerator.setDataSource(dataSourceConfig);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("randl2");
        pc.setParent("com.jeramtough");
        pc.setController("action.controller");
        pc.setMapper("dao.mapper");
        pc.setXml("dao.mapper.xml");
        pc.setEntity("dao.entity");
        codeAutoGenerator.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(false);
        strategy.setRestControllerStyle(true);
        strategy.setTablePrefix("_tb");
        codeAutoGenerator.setStrategy(strategy);

        codeAutoGenerator.execute();
    }


    @Test
    public void test3(){
        String a="safsafddddddddddddddddddddddddddd";
        L.debug(a.substring(0,a.length()<16?a.length():16));
    }
}
