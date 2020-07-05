import React from 'react'; 
import '../css/header.css';  

export default class Header extends React.Component {

    render() {
        return(
            <center> 
                <div id="header">   
                    <div className="animate__animated animate__fadeIn"> 
                        <h1>To-Do List  
                            <h3>TRACK YOUR DAILY NEEDS</h3>  
                        </h1> 
                    </div>   
                </div> 
            </center>
        )  
    } 
}