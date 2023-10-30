/*
 * xnat-template1-plugin: org.nrg.xnat.plugins.template1.services.impl.HibernateTemplate1Service
 * XNAT https://www.xnat.org
 * Copyright (c) 2005-2021, Washington University School of Medicine
 * All Rights Reserved
 *
 * Released under the Simplified BSD.
 */

package org.nrg.xnat.plugins.template1.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.nrg.framework.orm.hibernate.AbstractHibernateEntityService;
import org.nrg.xnat.plugins.template1.entities.Template1;
import org.nrg.xnat.plugins.template1.repositories.Template1Repository;
import org.nrg.xnat.plugins.template1.services.Template1Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Manages {@link Template1} data objects in Hibernate.
 */
@Service
@Slf4j
public class HibernateTemplate1Service extends AbstractHibernateEntityService<Template1, Template1Repository> implements Template1Service {
    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Template1 findByTemplate1Id(final String template1Id) {
        log.trace("Requested template1 with ID {}", template1Id);
        return getDao().findByUniqueProperty("template1Id", template1Id);
    }
}
