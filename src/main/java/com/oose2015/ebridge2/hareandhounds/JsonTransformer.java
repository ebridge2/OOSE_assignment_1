//-------------------------------------------------------------------------------------------------------------//
// Code based on a tutorial by Shekhar Gulati of SparkJava at
// https://blog.openshift.com/developing-single-page-web-applications-using-java-8-spark-mongodb-and-angularjs/
//-------------------------------------------------------------------------------------------------------------//

package com.oose2015.ebridge2.hareandhounds;

import com.google.gson.Gson;
import spark.Response;
import spark.ResponseTransformer;

import java.util.HashMap;

/** 
 * the given json transformer
 * @author eric
 *
 */
public class JsonTransformer implements ResponseTransformer {
	/** the gson object that is transformed from the response*/
    private Gson gson = new Gson();

    /**
     * the render class for the gson object to appear as json
     */
    @Override
    public String render(Object model) {
        if (model instanceof Response) {
            return gson.toJson(new HashMap<>());
        }
        /**transforms the gson to json*/
        return gson.toJson(model);
    }

}
