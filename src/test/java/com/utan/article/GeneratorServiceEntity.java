package com.utan.article;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

public class GeneratorServiceEntity {
        @Test
        public void generateCode() {
            String packageName = "com.utan.article";
            boolean serviceNameStartWithI = false;//user -> UserService, 设置成true: user -> IUserService
            generateByTables(serviceNameStartWithI, packageName, "tbl_toutiao_user_info");
        }

        private void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
            GlobalConfig config = new GlobalConfig();
            String dbUrl = "jdbc:mysql://192.168.1.110:3306/db2_utan_toutiao";
            DataSourceConfig dataSourceConfig = new DataSourceConfig();
            dataSourceConfig.setDbType(DbType.MYSQL)
                    .setUrl(dbUrl)
                    .setUsername("root")
                    .setPassword("123")
                    .setDriverName("com.mysql.jdbc.Driver");
            StrategyConfig strategyConfig = new StrategyConfig();
            strategyConfig
                    .setCapitalMode(true)
                    .setCapitalMode(true)
                    .setEntityLombokModel(true)
                    .setDbColumnUnderline(true)
                    .setNaming(NamingStrategy.underline_to_camel)
                    .setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
            config.setActiveRecord(false)
                    .setAuthor("Ethan")
                    .setOutputDir("D:\\workspace\\article\\src\\main\\java")
                    .setFileOverride(true);
            if (!serviceNameStartWithI) {
                config.setServiceName("%sService");
            }

            TemplateConfig templateConfig = new TemplateConfig()
                    .setEntity("/templates/entity.java");//注意：不要带上.vm

            new AutoGenerator().setGlobalConfig(config)
                    .setTemplate(templateConfig)
                    .setDataSource(dataSourceConfig)
                    .setStrategy(strategyConfig)
                    .setPackageInfo(
                            new PackageConfig()
                                    .setParent(packageName)
                                    .setController("controller")
                                    .setEntity("entity")
                    ).execute();
        }

        private void generateByTables(String packageName, String... tableNames) {
            generateByTables(true, packageName, tableNames);
        }
}
