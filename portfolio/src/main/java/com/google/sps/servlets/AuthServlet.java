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

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.util.HashMap;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

public static final Gson gson = new Gson();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    UserService userService = UserServiceFactory.getUserService();
    
    HashMap<String, String> loginInfo = new HashMap<String, String>();
    loginInfo.put("loginStatus", Boolean.toString(userService.isUserLoggedIn()));
    loginInfo.put("loginURL", userService.createLoginURL("/index.html"));
    loginInfo.put("logoutURL", userService.createLogoutURL("/index.html"));

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(loginInfo));
  }
}