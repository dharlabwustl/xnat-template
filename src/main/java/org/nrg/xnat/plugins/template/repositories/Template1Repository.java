/*
 * xnat-template1-plugin: org.nrg.xnat.plugins.template1.repositories.Template1Repository
 * XNAT https://www.xnat.org
 * Copyright (c) 2005-2021, Washington University School of Medicine
 * All Rights Reserved
 *
 * Released under the Simplified BSD.
 */

package org.nrg.xnat.plugins.template1.repositories;

import lombok.extern.slf4j.Slf4j;
import org.nrg.framework.orm.hibernate.AbstractHibernateDAO;
import org.nrg.xnat.plugins.template1.entities.Template1;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class Template1Repository extends AbstractHibernateDAO<Template1> {
}
