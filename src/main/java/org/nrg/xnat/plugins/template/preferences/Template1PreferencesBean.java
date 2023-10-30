/*
 * xnat-template1-plugin: org.nrg.xnat.plugins.template1.preferences.Template1PreferencesBean
 * XNAT https://www.xnat.org
 * Copyright (c) 2005-2021, Washington University School of Medicine
 * All Rights Reserved
 *
 * Released under the Simplified BSD.
 */

package org.nrg.xnat.plugins.template1.preferences;

import lombok.extern.slf4j.Slf4j;
import org.nrg.framework.configuration.ConfigPaths;
import org.nrg.framework.utilities.OrderedProperties;
import org.nrg.prefs.annotations.NrgPreference;
import org.nrg.prefs.annotations.NrgPreferenceBean;
import org.nrg.prefs.beans.AbstractPreferenceBean;
import org.nrg.prefs.exceptions.InvalidPreferenceName;
import org.nrg.prefs.services.NrgPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@NrgPreferenceBean(toolId = "template1", toolName = "XNAT Template1 Plugin")
@Slf4j
public class Template1PreferencesBean extends AbstractPreferenceBean {
    @Autowired
    public Template1PreferencesBean(final NrgPreferenceService preferenceService, final ConfigPaths configFolderPaths, final OrderedProperties orderedProperties) {
        super(preferenceService, configFolderPaths, orderedProperties);
    }

    @NrgPreference(defaultValue = "['Standard']")
    public List<String> getTemplate1Names() {
        return getListValue("template1Names");
    }

    @SuppressWarnings("unused")
    public void setTemplate1Names(final List<String> template1Names) {
        try {
            setListValue("template1Names", template1Names);
        } catch (InvalidPreferenceName invalidPreferenceName) {
            //
        }
    }

    @NrgPreference(defaultValue = "['standard']")
    public List<String> getTemplate1Types() {
        return getListValue("template1Types");
    }

    @SuppressWarnings("unused")
    public void setTemplate1Types(final List<String> template1Types) {
        try {
            setListValue("template1Types", template1Types);
        } catch (InvalidPreferenceName invalidPreferenceName) {
            //
        }
    }
}
