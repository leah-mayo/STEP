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
import com.google.sps.objects.Comment;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.FetchOptions;


@WebServlet("/data")
public class DataServlet extends HttpServlet {

  private static final Gson gson = new Gson();  
  private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
  private static final int NUM_COMMENTS_DEFAULT = 1;
  private static final String PARAM_NUM_COMMENTS = "num-comments"; 
 
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    int maxNumComments;
        try {
            maxNumComments = Integer.parseInt(request.getParameter(PARAM_NUM_COMMENTS));
        } catch (NumberFormatException e) {
            maxNumComments = NUM_COMMENTS_DEFAULT;
        }

    // Set up the query to fetch the comments from server
    Query query = new Query(Comment.TYPE);
    PreparedQuery results = datastore.prepare(query);
    List<Comment> commentList = new ArrayList<Comment>();


    for (Entity entity : results.asIterable(FetchOptions.Builder.withLimit(maxNumComments))) {
      String key = KeyFactory.keyToString(entity.getKey());
      String text = (String) entity.getProperty(Comment.COMMENT_TEXT);
      Comment comment = new Comment(key,text);
        
      commentList.add(comment);
    }

    response.setContentType("application/json;");
    response.getWriter().println(convertToJsonUsingGson(commentList));
  }

  private String convertToJsonUsingGson(List<Comment> jsonData) {
    return gson.toJson(jsonData);
  }
  
  // Get the comments from the user and add them to datastore
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String input = getParameter(request, "text-input", "");

    Entity commentEntity = new Entity(Comment.TYPE);
    commentEntity.setProperty(Comment.COMMENT_TEXT, input);
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

