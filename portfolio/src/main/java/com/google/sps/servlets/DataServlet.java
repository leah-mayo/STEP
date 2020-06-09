// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.PreparedQuery;
import java.util.List;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  private static final Gson gson = new Gson();  
  private static final ArrayList<String> commentList = new ArrayList<String>();
  private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService(); 
  private static final String COMMENT = "Comment";  
 
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    Query query = new Query(COMMENT);
    PreparedQuery results = datastore.prepare(query);
    for (Entity entity : results.asIterable()) {
        String comment = (String) entity.getProperty(COMMENT);
        commentList.add(comment);
    }

    
    response.setContentType("application/json;");
    response.getWriter().println(convertToJsonUsingGson(commentList));
    
  }

  private String convertToJsonUsingGson(ArrayList<String> jsonData) {
    return gson.toJson(jsonData);
  }
  
  // Get the comments from the user and add them to datastore
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String input = getParameter(request, "text-input", "");
    //comments.add(input);

    Entity commentEntity = new Entity(COMMENT);
    commentEntity.setProperty(COMMENT, input);
    datastore.put(commentEntity);

    response.sendRedirect("/index.html");
  }

   /**
    * @return the request parameter, or the default value if the parameter
    *         was not specified by the client
    */
   private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
  
}

