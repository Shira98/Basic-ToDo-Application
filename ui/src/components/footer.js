import React from 'react'; 
import '../css/footer.css';



export default class Footer extends React.Component {

    render() {
        return( 
            <div className="fixed-bottom">
                <footer>
                    <h3>To-Do List 1.0.0 <b>·</b> Created by D. Praneetha &copy; {(new Date().getFullYear())}</h3>
                    <h3>Powered by <a href="https://spring.io/projects/spring-boot">Spring Boot</a> and <a href="https://reactjs.org/">React</a>.</h3>
                    <h3><a href="https://github.com/Shira98/Basic_ToDo_Application/tree/draft">GitHub Project</a></h3>
                </footer>
            </div>
        )


    }
    
}