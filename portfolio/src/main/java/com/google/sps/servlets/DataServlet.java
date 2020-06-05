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

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
private static Gson gson = new Gson();  
private static final ArrayList<String> comments = new ArrayList<String>();  
  
 
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json;");
    response.getWriter().println(convertToJsonUsingGson(comments));
  }

  private String convertToJsonUsingGson(ArrayList<String> jsonData) {
    return gson.toJson(jsonData);
  }
  
  // Get the comments from the user and display them
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String input = getParameter(request, "text-input", "");
    String[] words = input.split("\\s*,\\s*");

    int i = 0;
    for ( String word : words) {
       comments.add(words[i] + "\n"); 
       i++;
    }

    response.setContentType("text/html");
    response.getWriter().println(comments);
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

