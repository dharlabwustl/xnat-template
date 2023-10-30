/*
 * xnat-template1-plugin: org.nrg.xnat.plugins.template1.services.Template1Service
 * XNAT https://www.xnat.org
 * Copyright (c) 2005-2021, Washington University School of Medicine
 * All Rights Reserved
 *
 * Released under the Simplified BSD.
 */

package org.nrg.xnat.plugins.template1.services;

import org.nrg.framework.orm.hibernate.BaseHibernateService;
import org.nrg.xnat.plugins.template1.entities.Template1;

public interface Template1Service extends BaseHibernateService<Template1> {
    /**
     * Finds the template1 with the indicated {@link Template1#getTemplate1Id() template1 ID}.
     *
     * @param template1Id The template1 ID.
     *
     * @return The template1 with the indicated ID, null if not found.
     */
    Template1 findByTemplate1Id(final String template1Id);
}
