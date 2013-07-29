/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.freedomotic.restapi.server.resources;

import it.freedomotic.environment.EnvironmentPersistence;
import it.freedomotic.model.environment.Zone;
import it.freedomotic.persistence.FreedomXStream;
import it.freedomotic.restapi.server.interfaces.ZonesResource;

import java.util.ArrayList;

import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

/**
 *
 * @author gpt
 */
public class ZonesServerResource extends ServerResource implements ZonesResource {

    private static volatile ArrayList<Zone> zones;

    @Override
    protected void doInit() throws ResourceException {

        int env = Integer.parseInt((String) getRequest().getAttributes().get("env"));
        zones = EnvironmentPersistence.getEnvironments().get(env).getPojo().getZones();
    }

    @Override
    public String retrieveXml() {
        String ret = "";
        XStream xstream = FreedomXStream.getXstream();
        ret = xstream.toXML(zones);
        return ret;
    }

    @Override
    public String retrieveJson() {
        String ret = "";
        XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
        xstream.setMode(XStream.NO_REFERENCES);
        ret = xstream.toXML(zones);
        return ret;
    }

    @Override
    public ArrayList<Zone> retrieveZones() {
        return zones;
    }
}
