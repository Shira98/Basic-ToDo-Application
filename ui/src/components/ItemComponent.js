import React from 'react'; 
import ItemsDataService from './ItemsDataService';
import { Formik, Form, Field, ErrorMessage } from 'formik'; 
// import Spinner from 'react-bootstrap/Spinner';
import FadeIn from 'react-fade-in';


export default class ItemComponent extends React.Component {
    
    constructor(props) {
        super(props)
        this.state = {
            id: this.props.match.params.id, 
            item: '',
            message: ''
        }

        this.onSubmit = this.onSubmit.bind(this)
        this.validate = this.validate.bind(this)
        this.backClicked = this.backClicked.bind(this)
    }
    
    componentDidMount() { 
        console.log(this.state.id)   

        ItemsDataService.retrieveItem(this.state.id)
            .then(response => this.setState({
                item: response.data.item   
            })) 
    }

    validate(values) {
        let errors = {}
        console.log("From validate;",values.item);
        if (!values.item) {
            errors.item = 'Please enter a description.'
        }   

        return errors
    }

    onSubmit(values) { 
        let todo_item = {
            id: this.state.id, 
            item: values.item
        }  
 
        ItemsDataService.updateItem(todo_item)
        .then(
            response => {
                this.setState({ message: `Success! Redirecting...` })
                document.getElementById("disable1").disabled = true;
                document.getElementById("disable2").disabled = true;
                document.getElementById("disable3").disabled = true;  
                document.getElementById("disable").style.pointerEvents= "none"; 
                this.timer = setTimeout(() => this.props.history.push('/items'), 3000);
            } 
        ) 
    }

    backClicked(){
        this.props.history.push(`/items`)
    }
    
    renderID(){ 
        return( 
            <fieldset className="form-group">
                <label>Item's ID</label>
                <Field className="form-control" type="text" name="id" disabled />
            </fieldset> 
        )
    }

    renderDescription(){ 
        return(  
            <fieldset className="form-group">
                <label>Edit Item's Description</label>
                <Field className="form-control"  id="disable3" type="text" name="item"  placeholder="Please enter a description." />
            </fieldset>  
        ) 
    }
 
    renderUpdateScreen() { 
        let { item, id } = this.state
        
        return (
            <div className="row">
                <div className="col-md-12">
                    <div  className="main-todo-input-wrap" id="disable">
                        <Formik 
                            initialValues= {{id:id, item:item}}

                            onSubmit={this.onSubmit}
                            validateOnChange={false}
                            validateOnBlur={false}
                            validate={this.validate}
                            enableReinitialize={true}
                        >  
                            { 
                                props => ( 
                                    <div className="main-todo-input fl-wrap" style={{display:"block" ,textAlign:"center", padding: "20px 0px 20px 0px"}} >
                                    <Form style={{display: "inline-block"}} className="updateItem"> 
                                        <ErrorMessage name="item" component="div" className="alert alert-warning animate__animated animate__fadeIn" /> 
                                        
                                        {this.state.message && <div class="alert alert-success animate__animated animate__fadeIn">{this.state.message}</div>}
                                        
                                        {this.renderID()}               {/* Renders the item's ID. */ }
                                        {this.renderDescription()}      {/* Renders the item's existing description. */ }
                                        
                                        <button className="btn btn-success"  id="disable1" style={{backgroundColor: "#798199"}} type="submit" disabled={!props.dirty && props.isValid}>SAVE</button>
                                        &nbsp;&nbsp;
                                        <button className="btn btn-success" id="disable2" style={{backgroundColor: "#798199"}} onClick={() => this.backClicked()}>BACK</button>
                                    </Form>
                                    </div> 
                                )
                            }
                        </Formik>  
                    </div>
                </div>
            </div>
        )
    }

    render(){
        return(
            <FadeIn>
                <div className="container">  
                    {this.renderUpdateScreen()} {/* Renders the update item form. */}
                </div>
            </FadeIn>
        )
    }
} 