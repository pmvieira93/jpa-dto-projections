package org.sql.projections;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.sql.projections.infrastructure.repository.entity.Store;
import org.sql.projections.infrastructure.repository.entity.Vehicle;
import org.sql.projections.infrastructure.repository.projections.StoreProjection;
import org.sql.projections.infrastructure.repository.projections.StoreSingleProjection;
import org.sql.projections.infrastructure.repository.projections.StoreVehicleProjection;
import org.sql.projections.infrastructure.repository.projections.StoreVehicles;
import org.sql.projections.infrastructure.repository.projections.StoreVehiclesJsonStringProjection;
import org.sql.projections.infrastructure.repository.projections.VehicleProjection;
import org.sql.projections.infrastructure.repository.projections.VehicleSimple;

import java.util.List;

/**
 *
 */
@ApplicationScoped
public class StoreRepository implements PanacheRepository<Store> {


    /**
     * Not Working.
     *
     * @return
     */
    public StoreProjection findStore() {

        TypedQuery<StoreProjection> query = getEntityManager()
                .createQuery("SELECT new org.sql.projections.infrastructure.repository.projections.StoreProjection( s.location, "
                        + " (SELECT new org.sql.projections.infrastructure.repository.projections.VehicleProjection(v.brand, v.model, v.kms, v.year) FROM Vehicle v WHERE v.store = s )) "
                        + "FROM Store s WHERE 1=1", StoreProjection.class);
//        PanacheQuery<StoreProjection> query = find("SELECT new org.sql.projections.infrastructure.repository.projections.StoreProjection( s.location, "
//                + " (SELECT new org.sql.projections.infrastructure.repository.projections.VehicleProjection(v.brand, v.model, v.kms, v.year) FROM Vehicle v WHERE v.store = s )) "
//                + "FROM Store s WHERE 1=1", StoreProjection.class);

        return query.getSingleResult();
    }


//    public List<StoreVehicleProjection> findAllProjections() {
//        // (new org.sql.projections.infrastructure.repository.projections.VehicleProjection(v.brand, v.model, v.kms, v.year))
//        TypedQuery<StoreVehicleProjection> query = getEntityManager().createQuery(
//                """
//                SELECT new org.sql.projections.infrastructure.repository.projections.StoreVehicleProjection( s.location,  s.vehicles)
//                FROM Store s
//                LEFT JOIN Vehicle v ON v.store = s
//                        """, StoreVehicleProjection.class);
//        return query.getResultList();
//    }

    /**
     * Not Working. Try using @Converter but not working.
     * Following: https://www.baeldung.com/spring-boot-jpa-storing-postgresql-jsonb
     *
     * @return
     */
    public List<StoreVehicleProjection> findAllProjections() {
        Query query = getEntityManager().createNativeQuery(
                """
                        SELECT s.location, jsonb_agg(jsonb_build_object('brand', v.brand, 'model', v.model, 'kms', v.kms, 'year', v.year)) as vehicles 
                        FROM store s INNER JOIN vehicle v ON v.store_id = s.id GROUP BY s.location
                        """, StoreVehicleProjection.class);
        return query.getResultList();
    }

    /**
     * Working. Return the 'vehicles' as a Json String.
     * https://stackoverflow.com/questions/6349421/how-to-use-jackson-to-deserialise-an-array-of-objects
     *
     * @return
     */
    public List<StoreVehiclesJsonStringProjection> findAllStoresJsonVehicles() {
        Query query = getEntityManager().createNativeQuery(
                """
                        SELECT s.location, jsonb_agg(jsonb_build_object('brand', v.brand, 'model', v.model, 'kms', v.kms, 'year', v.year)) as vehicles 
                        FROM store s INNER JOIN vehicle v ON v.store_id = s.id GROUP BY s.location
                        """, StoreVehiclesJsonStringProjection.class);
        return query.getResultList();
    }


    /**
     * Working.
     *
     * @return
     */
    public List<StoreSingleProjection> findAllStoresTwo() {
        return findAll().project(StoreSingleProjection.class).list();
    }


    /**
     * Working with EntityGraph. More Performed that others.
     *
     * @return
     */
    public List<StoreProjection> findAllStores() {
        final EntityGraph<Store> entityGraph = getEntityManager().createEntityGraph(Store.class);
        final List<Store> list = findAll().withHint("jakarta.persistence.fetchgraph", entityGraph).list();
        return list.stream()
                .map(st -> new StoreProjection(st.getLocation(),
                        st.getVehicles().stream().map(vh -> new VehicleProjection(vh.getBrand(), vh.getModel(), vh.getKms(), vh.getYear()))
                                .toList()))
                .toList();
    }


    // Another way: https://vladmihalcea.com/one-to-many-dto-projection-hibernate/

    public List<StoreVehicles> findAllStoresAndSimpleVehicles() {


        return criteriaBuilder();
//        final String JPQL = "SELECT " +
//                "new org.sql.projections.infrastructure.repository.projections.StoreVehicles(s.location, " +
//                " ARRAY(new org.sql.projections.infrastructure.repository.projections.VehicleSimple(v.brand, v.model, v.year, v.month))" +
//                ") " +
//                "FROM Store s " +
//                "LEFT JOIN s.vehicles v";
//        return getEntityManager().createQuery(JPQL, StoreVehicles.class).getResultList();


//        TypedQuery<StoreVehicles> query = getEntityManager()
//                .createQuery("""
//                        SELECT new org.sql.projections.infrastructure.repository.projections.StoreVehicles(s.location,
//                            COLLECT( new org.sql.projections.infrastructure.repository.projections.VehicleSimple( v.brand, v.mode, v.year, v.month)))
//                        FROM Store s
//                        INNER JOIN Vehicle v ON v.store = s
//                        """, StoreVehicles.class);
//        return query.getResultList();
    }

    /**
     * Not Working.
     * @return
     */
    private List<StoreVehicles> criteriaBuilder() {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<StoreVehicles> query = builder.createQuery(StoreVehicles.class);

        Root<Store> root = query.from(Store.class);
        Join<Store, Vehicle> join = root.join("vehicles");

        //List<Selection<?>> selections = new ArrayList<>();

        query.select(builder.construct(StoreVehicles.class,
                root.get("location"), builder.array(builder.construct(VehicleSimple.class,
                        join.get("brand"), join.get("model"), join.get("year"), join.get("month")))));

        TypedQuery<StoreVehicles> q = getEntityManager().createQuery(query);
        return q.getResultList();
    }
}
