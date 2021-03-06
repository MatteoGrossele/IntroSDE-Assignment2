package introsde.rest.ehealth.resources;

import introsde.rest.ehealth.model.*;

import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Iterator;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Stateless // only used if the the application is deployed in a Java EE container
@LocalBean // only used if the the application is deployed in a Java EE container
public class MeasureTypesCollectionResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    int id;
    String measureType;

    EntityManager entityManager; // only used if the application is deployed in a Java EE container

    public MeasureTypesCollectionResource(UriInfo uriInfo, Request request, int id, String measureType,EntityManager em) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
        this.entityManager = em;
        this.measureType = measureType;
    }

    public MeasureTypesCollectionResource(UriInfo uriInfo, Request request,int id, String measureType) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
        this.measureType = measureType;
    }

    /******** REQUEST -6- *********/
    /******** REQUEST -11- *********/
    // Application integration
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Measure> getMeasures(@QueryParam("before") String before,@QueryParam("after") String after ) throws ParseException {
        List<Measure> history = Measure.getMeasureHistorybyPersonIdType(id,measureType);
        if(after != null && before != null){

            DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Date beforedate = format.parse(before);
            Date afterdate = format.parse(after);
            for (Iterator<Measure> iter = history.listIterator(); iter.hasNext(); ) {
                Measure a = iter.next();
                Date measuredate = format.parse(a.getDate());
                if (measuredate.before(afterdate)) {
                    iter.remove();
                }
                if (measuredate.after(beforedate)) {
                    iter.remove();
                }
            }
        }
        return history;
    }


    /******** REQUEST -7- *********/
    @Path("{idMeasure}")
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
    public  List<Measure>  getMeasurebyPersonIdTypeMid(@PathParam("idMeasure") int mid) {
        List<Measure> history = Measure.getMeasureHistorybyPersonIdTypeMid(id ,measureType, mid);
        return history;
    }

    /******** REQUEST -8- *********/
    @POST
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces({MediaType.APPLICATION_XML,  MediaType.APPLICATION_JSON})
    public Measure newMeasure(Measure measure) {
       return Measure.saveMeasure(measure);
    }

    //Used to delete the created measure to avoid conflict in future executions
    @DELETE
    @Path("{idMeasure}")
    public void deleteMeasure(@PathParam("idMeasure") int id) {
        Measure c = Measure.getMeasureById(id);
        Measure.removeMeasure(c);
    }

    /******** REQUEST -10- *********/
    @PUT
    @Path("{idMeasure}")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response putPerson(@PathParam("idMeasure") int id, Measure measure) {
        Measure existing = Measure.getMeasureById(id);
        Response res;
        if (existing == null) {
            res = Response.noContent().build();
        } else {
            res = Response.ok().entity(measure).build();
            measure.setIdMeasure(id);
            Measure.updateMeasure(measure);
        }
        return res;
    }

   


}