/*
 * Copyright 2012 hbz NRW (http://www.hbz-nrw.de/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package de.nrw.hbz.regal.api;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.multipart.MultiPart;

import de.nrw.hbz.regal.api.helper.ObjectType;

/**
 * @author Jan Schnasse, schnasse@hbz-nrw.de
 * 
 */
@Path("/webpage")
public class Webpage {
    final static Logger logger = LoggerFactory.getLogger(Webpage.class);

    Resource resources = null;

    /**
     * @throws IOException
     *             if resources can't be initialised.
     */
    public Webpage() throws IOException {
	resources = new Resource();
    }

    /**
     * @return a message
     */
    @DELETE
    @Produces({ "application/json", "application/xml" })
    public String deleteAll() {
	return resources.deleteAllOfType(ObjectType.webpage.toString());
    }

    /**
     * @param pid
     *            the pid of the resource
     * @param namespace
     *            the namespace of the resource
     * @return a message
     */
    @PUT
    @Path("/{namespace}:{pid}")
    @Produces({ "application/json", "application/xml" })
    public String createWebpage(@PathParam("pid") String pid,
	    @PathParam("namespace") String namespace) {
	CreateObjectBean input = new CreateObjectBean();
	input.type = ObjectType.webpage.toString();
	return resources.create(pid, namespace, input);
    }

    /**
     * @param pid
     *            the pid of the resource
     * @param namespace
     *            the namespace of the resource
     * @return a message
     */
    @DELETE
    @Path("/{namespace}:{pid}")
    @Produces({ "application/json", "application/xml" })
    public String deleteWebpage(@PathParam("pid") String pid,
	    @PathParam("namespace") String namespace) {
	return resources.delete(pid, namespace);
    }

    /**
     * @param pid
     *            the pid of the resource
     * @param content
     *            dublin core
     * @return a message
     */
    @POST
    @Path("/{pid}/dc")
    @Produces({ "application/json", "application/xml" })
    @Consumes({ "application/json", "application/xml" })
    public String updateWebpageDC(@PathParam("pid") String pid,
	    DCBeanAnnotated content) {
	return resources.updateDC(pid, content);
    }

    /**
     * @param pid
     *            the pid of the resource
     * @param content
     *            n-triple
     * @return a message
     */
    @PUT
    @Path("/{pid}/metadata")
    @Consumes({ "text/plain" })
    @Produces({ "text/plain" })
    public String updateWebpageMetadata(@PathParam("pid") String pid,
	    String content) {
	return resources.updateMetadata(pid, content);
    }

    /**
     * @param pid
     *            the pid of the resource
     * @param content
     *            n-triple
     * @return a message
     */
    @Deprecated
    @POST
    @Path("/{pid}/metadata")
    @Consumes({ "text/plain" })
    @Produces({ "text/plain" })
    public String updateWebpageMetadataPost(@PathParam("pid") String pid,
	    String content) {
	return resources.updateMetadata(pid, content);
    }

    /**
     * @param pid
     *            the pid of the resource
     * @param versionPid
     *            the pid of the version
     * @param namespace
     *            the namespace of the resource
     * @return a message
     */
    @PUT
    @Path("/{pid}/version/{namespace}:{versionPid}")
    @Produces({ "application/json", "application/xml" })
    public String createWebpageVersion(@PathParam("pid") String pid,
	    @PathParam("versionPid") String versionPid,
	    @PathParam("namespace") String namespace) {
	CreateObjectBean input = new CreateObjectBean();
	input.type = ObjectType.version.toString();
	input.parentPid = pid;
	return resources.create(versionPid, namespace, input);

    }

    /**
     * @param pid
     *            the pid of the resource
     * @param versionPid
     *            the pid of the version
     * @param content
     *            dublin core
     * @return a message
     */
    @POST
    @Path("/{pid}/version/{versionPid}/dc")
    @Produces({ "application/json", "application/xml" })
    @Consumes({ "application/json", "application/xml" })
    public String updateWebpageVersionDC(@PathParam("pid") String pid,
	    @PathParam("versionPid") String versionPid, DCBeanAnnotated content) {
	return resources.updateDC(versionPid, content);
    }

    /**
     * @param pid
     *            the pid of the resource
     * @param namespace
     *            the namespace of the resource
     * @param versionPid
     *            the pid of the version
     * @param multiPart
     *            the data as multipart
     * @return a message
     */
    @POST
    @Path("/{namespace}:{pid}/version/{versionPid}/data")
    @Produces({ "application/json", "application/xml" })
    @Consumes("multipart/mixed")
    public String updateWebpageVersionData(@PathParam("pid") String pid,
	    @PathParam("namespace") String namespace,
	    @PathParam("versionPid") String versionPid, MultiPart multiPart) {
	return resources.updateData(versionPid, namespace, multiPart);
    }

    /**
     * @param pid
     *            the pid of the resource
     * @param versionPid
     *            the pid of the version
     * @param content
     *            n-triple
     * @return a message
     */
    @PUT
    @Path("/{pid}/version/{versionPid}/metadata")
    @Consumes({ "text/plain" })
    @Produces({ "text/plain" })
    public String updateWebpageVersionMetadata(@PathParam("pid") String pid,
	    @PathParam("versionPid") String versionPid, String content) {
	return resources.updateMetadata(versionPid, content);
    }

    /**
     * @param pid
     *            the pid of the resource
     * @param versionPid
     *            the pid of the version
     * @param content
     *            n-triple
     * @return a message
     */
    @Deprecated
    @POST
    @Path("/{pid}/version/{versionPid}/metadata")
    @Consumes({ "text/plain" })
    @Produces({ "text/plain" })
    public String updateWebpageVersionMetadataPost(
	    @PathParam("pid") String pid,
	    @PathParam("versionPid") String versionPid, String content) {
	return resources.updateMetadata(versionPid, content);
    }

    /**
     * @param pid
     *            the pid of the resource
     * @param versionPid
     *            the pid of the version
     * @return n-triple
     */
    @GET
    @Path("/{pid}/version/{versionPid}/metadata")
    @Produces({ "application/*" })
    public String readWebpageVersionMetadata(@PathParam("pid") String pid,
	    @PathParam("versionPid") String versionPid) {
	return resources.readMetadata(versionPid);

    }

    /**
     * @param pid
     *            the pid of the resource
     * @param versionPid
     *            the pid of the version
     * @return dublin core
     */
    @GET
    @Path("/{pid}/version/{versionPid}/dc")
    @Produces({ "application/xml", "application/json" })
    public DCBeanAnnotated readWebpageVersionDC(@PathParam("pid") String pid,
	    @PathParam("versionPid") String versionPid) {
	return resources.readDC(versionPid);

    }

    /**
     * @param pid
     *            the pid of the resource
     * @param versionPid
     *            the pid of the version
     * @param namespace
     *            the namespace of the resource
     * @return data of that version
     */
    @GET
    @Path("/{pid}/version/{namespace}:{versionPid}/data")
    @Produces({ "application/*" })
    public Response readWebpageVersionData(@PathParam("pid") String pid,
	    @PathParam("versionPid") String versionPid,
	    @PathParam("namespace") String namespace) {
	return resources.readData(versionPid, namespace);

    }

    /**
     * @param pid
     *            the pid of the resource
     * @return a list of versions of this webpage
     */
    @GET
    @Path("/{pid}/version/")
    @Produces({ "application/json", "application/xml" })
    public ObjectList getAllVersions(@PathParam("pid") String pid) {
	return resources.getAllParts(pid);

    }

    /**
     * @param pid
     *            the pid of the resource
     * @return n-triple
     */
    @GET
    @Path("/{pid}/metadata")
    @Produces({ "text/plain" })
    public String readWebpageMetadata(@PathParam("pid") String pid) {
	return resources.readMetadata(pid);
    }

    /**
     * @return a list of webpages
     */
    @GET
    @Produces({ "application/json", "application/xml" })
    public ObjectList getAll() {
	return resources.getAllOfType(ObjectType.webpage.toString());
    }

    /**
     * @return a list of webpages as html
     */
    @GET
    @Produces({ "text/html" })
    public Response getAllAsHtml() {
	String rem = resources
		.getAllOfTypeAsHtml(ObjectType.webpage.toString());
	ResponseBuilder res = Response.ok().entity(rem);
	return res.build();
    }

    /**
     * @param pid
     *            the pid of the resource the pid of the resource
     * @param namespace
     *            the namespace of the resource
     * @return an aggregated representation of the resource
     * @throws URISyntaxException
     *             if redirection is coded wrong
     */
    @GET
    @Path("/{namespace}:{pid}")
    @Produces({ "application/json", "application/xml", "text/html" })
    public Response getResource(@PathParam("pid") String pid,
	    @PathParam("namespace") String namespace) throws URISyntaxException {
	return Response
		.temporaryRedirect(
			new java.net.URI("../resource/" + namespace + ":" + pid
				+ "/about")).status(303).build();
    }

    /**
     * @param pid
     *            the pid of the resource
     * @return dublin core
     */
    @GET
    @Path("/{pid}/dc")
    @Produces({ "application/xml", "application/json" })
    public DCBeanAnnotated readWebpageDC(@PathParam("pid") String pid) {
	return resources.readDC(pid);
    }
}