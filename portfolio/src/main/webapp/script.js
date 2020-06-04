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
 * Fetches messages from the server
 */
function message() {
  fetch('/data').then(response => response.text()).then((message) => {
    document.getElementById('data-container').innerText = message;
  });
}