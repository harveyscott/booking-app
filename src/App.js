import React, { Component } from 'react';
import './App.css';

class App extends Component {
  
  
  render() {
    return (
      <div className="App">
        <div className="App-header">
          <h2>Booking Application</h2>
        </div>
        <div id="padding"></div>
        <div id="form">
          <label> 
            <p>Number of people  </p>  
              <select>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
              </select>
          </label>
        </div>
      </div>
    );
  }
}

export default App;
