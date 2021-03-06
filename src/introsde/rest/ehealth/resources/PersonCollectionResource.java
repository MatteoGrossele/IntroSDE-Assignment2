package introsde.rest.ehealth.resources;
import introsde.rest.ehealth.model.Person;
import introsde.rest.ehealth.model.Measure;

import java.io.IOException;
import java.util.List;
import java.util.Iterator;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Stateless // will work only inside a Java EE application
@LocalBean // will work only inside a Java EE application
@Path("/person")
public class PersonCollectionResource {

    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    // will work only inside a Java EE application
    @PersistenceUnit(unitName="introsde-jpa")
    EntityManager entityManager;

    // will work only inside a Java EE application
    @PersistenceContext(unitName = "introsde-jpa",type=PersistenceContextType.TRANSACTION)
    private EntityManagerFactory entityManagerFactory;

    // Return the list of people to the user in the browser
    /******** REQUEST -1- **********/
    /******** REQUEST -12- *********/
    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public List<Person> getPersonsBrowser(@QueryParam("measureType") String measureType, @QueryParam("max") String max, @QueryParam("min") String min) {
        List<Person> people = Person.getAll();

        if(measureType != null){
            float massimo = Float.parseFloat(max);
            float minimo = Float.parseFloat(min);
            for (Iterator<Person> iter = people.listIterator(); iter.hasNext(); ) {
                Person a = iter.next();
                List<Measure> misure = a.getMeasures();
                if(misure != null)
                    for(Measure meas : misure) {
                        if(meas.getType().equals(measureType)) {
                            float personvalue = Float.parseFloat(meas.getValue());
                            if(max != null) {
                                if(personvalue > massimo){
                                    iter.remove();
                                    break;
                                }
                            }
                            if(min != null) {
                                if(personvalue < minimo){
                                    iter.remove();
                                    break;
                                }
                            }
                        }

                    }
                else
                    iter.remove();
                
            }
        }

        return people;
    }

    // retuns the number of people
    // to get the total number of records
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        List<Person> people = Person.getAll();
        int count = people.size();
        return String.valueOf(count);
    }

    /******** REQUEST -4- *********/

    @POST
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    @Consumes({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public Person newPerson(Person person) throws IOException {
        return Person.savePerson(person);
    }

    // Defines that the next path parameter after the base url is
    // treated as a parameter and passed to the PersonResources
    // Allows to type http://localhost:599/base_url/1
    // 1 will be treaded as parameter todo and passed to PersonResource
    @Path("{personId}")
    public PersonResource getPerson(@PathParam("personId") int id) {
        return new PersonResource(uriInfo, request, id);
    }
}