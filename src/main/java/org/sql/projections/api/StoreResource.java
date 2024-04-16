package org.sql.projections.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.sql.projections.ListVehicleAttributeConverter;
import org.sql.projections.StoreRepository;
import org.sql.projections.VehicleRepository;
import org.sql.projections.api.dto.StoreDTO;
import org.sql.projections.infrastructure.repository.entity.Store;
import org.sql.projections.infrastructure.repository.entity.Vehicle;
import org.sql.projections.infrastructure.repository.projections.StoreProjection;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 *
 */
@Path("/stores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StoreResource {

    @Inject
    StoreRepository storeRepository;

    @Inject
    VehicleRepository vehicleRepository;

    @Inject
    ListVehicleAttributeConverter listVehicleAttributeConverter;

//    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = Logger.getLogger(StoreResource.class);

    @GET
    public List<Store> list() {
        return storeRepository.findAll().list();
    }

    @POST
    @Transactional
    public Response create(Store store) {
        storeRepository.persist(store);
        return Response.created(URI.create("/stores/" + store.getId())).build();
    }

    @GET
    @Path("/{id}")
    public Store get(String id) {
        return storeRepository.find("id", UUID.fromString(id)).firstResult();
    }

    @POST
    @Path("/{id}/vehicles")
    @Transactional
    public Response create(String id, Vehicle vehicle) {
        Store store = get(id);
        vehicle.setStore(store);
        vehicleRepository.persist(vehicle);
        return Response.created(URI.create("/vehicles/" + vehicle.getId())).build();
    }

    @GET
    @Path("/{id}/vehicles")
    public List<Vehicle> getVehicles(String id) {
        return vehicleRepository.find("store.id", UUID.fromString(id)).list();
    }

    @GET
    @Path("/vehicles/{id}")
    public Vehicle getVehicle(String id) {
        return vehicleRepository.find("id", UUID.fromString(id)).firstResult();
    }

    @GET
    @Path("/others")
    public StoreProjection one() {
        return storeRepository.findStore();
    }

    @GET
    @Path("/others/all/{option}")
    public Response all(Integer option) {
        switch (option) {
            case 1:
                logger.info("### EntityGraph Solution ### {} "+ option);
                // Increase of JDBC Connections
                // 5 Connection WHEN have ~ 265 Rows
                /*
                2024-04-15 11:23:28,142 INFO  [org.hib.eng.int.StatisticalLoggingSessionEventListener] (executor-thread-1) Session Metrics {
                    256100 nanoseconds spent acquiring 5 JDBC connections;
                    217600 nanoseconds spent releasing 5 JDBC connections;
                    753300 nanoseconds spent preparing 5 JDBC statements;
                    15517500 nanoseconds spent executing 5 JDBC statements;
                    0 nanoseconds spent executing 0 JDBC batches;
                    0 nanoseconds spent performing 0 L2C puts;
                    0 nanoseconds spent performing 0 L2C hits;
                    0 nanoseconds spent performing 0 L2C misses;
                    0 nanoseconds spent executing 0 flushes (flushing a total of 0 entities and 0 collections);
                    0 nanoseconds spent executing 0 partial-flushes (flushing a total of 0 entities and 0 collections)
                }
                */
                return Response.ok(storeRepository.findAllStores()).build();
            case 2:
                logger.info("### Single Projection Solution ### {} "+ option);
                return Response.ok(storeRepository.findAllStoresTwo()).build();
            case 3:
                logger.info("### Single Projection Solution ### {} "+ option);
                // Increase a lot of JDBC Connections
                // 65 Connection WHEN have ~ 265 Rows
                /*
                2024-04-15 11:25:48,475 INFO  [org.hib.eng.int.StatisticalLoggingSessionEventListener] (executor-thread-1) Session Metrics {
                    2957200 nanoseconds spent acquiring 65 JDBC connections;
                    3173000 nanoseconds spent releasing 65 JDBC connections;
                    7239000 nanoseconds spent preparing 65 JDBC statements;
                    134765100 nanoseconds spent executing 65 JDBC statements;
                    0 nanoseconds spent executing 0 JDBC batches;
                    0 nanoseconds spent performing 0 L2C puts;
                    0 nanoseconds spent performing 0 L2C hits;
                    0 nanoseconds spent performing 0 L2C misses;
                    0 nanoseconds spent executing 0 flushes (flushing a total of 0 entities and 0 collections);
                    0 nanoseconds spent executing 0 partial-flushes (flushing a total of 0 entities and 0 collections)
                }
                 */
                return Response.ok(storeRepository.findAllStoresTwo()
                        .stream()
                        .map(st -> new StoreDTO(st.location(),
                                vehicleRepository.findVehiclesByStore(st.id())))
                        .toList()).build();
            case 4:
                //Not Working
                logger.info("### Projection Solution with JsonB & @Converter ### {} "+ option);
                return Response.ok(storeRepository.findAllProjections()).build();
            case 5:
                logger.info("### Projection Solution with JsonB & Call Converter ### {} "+ option);
                // Not Increase of JDBC Connections
                // 1 Connection WHEN have ~ 265 Rows
                /*
                2024-04-15 11:22:23,655 INFO  [org.hib.eng.int.StatisticalLoggingSessionEventListener] (executor-thread-1) Session Metrics {
                    89400 nanoseconds spent acquiring 1 JDBC connections;
                    39100 nanoseconds spent releasing 1 JDBC connections;
                    327800 nanoseconds spent preparing 1 JDBC statements;
                    3762200 nanoseconds spent executing 1 JDBC statements;
                    0 nanoseconds spent executing 0 JDBC batches;
                    0 nanoseconds spent performing 0 L2C puts;
                    0 nanoseconds spent performing 0 L2C hits;
                    0 nanoseconds spent performing 0 L2C misses;
                    0 nanoseconds spent executing 0 flushes (flushing a total of 0 entities and 0 collections);
                    0 nanoseconds spent executing 0 partial-flushes (flushing a total of 0 entities and 0 collections)
                }
                 */
                return Response.ok(storeRepository.findAllStoresJsonVehicles().stream()
                        .map(st -> {
//                            try {
//                                return new StoreDTO(st.getLocation(),
//                                        objectMapper.readValue(st.getVehicles(), new TypeReference<List<VehicleProjection>>() {}));
//                            } catch (JsonProcessingException e) {
//                                e.printStackTrace();
//                                throw new RuntimeException(e);
//                            }
                                    return new StoreDTO(st.getLocation(), listVehicleAttributeConverter.convertToEntityAttribute(st.getVehicles()));
                                }

                        ).toList()).build();
            case 6:
                logger.info("### Projection Solution & Collect() ### {} "+ option);
                return Response.ok(storeRepository.findAllStoresAndSimpleVehicles()).build();
            default:
                return Response.ok(storeRepository.findAllStores()).build();
        }
    }

}
