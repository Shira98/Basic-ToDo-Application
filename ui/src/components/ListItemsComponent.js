import React  from 'react'; 
import ItemsDataService from './ItemsDataService';
import { Formik, Form, Field } from 'formik'; 

export default class ListItemsComponent extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            items: [],
            new_item: "", 
            message: null
        }

        this.refreshList = this.refreshList.bind(this)
        this.deleteItemClicked = this.deleteItemClicked.bind(this)
        this.updateItemClicked = this.updateItemClicked.bind(this) 
        this.onSubmit = this.onSubmit.bind(this) 
        this.validate = this.validate.bind(this)

    }

    componentDidMount() {
        this.refreshList();
    }

    refreshList() {
        ItemsDataService.retrieveAllItems( ) 
            .then(
                response => {
                    console.log(response);
                    this.setState({ items: response.data })
                }
            )
    }

    
    deleteItemClicked(id) {
        ItemsDataService.deleteItem(id)
            .then(
                response => {
                    this.setState({ message: `Deleted item ${id} successfully!` })
                    this.refreshList()
                }
            )
    }

    updateItemClicked(id) {
        console.log('update ' + id)
        this.props.history.push(`/items/${id}`)
    }

    onSubmit(values, {resetForm}){
        let todo_new_item = { 
            item: values.new_item
        } 

        ItemsDataService.createItem(todo_new_item)
            .then(
                response => {
                    this.setState({ message: `New item added successfully!` }) 
                    resetForm({})
                    this.refreshList() 
                }
            )
    }

    validate(values){
        let errors = {}
        if(!values.new_item){
            errors.new_item = 'Please enter a description.';  
        }     
        return errors;
    }

    renderAdd(){ 
        return(   
            <div className="row">
                <div className="col-md-12">
                    <div  className="main-todo-input-wrap">
                        <Formik   

                            initialValues = {{new_item: ''}}

                            onSubmit={this.onSubmit} 
                            validateOnChange={false}
                            validateOnBlur={false} 
                            validate={this.validate}
                            enableReinitialize={true} 
                            
                        >  
                            { 
                            props =>  (
                                <div className="main-todo-input fl-wrap1">
                                    <Form >
                                        <div className="main-todo-input-item" style={{paddingRight: "0.4px"}}>
                                            <fieldset className="form-group"> 
                                                <Field className="form-control" id="todo-list-item" type="text" name="new_item" 
                                                        placeholder="What do you want to do?"  />
                                            </fieldset> 
                                        </div>
                                        <button className="add-items main-search-button btn btn-success" style={{backgroundColor: "#798199", borderRadius: "0px"}} type="submit" disabled={!props.dirty && props.isValid}>ADD ITEM</button>
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
  
    renderTable(){
        return(
            <div class="row">
                    <div class="col-md-12">
                        <div class="main-todo-input-wrap">
                            <table className="table main-todo-input fl-wrap todo-listing" style={{tableLayout:"fixed"}}>
                                <col width="30"/> 
                                    <col width="140"/> 
                                        <col width="100"/>  
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Description</th> 
                                                    <th style={{textAlign:"center"}} >Update/Delete</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            {
                                                !this.state.items.length ?
                                                
                                                <tr><td colspan="3" style={{textAlign:"center"}}><i>Please add an item to get started.</i></td></tr>
                                                :
                                                this.state.items.map(
                                                    item => 
                                                        <tr className="rows" id="list-items" key={item.id}>
                                                            <td>{item.id}</td>
                                                            <td style={{wordWrap:"break-word"}}>{item.item}</td>
                                                            <td style={{textAlign:"center"}} >
                                                                <button className="btn btn-success btn-icon" onClick={() => this.updateItemClicked(item.id)}><i class='fa fa-edit'></i></button>
                                                                <button className="btn btn-danger btn-icon" onClick={() => this.deleteItemClicked(item.id)}><i class='fa fa-close'></i></button>
                                                            </td>
                                                        </tr>
                                                )
                                            }
                                            </tbody>
                            </table> 
                        </div>
                    </div>
                </div>
            )
    }

    render() { 
        return ( 
            <div className="container" id="all-items-container">  
                {this.renderAdd()}     {/* Renders the add item input. */}
                {this.renderTable()}   {/* Renders the table of items if number of items are not zero. */}
            </div>   
        )
    }
    
} 