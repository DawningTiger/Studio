package com.apress.springrecipes.nosql;

import com.mongodb.DB;
import com.mongodb.MongoException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.DbCallback;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.PreDestroy;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Created by marten on 22-09-14.
 */
public class MongoDBVehicleRepository implements VehicleRepository {

    private final MongoTemplate mongo;

    public MongoDBVehicleRepository(MongoTemplate mongo) {
        this.mongo = mongo;
    }

    @Override
    public long count() {
        return mongo.count(null, Vehicle.class);
    }

    @Override
    public void save(Vehicle vehicle) {
        mongo.save(vehicle);
    }

    @Override
    public void delete(Vehicle vehicle) {
        mongo.remove(vehicle);
    }

    @Override
    public List<Vehicle> findAll() {
        return mongo.findAll(Vehicle.class);
    }

    @Override
    public Vehicle findByVehicleNo(String vehicleNo) {
        return mongo.findOne(new Query(where("vehicleNo").is(vehicleNo)), Vehicle.class);
    }


    @PreDestroy
    public void cleanUp() {
        mongo.execute(new DbCallback<Object>() {
            @Override
            public Object doInDB(DB db) throws MongoException, DataAccessException {
                db.dropDatabase();
                return null;
            }
        });
    }
}