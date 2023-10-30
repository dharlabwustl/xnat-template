/*
 * xnat-template1-plugin: org.nrg.xnat.plugins.template1.tasks.Template1Task
 * XNAT https://www.xnat.org
 * Copyright (c) 2005-2021, Washington University School of Medicine
 * All Rights Reserved
 *
 * Released under the Simplified BSD.
 */

package org.nrg.xnat.plugins.template1.tasks;

import lombok.extern.slf4j.Slf4j;
import org.nrg.framework.task.XnatTask;
import org.nrg.framework.task.services.XnatTaskService;
import org.nrg.xnat.services.XnatAppInfo;
import org.nrg.xnat.task.AbstractXnatTask;
import org.springframework.jdbc.core.JdbcTemplate;

@XnatTask(taskId = "Template1Task", description = "Template1 Task", defaultExecutionResolver = "SingleNodeExecutionResolver", executionResolverConfigurable = true)
@Slf4j
public class Template1Task extends AbstractXnatTask {
    public Template1Task(final XnatTaskService taskService, final XnatAppInfo appInfo, final JdbcTemplate jdbcTemplate) {
        super(taskService, true, appInfo, jdbcTemplate);
    }

    @Override
    protected void runTask() {
        log.info("Now running the template1 task");
    }
}
