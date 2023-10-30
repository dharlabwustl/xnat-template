/*
 * xnat-template1-plugin: org.nrg.xnat.plugins.template1.entities.Template1
 * XNAT https://www.xnat.org
 * Copyright (c) 2005-2021, Washington University School of Medicine
 * All Rights Reserved
 *
 * Released under the Simplified BSD.
 */

package org.nrg.xnat.plugins.template1.entities;

import lombok.extern.slf4j.Slf4j;
import org.nrg.framework.orm.hibernate.AbstractHibernateEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "template1Id"))
@Slf4j
public class Template1 extends AbstractHibernateEntity {
    private String _template1Id;

    public String getTemplate1Id() {
        return _template1Id;
    }

    public void setTemplate1Id(final String template1Id) {
        _template1Id = template1Id;
    }
}
