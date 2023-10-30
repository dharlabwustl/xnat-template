/*
 * xnat-template1-plugin: org.nrg.xnat.plugins.template1.rest.Template1PrefsApi
 * XNAT https://www.xnat.org
 * Copyright (c) 2005-2021, Washington University School of Medicine
 * All Rights Reserved
 *
 * Released under the Simplified BSD.
 */

package org.nrg.xnat.plugins.template1.rest;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.nrg.framework.annotations.XapiRestController;
import org.nrg.framework.exceptions.NrgServiceException;
import org.nrg.prefs.exceptions.InvalidPreferenceName;
import org.nrg.xapi.exceptions.NotFoundException;
import org.nrg.xapi.rest.AbstractXapiRestController;
import org.nrg.xapi.rest.XapiRequestMapping;
import org.nrg.xdat.security.services.RoleHolder;
import org.nrg.xdat.security.services.UserManagementServiceI;
import org.nrg.xnat.plugins.template1.preferences.Template1PreferencesBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

import static org.nrg.framework.exceptions.NrgServiceError.ConfigurationError;
import static org.nrg.xdat.security.helpers.AccessLevel.Authenticated;

@Api("Template1 Preferences API")
@XapiRestController
@RequestMapping(value = "/template1/prefs")
@Slf4j
public class Template1PrefsApi extends AbstractXapiRestController {
    @Autowired
    public Template1PrefsApi(final UserManagementServiceI userManagementService, final RoleHolder roleHolder, final Template1PreferencesBean template1Prefs) {
        super(userManagementService, roleHolder);
        _template1Prefs = template1Prefs;
    }

    @ApiOperation(value = "Returns the full map of template1 preferences.", response = String.class, responseContainer = "Map")
    @ApiResponses({@ApiResponse(code = 200, message = "Site configuration properties successfully retrieved."),
                   @ApiResponse(code = 401, message = "Must be authenticated to access the XNAT REST API."),
                   @ApiResponse(code = 403, message = "Not authorized to set site configuration properties."),
                   @ApiResponse(code = 500, message = "Unexpected error")})
    @XapiRequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET, restrictTo = Authenticated)
    public Map<String, Object> getTemplate1Preferences() {
        return _template1Prefs;
    }

    @ApiOperation(value = "Returns the value of the specified template1 preference.", response = String.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Template1 preference value successfully retrieved."),
                   @ApiResponse(code = 401, message = "Must be authenticated to access the XNAT REST API."),
                   @ApiResponse(code = 403, message = "Not authorized to access template1 preferences."),
                   @ApiResponse(code = 500, message = "Unexpected error")})
    @XapiRequestMapping(value = "{preference}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET, restrictTo = Authenticated)
    public String getPreferenceValue(@ApiParam(value = "The template1 preference to retrieve.", required = true) @PathVariable final String preference) throws NotFoundException {
        if (!_template1Prefs.containsKey(preference)) {
            throw new NotFoundException("No preference named \"" + preference + "\" was found.");
        }
        return _template1Prefs.getValue(preference);
    }

    @ApiOperation(value = "Updates the value of the specified template1 preference.", response = String.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Template1 preference value successfully retrieved."),
                   @ApiResponse(code = 401, message = "Must be authenticated to access the XNAT REST API."),
                   @ApiResponse(code = 403, message = "Not authorized to access template1 preferences."),
                   @ApiResponse(code = 500, message = "Unexpected error")})
    @XapiRequestMapping(value = "{preference}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT, restrictTo = Authenticated)
    public String setPreferenceValue(@ApiParam(value = "The template1 preference to set.", required = true) @PathVariable final String preference,
                                     @ApiParam(value = "The template1 preference to set.", required = true) @RequestBody final String value) throws NotFoundException, NrgServiceException {
        if (!_template1Prefs.containsKey(preference)) {
            throw new NotFoundException("No preference named \"" + preference + "\" was found.");
        }
        try {
            return _template1Prefs.set(value, preference);
        } catch (InvalidPreferenceName invalidPreferenceName) {
            throw new NrgServiceException(ConfigurationError, "An error occurred trying to set the \"" + preference + "\" template1 preference to the value: " + value);
        }
    }

    private final Template1PreferencesBean _template1Prefs;
}
