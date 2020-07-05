import React from 'react'; 
import '../css/footer.css';
 
export default class Footer extends React.Component {

    render() {
        return( 
            <div id="footer" >
                <footer>
                    <div className="animate__animated animate__fadeIn animate__delay-0.7s"> 
                        <h3>Created by D. Praneetha &copy; {(new Date().getFullYear())} <b>|</b> Powered by <a href="https://spring.io/projects/spring-boot" target="_blank" rel="noopener noreferrer">Spring Boot</a> and <a href="https://reactjs.org/" target="_blank" rel="noopener noreferrer">React</a></h3>    
                        <h3><a href="https://github.com/Shira98/Basic_ToDo_Application/tree/draft" target="_blank" rel="noopener noreferrer">GitHub Project</a></h3>
                    </div>
                </footer>
            </div>
        ) 
    } 
}