package org.flowable;

import org.flowable.form.api.*;
import org.flowable.form.engine.FormEngine;
import org.flowable.form.engine.FormEngineConfiguration;
import org.flowable.form.engine.impl.cfg.StandaloneFormEngineConfiguration;

import java.util.HashMap;
import java.util.Map;

public class HolidayForm {

    public static void main(String[] args) {
        FormEngineConfiguration cfg = FormEngineConfiguration.createStandaloneFormEngineConfiguration();
//        cfg.setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1");
//        cfg.setJdbcUsername("sa");
//        cfg.setJdbcPassword("");
//        cfg.setJdbcDriver("org.h2.Driver");
        cfg.setJdbcUrl("jdbc:mysql://localhost:3306/flowable660?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=TRUE");
        cfg.setJdbcUsername("root");
        cfg.setJdbcPassword("123456");
        cfg.setJdbcDriver("com.mysql.jdbc.Driver");
        //cfg.setDatabaseSchemaUpdate(FormEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        FormEngine formEngine = cfg.buildFormEngine();
        FormRepositoryService formRepositoryService = formEngine.getFormRepositoryService();
        FormDeployment deployment = formRepositoryService.createDeployment().name("HelloWorld").addClasspathResource("form-HelloWorldForm.form").deploy();

        FormDefinition formDefinition = formRepositoryService.createFormDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        String key = formDefinition.getKey();
        System.out.println("Form definition key: " + key);

        FormService formService = formEngine.getFormService();
        FormInfo formInfo = formService.getFormModelWithVariablesById(formDefinition.getId(), null, null);
        String formInfoId = formInfo.getId();
        System.out.println("Form info Id: " + formInfoId);

        Map variables = new HashMap();
        variables.put("name","swang");
        variables.put("age",1);
        FormInstance formInstance = formService.createFormInstance(variables, formInfo, null, null, null, null, null);
        System.out.println("Form instance id: "+formInstance.getId());

        FormInstanceInfo formInstanceInfo = formService.getFormInstanceModelById(formInstance.getId(), null);
        FormModel formModel = formInstanceInfo.getFormModel();
        System.out.println(formModel.getClass());


    }
}
