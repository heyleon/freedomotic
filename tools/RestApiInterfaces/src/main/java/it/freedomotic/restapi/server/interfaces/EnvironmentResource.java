/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.freedomotic.restapi.server.interfaces;

import it.freedomotic.model.environment.Environment;

import org.restlet.resource.Get;

/**
 *
 * @author gpt
 */
public interface EnvironmentResource extends FreedomoticResource {

    @Get("object|gwt_object")
    public Environment retrieveEnvironment();
}
