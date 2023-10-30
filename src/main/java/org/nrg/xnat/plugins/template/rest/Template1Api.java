/*
 * xnat-template1-plugin: org.nrg.xnat.plugins.template1.rest.Template1Api
 * XNAT https://www.xnat.org
 * Copyright (c) 2005-2021, Washington University School of Medicine
 * All Rights Reserved
 *
 * Released under the Simplified BSD.
 */

package org.nrg.xnat.plugins.template1.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.nrg.framework.annotations.XapiRestController;
import org.nrg.xapi.exceptions.NotFoundException;
import org.nrg.xapi.rest.AbstractXapiRestController;
import org.nrg.xapi.rest.XapiRequestMapping;
import org.nrg.xdat.security.services.RoleHolder;
import org.nrg.xdat.security.services.UserManagementServiceI;
import org.nrg.xnat.plugins.template1.entities.Template1;
import org.nrg.xnat.plugins.template1.services.Template1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Api("Template1 API")
@XapiRestController
@RequestMapping(value = "/template1/entities")
@Slf4j
public class Template1Api extends AbstractXapiRestController {
    @Autowired
    protected Template1Api(final UserManagementServiceI userManagementService, final RoleHolder roleHolder, final Template1Service template1Service) {
        super(userManagementService, roleHolder);
        _template1Service = template1Service;
    }

    @ApiOperation(value = "Returns a list of all template1s.", response = Template1.class, responseContainer = "List")
    @ApiResponses({@ApiResponse(code = 200, message = "Template1s successfully retrieved."),
                   @ApiResponse(code = 401, message = "Must be authenticated to access the XNAT REST API."),
                   @ApiResponse(code = 500, message = "Unexpected error")})
    @XapiRequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public List<Template1> getEntities() {
        return _template1Service.getAll();
    }

    @ApiOperation(value = "Creates a new template1.", response = Template1.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Template1 successfully created."),
                   @ApiResponse(code = 401, message = "Must be authenticated to access the XNAT REST API."),
                   @ApiResponse(code = 500, message = "Unexpected error")})
    @XapiRequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Template1 createEntity(@RequestBody final Template1 entity) {
        return _template1Service.create(entity);
    }

    @ApiOperation(value = "Retrieves the indicated template1.",
                  notes = "Based on the template1 ID, not the primary key ID.",
                  response = Template1.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Template1 successfully retrieved."),
                   @ApiResponse(code = 401, message = "Must be authenticated to access the XNAT REST API."),
                   @ApiResponse(code = 500, message = "Unexpected error")})
    @XapiRequestMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Template1 getEntity(@PathVariable final String id) throws NotFoundException {
        if (!_template1Service.exists("template1Id", id)) {
            throw new NotFoundException("No template1 with the ID \"" + id + "\" was found.");
        }
        return _template1Service.findByTemplate1Id(id);
    }

    @ApiOperation(value = "Updates the indicated template1.",
                  notes = "Based on primary key ID, not subject or record ID.",
                  response = Long.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Template1 successfully updated."),
                   @ApiResponse(code = 401, message = "Must be authenticated to access the XNAT REST API."),
                   @ApiResponse(code = 500, message = "Unexpected error")})
    @XapiRequestMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public long updateEntity(@PathVariable final Long id, @RequestBody final Template1 entity) throws NotFoundException {
        if (!_template1Service.exists("template1Id", id)) {
            throw new NotFoundException("No template1 with the ID \"" + id + "\" was found.");
        }
        final Template1 existing = _template1Service.retrieve(id);
        existing.setTemplate1Id(entity.getTemplate1Id());
        _template1Service.update(existing);
        return id;
    }

    @ApiOperation(value = "Deletes the indicated template1.",
                  notes = "Based on primary key ID, not subject or record ID.",
                  response = Long.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Template1 successfully deleted."),
                   @ApiResponse(code = 401, message = "Must be authenticated to access the XNAT REST API."),
                   @ApiResponse(code = 500, message = "Unexpected error")})
    @XapiRequestMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public long deleteEntity(@PathVariable final Long id) {
        final Template1 existing = _template1Service.retrieve(id);
        _template1Service.delete(existing);
        return id;
    }

    private final Template1Service _template1Service;
}
