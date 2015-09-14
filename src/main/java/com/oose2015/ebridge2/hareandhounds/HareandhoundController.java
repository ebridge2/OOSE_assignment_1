//-------------------------------------------------------------------------------------------------------------//
// Code based on a tutorial by Shekhar Gulati of SparkJava at
// https://blog.openshift.com/developing-single-page-web-applications-using-java-8-spark-mongodb-and-angularjs/
//-------------------------------------------------------------------------------------------------------------//

package com.oose2015.ebridge2.hareandhounds;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Collections;

import static spark.Spark.*;

public class HareandhoundController {

    private static final String API_CONTEXT = "/hareandhounds/api";

    private final HareandhoundService hareandhoundService;

    private final Logger logger = LoggerFactory.getLogger(HareandhoundController.class);

    public HareandhoundController(HareandhoundService hareandhoundService) {
        this.hareandhoundService = hareandhoundService;
        setupEndpoints();
    }

    private void setupEndpoints() {
        post(API_CONTEXT + "/games", "application/json", (request, response) -> {
            try {
                hareandhoundService.createNewHareandhound(request.body());
                response.status(201);
            } catch (HareandhoundService.HareandhoundServiceException ex) {
                logger.error("Failed to start a game");
                response.status(500);
            }
            return Collections.EMPTY_MAP;
        }, new JsonTransformer());
        
        post(API_CONTEXT + "/games/:gameId", "application/json", (request, response) -> {
        	try {
        		hareandhoundService.createNewHareandhound(request.body());
        		response.status(200);
        	} catch (HareandhoundService.HareandhoundServiceException ex) {
        		logger.error("Failed to join a game");
        		response.error(404);
        	}
        	return Collections.EMPTY_MAP;
        }, new JsonTransformer());
        

        get(API_CONTEXT + "/hareandhound/:id", "application/json", (request, response) -> {
            try {
                return hareandhoundService.find(request.params(":id"));
            } catch (HareandhoundService.HareandhoundServiceException ex) {
                logger.error(String.format("Failed to find object with id: %s", request.params(":id")));
                response.status(500);
                return Collections.EMPTY_MAP;
            }
        }, new JsonTransformer());

        get(API_CONTEXT + "/hareandhound", "application/json", (request, response)-> {
            try {
                return hareandhoundService.findAll() ;
            } catch (HareandhoundService.HareandhoundServiceException ex) {
                logger.error("Failed to fetch the list of hareandhound");
                response.status(500);
                return Collections.EMPTY_MAP;
            }
        }, new JsonTransformer());

        put(API_CONTEXT + "/hareandhound/:id", "application/json", (request, response) -> {
            try {
                return hareandhoundService.update(request.params(":id"), request.body());
            } catch (HareandhoundService.HareandhoundServiceException ex) {
                logger.error(String.format("Failed to update hareandhound with id: %s", request.params(":id")));
                response.status(500);
                return Collections.EMPTY_MAP;
            }
        }, new JsonTransformer());

        delete(API_CONTEXT + "/hareandhound/:id", "application/json", (request, response) -> {
            try {
                hareandhoundService.delete(request.params(":id"));
                response.status(200);
            } catch (HareandhoundService.HareandhoundServiceException ex) {
                logger.error(String.format("Failed to delete hareandhound with id: %s", request.params(":id")));
                response.status(500);
            }
            return Collections.EMPTY_MAP;
        }, new JsonTransformer());
    }
}
