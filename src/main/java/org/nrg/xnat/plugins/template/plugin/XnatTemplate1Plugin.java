/*
 * xnat-template1-plugin: org.nrg.xnat.plugins.template1.plugin.XnatTemplate1Plugin
 * XNAT https://www.xnat.org
 * Copyright (c) 2005-2021, Washington University School of Medicine
 * All Rights Reserved
 *
 * Released under the Simplified BSD.
 */

package org.nrg.xnat.plugins.template1.plugin;

import lombok.extern.slf4j.Slf4j;
import org.dcm4che2.data.Tag;
import org.nrg.config.services.ConfigService;
import org.nrg.framework.annotations.XnatDataModel;
import org.nrg.framework.annotations.XnatPlugin;
import org.nrg.xdat.bean.Template1SampleBean;
import org.nrg.xdat.security.user.XnatUserProvider;
import org.nrg.xnat.plugins.template1.dcm.ConfigurableMappedAttributeExtractor;
import org.nrg.xnat.plugins.template1.dcm.ConfigurableMappedDicomObjectIdentifier;
import org.nrg.xnat.plugins.template1.dcm.ConfigurableMappedNumberExtractor;
import org.nrg.xnat.plugins.template1.dcm.ConfigurableMappedProjectIdentifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@XnatPlugin(value = "template1Plugin", name = "XNAT Template1 Plugin",
            entityPackages = "org.nrg.xnat.plugins.template1.entities",
            dataModels = {@XnatDataModel(value = Template1SampleBean.SCHEMA_ELEMENT_NAME,
                                         singular = "Template1",
                                         plural = "Template1s",
                                         code = "TM")})
@ComponentScan({"org.nrg.xnat.plugins.template1.preferences",
                "org.nrg.xnat.plugins.template1.repositories",
                "org.nrg.xnat.plugins.template1.rest",
                "org.nrg.xnat.plugins.template1.services.impl"})
@Slf4j
public class XnatTemplate1Plugin {
    public XnatTemplate1Plugin() {
        log.info("Creating the XnatTemplate1Plugin configuration class");
    }

    @Bean
    public String template1PluginMessage() {
        return "This comes from deep within the template1 plugin.";
    }

    /**
     * Extracts the project's key number from the specified DICOM tag using the specified regex.
     *
     * @return An extractor that gets numbers from the study description.
     */
    @Bean
    public ConfigurableMappedNumberExtractor mappedNumberExtractor() {
        return new ConfigurableMappedNumberExtractor(Tag.StudyDescription, ".*\\^([A-Za-z0-9-_]+).*", 1);
    }

    /**
     * Builds the subject label by extracting the contents of StudyDescription between the ^ and the first space.
     *
     * @return An extractor that gets the subject label.
     */
    @Bean
    public ConfigurableMappedAttributeExtractor mappedSubjectExtractor() {
        return new ConfigurableMappedAttributeExtractor(0, mappedNumberExtractor());
    }

    /**
     * Gets the study date.
     *
     * @return An extractor to get the study date.
     */
    @Bean
    public ConfigurableMappedAttributeExtractor mappedStudyDateExtractor() {
        return new ConfigurableMappedAttributeExtractor(Tag.StudyDate);
    }

    /**
     * Builds the subject label by concatenating study date and time.
     *
     * @return An extractor that gets the session label.
     */
    @Bean
    public ConfigurableMappedAttributeExtractor mappedSessionExtractor() {
        return new ConfigurableMappedAttributeExtractor(Tag.StudyTime, mappedStudyDateExtractor());
    }

    /**
     * Determines the project by extracting the contents from the specified DICOM tag.
     *
     * @return The project identifier.
     */
    @Bean
    public ConfigurableMappedProjectIdentifier mappedProjectIdentifier(final ConfigService configService) {
        return new ConfigurableMappedProjectIdentifier(configService, Tag.StationName);
    }

    /**
     * Creates the DICOM object identifier.
     *
     * @param receivedFileUserProvider The user provider for writing files.
     *
     * @return The DICOM object identifier.
     */
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Bean
    public ConfigurableMappedDicomObjectIdentifier mappedObjectIdentifier(final XnatUserProvider receivedFileUserProvider, final ConfigurableMappedProjectIdentifier mappedProjectIdentifier) {
        final ConfigurableMappedDicomObjectIdentifier identifier = new ConfigurableMappedDicomObjectIdentifier(mappedProjectIdentifier, mappedSubjectExtractor(), mappedSessionExtractor());
        identifier.setUserProvider(receivedFileUserProvider);
        return identifier;
    }
}
