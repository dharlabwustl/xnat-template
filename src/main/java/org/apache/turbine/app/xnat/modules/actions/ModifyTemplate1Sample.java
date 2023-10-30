/*
 * xnat-template-plugin: org.apache.turbine.app.xnat.modules.actions.ModifySampleTemplate1
 * XNAT http://www.xnat.org
 * Copyright (c) 2005-2021, Washington University School of Medicine
 * All Rights Reserved
 *
 * Released under the Simplified BSD.
 */

package org.apache.turbine.app.xnat.modules.actions;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.nrg.xdat.om.Template1Sample;
import org.nrg.xft.XFTItem;
import org.nrg.xnat.turbine.modules.actions.ModifySubjectAssessorData;

@SuppressWarnings("unused")
@Slf4j
public class ModifyTemplate1Sample extends ModifySubjectAssessorData {
    public void preProcess(final XFTItem item, final RunData data, final Context context) {
        final Template1Sample sample = new Template1Sample(item);
        final String existing = sample.getLabel();
        if (StringUtils.isBlank(existing)) {
            sample.setLabel("Template1_Sample_" + sample.getSubjectId());
        }
    }
}
