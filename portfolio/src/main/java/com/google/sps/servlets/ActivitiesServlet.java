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
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/activity-data")
public class ActivitiesServlet extends HttpServlet {

  private Map<String, Integer> activityVotes = new HashMap<>();
  private static final Gson gson = new Gson();
  private static final String ACTIVITY = "activity";
  private static final String JSON = "application/json";
  private static final String CHARTS_PAGE = "chart/html";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType(JSON);
    String json = gson.toJson(activityVotes);
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String activity = request.getParameter(ACTIVITY);
    int currentVotes = activityVotes.containsKey(activity) ? activityVotes.get(activity) : 0;
    activityVotes.put(activity, currentVotes + 1);

    response.sendRedirect(CHARTS_PAGE);
  }
}
