import React from 'react'; 
import '../css/header.css';



export default class Header extends React.Component {

    render() {
        return(
            <div className="fixed-top">  
                <div color="dark" dark>
                    <div className="header-container">
                        <h1>To-Do <small>List</small></h1>
                        <h3>TRACK YOUR DAILY NEEDS.</h3> 
                    </div>
                </div>
            </div>
             
        )


    }
    
}