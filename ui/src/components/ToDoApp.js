import React from 'react'; 
import ListItemsComponent from './ListItemsComponent';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import ItemComponent from './ItemComponent';
 
export default class ToDoApp extends React.Component {

  render() 
  { 
    return (
      <Router>  
            <Switch>
              <Route path="/" exact component={ListItemsComponent} />
              <Route path="/items" exact component={ListItemsComponent} />
              <Route path="/items/:id" component={ItemComponent} />
               
            </Switch>  
      </Router>)
  }
 
} 