//-------------------------------------------------------------------------------------------------------------//
// Code based on a tutorial by Shekhar Gulati of SparkJava at
// https://blog.openshift.com/developing-single-page-web-applications-using-java-8-spark-mongodb-and-angularjs/
//-------------------------------------------------------------------------------------------------------------//

package com.oose2015.ebridge2.hareandhounds;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import javax.sql.DataSource;
import java.util.List;

public class HareandhoundService {

    private Sql2o db;

    private final Logger logger = LoggerFactory.getLogger(HareandhoundService.class);

    /**
     * Construct the model with a pre-defined datasource. The current implementation
     * also ensures that the DB schema is created if necessary.
     *
     * @param dataSource
     */
    public HareandhoundService(DataSource dataSource) throws HareandhoundServiceException {
        db = new Sql2o(dataSource);

        //Create the schema for the database if necessary. This allows this
        //program to mostly self-contained. But this is not always what you want;
        //sometimes you want to create the schema externally via a script.
        try (Connection conn = db.open()) {
            String sql = "CREATE TABLE IF NOT EXISTS item (item_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                         "                                 title TEXT, done BOOLEAN, created_on TIMESTAMP)" ;
            conn.createQuery(sql).executeUpdate();
        } catch(Sql2oException ex) {
            logger.error("Failed to create schema at startup", ex);
            throw new HareandhoundServiceException("Failed to create schema at startup", ex);
        }
    }

    /**
     * Fetch all hareandhounds entries in the list
     *
     * @return List of all Hareandhound entries
     */
    public List<Hareandhound> findAll() throws HareandhoundServiceException {
        String sql = "SELECT * FROM item" ;
        try (Connection conn = db.open()) {
            List<Hareandhound> harehound =  conn.createQuery(sql)
                .addColumnMapping("item_id", "id")
                .addColumnMapping("created_on", "createdOn")
                .executeAndFetch(Hareandhound.class);
            return harehound;
        } catch(Sql2oException ex) {
            logger.error("HareandhoundService.findAll: Failed to query database", ex);
            throw new HareandhoundServiceException("HareandhoundService.findAll: Failed to query database", ex);
        }
    }

    /**
     * Create a new Hareandhound entry.
     */
    public void createNewHareandhound(String body) throws HareandhoundServiceException {
        Hareandhound hareandhounds = new Gson().fromJson(body, Hareandhound.class);

        String sql = "INSERT INTO item (title, done, created_on) " +
                     "             VALUES (:title, :done, :createdOn)" ;

        try (Connection conn = db.open()) {
            conn.createQuery(sql)
                .bind(hareandhounds)
                .executeUpdate();
        } catch(Sql2oException ex) {
            logger.error("HareandhoundService.createNewHareandhound: Failed to create new entry", ex);
            throw new HareandhoundServiceException("HareandhoundService.createNewHareandhound: Failed to create new entry", ex);
        }
    }

    /**
     * Find a hareandhounds entry given an Id.
     *
     * @param id The id for the Hareandhound entry
     * @return The Hareandhound corresponding to the id if one is found, otherwise null
     */
    public Hareandhound find(String id) throws HareandhoundServiceException {
        String sql = "SELECT * FROM item WHERE item_id = :itemId ";

        try (Connection conn = db.open()) {
            return conn.createQuery(sql)
                .addParameter("itemId", Integer.parseInt(id))
                .addColumnMapping("item_id", "id")
                .addColumnMapping("created_on", "createdOn")
                .executeAndFetchFirst(Hareandhound.class);
        } catch(Sql2oException ex) {
            logger.error(String.format("HareandhoundService.find: Failed to query database for id: %s", id), ex);
            throw new HareandhoundServiceException(String.format("HareandhoundService.find: Failed to query database for id: %s", id), ex);
        }
    }

    /**
     * Update the specified Hareandhound entry with new information
     */
    public Hareandhound update(String hareandhoundsId, String body) throws HareandhoundServiceException {
        Hareandhound hareandhounds = new Gson().fromJson(body, Hareandhound.class);

        String sql = "UPDATE item SET title = :title, done = :done, created_on = :createdOn WHERE item_id = :itemId ";
        try (Connection conn = db.open()) {
            //Update the item
            conn.createQuery(sql)
                    .bind(hareandhounds)  // one-liner to map all Hareandhound object fields to query parameters :title etc
                    .addParameter("itemId", Integer.parseInt(hareandhoundsId))
                    .executeUpdate();

            //Verify that we did indeed update something
            if (getChangedRows(conn) != 1) {
                logger.error(String.format("HareandhoundService.update: Update operation did not update rows. Incorrect id(?): %s", hareandhoundsId));
                throw new HareandhoundServiceException(String.format("HareandhoundService.update: Update operation did not update rows. Incorrect id(?): %s", hareandhoundsId), null);
            }
        } catch(Sql2oException ex) {
            logger.error(String.format("HareandhoundService.update: Failed to update database for id: %s", hareandhoundsId), ex);
            throw new HareandhoundServiceException(String.format("HareandhoundService.update: Failed to update database for id: %s", hareandhoundsId), ex);
        }

        return find(hareandhoundsId);
    }

    /**
     * Delete the entry with the specified id
     */
    public void delete(String hareandhoundsId) throws HareandhoundServiceException {
        String sql = "DELETE FROM item WHERE item_id = :itemId" ;
        try (Connection conn = db.open()) {
            //Delete the item
            conn.createQuery(sql)
                .addParameter("itemId", Integer.parseInt(hareandhoundsId))
                .executeUpdate();

            //Verify that we did indeed change something
            if (getChangedRows(conn) != 1) {
                logger.error(String.format("HareandhoundService.delete: Delete operation did not delete rows. Incorrect id(?): %s", hareandhoundsId));
                throw new HareandhoundServiceException(String.format("HareandhoundService.delete: Delete operation did not delete rows. Incorrect id(?): %s", hareandhoundsId), null);
            }
        } catch(Sql2oException ex) {
            logger.error(String.format("HareandhoundService.update: Failed to delete id: %s", hareandhoundsId), ex);
            throw new HareandhoundServiceException(String.format("HareandhoundService.update: Failed to delete id: %s", hareandhoundsId), ex);
        }
    }

    //-----------------------------------------------------------------------------//
    // Helper Classes and Methods
    //-----------------------------------------------------------------------------//

    public static class HareandhoundServiceException extends Exception {
        public HareandhoundServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * This Sqlite specific method returns the number of rows changed by the most recent
     * INSERT, UPDATE, DELETE operation. Note that you MUST use the same connection to get
     * this information
     */
    private int getChangedRows(Connection conn) throws Sql2oException {
        return conn.createQuery("SELECT changes()").executeScalar(Integer.class);
    }
}
