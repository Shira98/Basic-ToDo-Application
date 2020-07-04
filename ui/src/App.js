import React from 'react';
import './App.css';

import ToDoApp from './components/ToDoApp';
import Footer from './components/footer';
import Header from './components/header';
 
export default class App extends React.Component {

  render() {
    return (
      <div id="app-container"> 
          <Header/>
          <ToDoApp /> 
          <Footer/>
      </div>
    );
  }
} 