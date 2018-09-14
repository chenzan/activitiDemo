//package com.act.demo.config;
//
//import org.activiti.engine.*;
//import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
//import org.activiti.spring.ProcessEngineFactoryBean;
//import org.activiti.spring.SpringProcessEngineConfiguration;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
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
//@Configuration
//public class ActivitiConfig {
//    @Autowired
//    DataSource dataSource;
//
//    private static final String FONT_TYPE = "宋体";
//
//    @Bean
//    public ProcessEngineConfiguration processEngineConfiguration(PlatformTransactionManager platformTransactionManager) {
//        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
//        processEngineConfiguration.setDataSource(dataSource);
//        processEngineConfiguration.setTransactionManager(platformTransactionManager);
//        processEngineConfiguration.setActivityFontName(FONT_TYPE);
//        processEngineConfiguration.setLabelFontName(FONT_TYPE);
//        processEngineConfiguration.setAnnotationFontName(FONT_TYPE);
//        processEngineConfiguration.setDatabaseSchemaUpdate(SpringProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
//        processEngineConfiguration.setAsyncExecutorActivate(false);
//        return processEngineConfiguration;
//    }
//
//    @Bean
//    public ProcessEngineFactoryBean processEngineFactoryBean(ProcessEngineConfiguration processEngineConfiguration) {
//        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
//        processEngineFactoryBean.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);
//        return processEngineFactoryBean;
//    }
//
//
//    @Bean
//    public RepositoryService repositoryService(ProcessEngine processEngine) {
//        return processEngine.getRepositoryService();
//    }
//
//    @Bean
//    public RuntimeService runtimeService(ProcessEngine processEngine) {
//        return processEngine.getRuntimeService();
//    }
//
//    @Bean
//    public TaskService taskService(ProcessEngine processEngine) {
//        return processEngine.getTaskService();
//    }
//
//    @Bean
//    public HistoryService historyService(ProcessEngine processEngine) {
//        return processEngine.getHistoryService();
//    }
//
//    @Bean
//    public ManagementService managementService(ProcessEngine processEngine) {
//        return processEngine.getManagementService();
//    }
//
//    @Bean
//    public FormService formService(ProcessEngine processEngine) {
//        return processEngine.getFormService();
//    }
//
//    @Bean
//    public IdentityService identityService(ProcessEngine processEngine) {
//        return processEngine.getIdentityService();
//    }
//}
