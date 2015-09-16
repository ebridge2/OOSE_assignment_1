//-------------------------------------------------------------------------------------------------------------//
// Code based on a tutorial by Shekhar Gulati of SparkJava at
// https://blog.openshift.com/developing-single-page-web-applications-using-java-8-spark-mongodb-and-angularjs/
//-------------------------------------------------------------------------------------------------------------//

package com.oose2015.ebridge2.hareandhounds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import static spark.Spark.*;

public class HareandhoundController {

    private static final String API_CONTEXT = "/hareandhounds/api";
    /** the game repo that the controller acts on*/
    private GameRepo grepo;
    /** leftover*/
    private final Logger logger = LoggerFactory.getLogger(HareandhoundController.class);
    /**
     * initializes a controller for the game repos so that the games can do things
     * @param gamerepo
     */
    public HareandhoundController(GameRepo gamerepo) {
        this.grepo = gamerepo;
        this.setupEndpoints();
    }
    /**
     * retrieves the appropriate function calls given a Json request and returns
     * packet in a hashmap that the transformer can turn into the appropriate response
     */
    private void setupEndpoints() {
    	//start a new game
        post(API_CONTEXT + "/games", "application/json", (request, response) -> {
        	try {
        		HashMap<String, String> returnMap = this.grepo.addGame(request.body());
        		response.status(201);
        		return returnMap;
        	} catch (IncorrectJsonException ex) {
        		logger.error(ex.getMessage());
        		response.status(400);
        		return ex.getHash();
        	}
        	}, new JsonTransformer());
        //adds a player to an existing game
        put(API_CONTEXT + "/games/:gameId", "application/json", (request, response) -> {
        	try {
            	String gameId = request.params(":gameId"); //find the game to add a player to
        		HashMap<String, String> returnMap = this.grepo.addPlayer(gameId);
        		response.status(200);
        		return returnMap;
        	} catch (NoSpaceException ex) {
        		logger.error(ex.getMessage());
        		response.status(410);
        		return ex.getHash();
        	} catch (InvalidGameException ex) {
        		logger.error(ex.getMessage());
        		response.status(404);
        		return ex.getHash();
        	} catch (IncorrectJsonException ex) {
        		logger.error(ex.getMessage());
        		response.status(400);
        		return ex.getHash();
        	}
        }, new JsonTransformer());
        //describe the game state
        get(API_CONTEXT + "/games/:gameId/state", "application/json", (request, response) -> {
	        try {
        		String gameId = request.params(":gameId");
	        	HashMap<String, String> returnMap = this.grepo.fetchState(gameId);
	        	response.status(200);
	        	return returnMap;
	        } catch (InvalidGameException ex) {
	        	logger.error(ex.getMessage());
	        	response.status(404);
	        	return ex.getHash();
	        } catch (IncorrectJsonException ex) {
        		logger.error(ex.getMessage());
        		response.status(400);
        		return ex.getHash();
        	}
	    }, new JsonTransformer());
        //retrieves the board states
        get(API_CONTEXT + "/games/:gameId/board", "application/json", (request, response) -> {
        	try {
        		response.status(200);
        		String gameId=request.params(":gameId");
        		return this.grepo.fetchBoard(gameId);      
	        } catch (InvalidGameException ex) {
	        	logger.error(ex.getMessage());
	        	response.status(404);
	        	return ex.getHash();
	        } catch (IncorrectJsonException ex) {
        		logger.error(ex.getMessage());
        		response.status(400);
        		return ex.getHash();
        	}
        }, new JsonTransformer());
        // a move of a game post
        post(API_CONTEXT + "/games/:gameId/turns", "application/json", (request, response) -> {
        	try {
        		String gameId = request.params(":gameId");
        		HashMap<String, String> returnMap = this.grepo.move(request.body(), gameId);
        		response.status(200);
        		return returnMap;
        	} catch (InvalidGameException ex) {
        		logger.error(ex.getMessage());
        		response.status(404);
        		return ex.getHash();
        	} catch (InvalidPlayerException ex) {
        		logger.error(ex.getMessage());
        		response.status(404);
        		return ex.getHash();
        	} catch (IncorrectTurnException ex) {
        		logger.error(ex.getMessage());
        		response.status(422);
        		return ex.getHash();
        	} catch (IllegalMoveException ex) {
        		logger.error(ex.getMessage());
        		response.status(422);
        		return ex.getHash();
        	} catch (IncorrectJsonException ex) {
        		logger.error(ex.getMessage());
        		response.status(400);
        		return ex.getHash();
        	}
        }, new JsonTransformer());
    }
}
