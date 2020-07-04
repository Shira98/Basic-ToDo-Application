import React from 'react'; 
import '../css/header.css';
// import Icon from "../images/logo1.png"



export default class Header extends React.Component {

    render() {
        return(
            <center>
            <div id="header">   
                {/* <h1 className="logo">
                    <img className="rotate" src={Icon} alt="to-do logo" /> 
                </h1> */}
                <h1>
                    To-Do List         
                    <h3>TRACK YOUR DAILY NEEDS</h3>  
                </h1>  
                {/* <h3>Track your daily needs.</h3> */}
            </div>
            </center>
        )


    }
    
}