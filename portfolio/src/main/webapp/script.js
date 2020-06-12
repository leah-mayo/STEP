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



function startAuth() {
  handleCommentFormVisibility();
  initializeAuth();  
  console.log("Initializing authentication...");
}

/**
 * Adds a random fact to the page.
 */
function addRandomFact() {
  const facts =
     ['I am 20 years old, in the year 2020.','I have three siblings.','I like to make music.',
     'I like to code.', 'I like to play guitar.','I like to create.',
     'I like tech.', "I like to cook, but I'm still learning.", 'I like seafood.', 'I like tacos.',
     'I like to encourage people.', 'I interned at Google in Mountain View, Summer 2019.', 
     'I dabble in graphic design.'];

  // Pick a random greeting.
  const fact = facts[Math.floor(Math.random() * facts.length)];

  // Add it to the page.
  const factContainer = document.getElementById('fact-container');
  factContainer.innerText = fact;
}

/**
 * Adds a photo grid with different width/height options to the page
 */

// Get the elements with class="column"
var elements = document.getElementsByClassName("column");

// Full-width images
function one() {
    for (var i = 0; i < elements.length; i++) {
    elements[i].style.msFlex = "100%";  // IE10
    elements[i].style.flex = "100%";
  }
}

// Two images side by side
function two() {
  for (var i = 0; i < elements.length; i++) {
    elements[i].style.msFlex = "50%";  // IE10
    elements[i].style.flex = "50%";
  }
}

// Four images side by side
function four() {
  for (var i = 0; i < elements.length; i++) {
    elements[i].style.msFlex = "25%";  // IE10
    elements[i].style.flex = "25%";
  }
}

/**
 * Fetches comments from the server and adds them to the page
 */
var maxNumComments = 5;

function retrieveComments() {
  maxNumComments = document.getElementById("num-comments").value;
  console.log(`# of comments shown:${maxNumComments}`);
  fetch('/data?num-comments=' + maxNumComments).then(response => response.json()).then((commentList) => {
    console.log("retrieved comments: " + commentList);
    addComments(commentList);
  });
}

/**
 * Add comments to the page
 */
function addComments(commentList) {
    const commentListElement = document.getElementById("data-container");
    while (commentListElement.firstChild) {
        commentListElement.removeChild(commentListElement.firstChild);
    }

    for (comment of commentList) {
        commentListElement.appendChild(createCommentElement(comment));
    }   
}

function createCommentElement(comment) {
    let commentElement = document.createElement('div');
    commentElement.classList.add("Comment");
    
    let commentText = document.createElement('p');
    commentText.innerText = comment.text;
    commentElement.appendChild(commentText);

    return commentElement;
    
}

/**
 * Toggles comment visibility
 */
function toggleComments() {
    let comments = document.getElementById("data-container");
    comments.classList.toggle("gone");

    let view = document.getElementById("show-hide-comments");
    if (view.innerText == "Hide Comments") {
        view.innerText = "Show Comments";
    } else {
        view.innerText = "Hide Comments";
    }
}

/**
 * Retrieves auth info and displays the correct login interface
 */
function initializeAuth() {
  const loginStatusPromise = fetch("/auth");

  loginStatusPromise
    .then((response) => response.json())
    .then((loginInfo) => {
      const loginStatus = loginInfo["loginStatus"];
      const loginURL = loginInfo["loginURL"];
      const logoutURL = loginInfo["logoutURL"];

      const authButton = document.getElementById("auth-button");
      const authGreeting = document.getElementById("auth-greeting");

      if (loginStatus === "true") {
        authGreeting.innerHTML = "You're logged in:";
        retrieveComments()
        authButton.innerHTML = "Log Out";
        authButton.href = logoutURL;
        authButton.classList.add("logout");
        authButton.classList.remove("login");
      } else {
        authGreeting.innerHTML = "Please log in here: ";

        authButton.innerHTML = "Log In";
        authButton.href = loginURL;
        authButton.classList.add("login");
        authButton.classList.remove("logout");
      }
    });
}

/**
 * Hides comment form if user is not signed in. Otherwise display user form.
 */
function handleCommentFormVisibility() {
  const loginStatusPromise = fetch("/auth");

  loginStatusPromise
    .then((response) => response.json())
    .then((loginInfo) => {
      const loginStatus = loginInfo["loginStatus"];
      const commentForm = document.getElementById("comment-form");
      const dataDisplay = document.getElementById("data-display");
      const loginAlert = document.getElementById("comment-login-alert");
      if (loginStatus === "true") {
        commentForm.style.visibility = "visible";
        dataDisplay.styleVisibility = "visible";
        loginAlert.style.visibility = "collapse";
      } else {
        commentForm.style.visibility = "hidden";
        dataDisplay.styleVisibility = "hidden";
        loginAlert.style.visibility = "visible";
      }
    });
}
