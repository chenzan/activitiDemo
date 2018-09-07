//package com.act.demo.config;
//
//import org.activiti.engine.*;
//import org.activiti.spring.ProcessEngineFactoryBean;
//import org.activiti.spring.SpringProcessEngineConfiguration;
//import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
///**
// * @author chenzan
// * @version V1.0
// * @description TODO
// * @create-date 2018/8/24
// * @modifier
// * @modifier-data
// */
////@Configuration
//public class ActivitiConfig extends AbstractProcessEngineAutoConfiguration {
//    @Resource
//    DataSource dataSource;
//    @Resource
//    PlatformTransactionManager transactionManager;
//
//    private static final String FONT_TYPE = "Microsoft Yahei";
//
//    @Bean
//    public ProcessEngineConfiguration processEngineConfiguration() {
//        ProcessEngineConfiguration springProcessEngineConfiguration = new ProcessEngineConfiguration();
//        springProcessEngineConfiguration.setDataSource(dataSource);
////        springProcessEngineConfiguration.setTransactionManager(transactionManager);
//        springProcessEngineConfiguration.setActivityFontName(FONT_TYPE);
//        springProcessEngineConfiguration.setLabelFontName(FONT_TYPE);
//        springProcessEngineConfiguration.setDatabaseSchemaUpdate(SpringProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
//        springProcessEngineConfiguration.setAsyncExecutorActivate(false);
//        return this.baseSpringProcessEngineConfiguration(dataSource,transactionManager,);
//    }
//
//    @Bean
//    public ProcessEngine processEngine() {
//        return processEngineConfiguration();
//    }
//
//    @Bean
//    public RepositoryService repositoryService() {
//        return processEngine().getRepositoryService();
//    }
//
//    @Bean
//    public RuntimeService runtimeService() {
//        return processEngine().getRuntimeService();
//    }
//
//    @Bean
//    public TaskService taskService() {
//        return processEngine().getTaskService();
//    }
//
//    @Bean
//    public HistoryService historyService() {
//        return processEngine().getHistoryService();
//    }
//
//    @Bean
//    public ManagementService managementService() {
//        return processEngine().getManagementService();
//    }
//
//    @Bean
//    public FormService formService() {
//        return processEngine().getFormService();
//    }
//
//    @Bean
//    public IdentityService identityService() {
//        return processEngine().getIdentityService();
//    }
//}
