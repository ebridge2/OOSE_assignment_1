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
import java.util.HashMap;

import static spark.Spark.*;

public class HareandhoundController {

    private static final String API_CONTEXT = "/hareandhounds/api";

    private GameRepo grepo;

    private final Logger logger = LoggerFactory.getLogger(HareandhoundController.class);

    public HareandhoundController(GameRepo gamerepo) {
        this.grepo = gamerepo;
        this.setupEndpoints();
    }

    private void setupEndpoints() {
        post(API_CONTEXT + "/games", "application/json", (request, response) -> {

    		Player player1 = new Player(Player.PLAYER_ONE, PieceType.valueOf("HOUND"));
        	Game gameadded = grepo.addGame(player1);
        	response.status(201);
        	HashMap<String, String> returnMap = new HashMap<>();
        	returnMap.put("gameId", gameadded.getId());
        	returnMap.put("playerId", player1.getId());
        	returnMap.put("pieceType", player1.getType().toString());
        	return returnMap;
        }, new JsonTransformer());
    }
}
