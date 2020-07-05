import axios from 'axios';
 
const TODO_API_URL = 'http://localhost:8080';
const ALL_ITEMS_API_URL = `${TODO_API_URL}/ToDo`;

export default new class ItemsDataService {

    retrieveAllItems( ) {
        //console.log('executed retrieveAllItems service')
        return axios.get(`${ALL_ITEMS_API_URL}`);
    }
 
    deleteItem(id) {
        //console.log('executed deleteItem service')
        return axios.delete(`${ALL_ITEMS_API_URL}/${id}`);
    }

    retrieveItem(id) {
        //console.log('executed retrieveItem service')
        return axios.get(`${ALL_ITEMS_API_URL}/${id}`);
    }

    updateItem(item) {
        //console.log('executed updateItem service')
        return axios.put(`${ALL_ITEMS_API_URL}`, item);
    }

    createItem(item) {
        //console.log('executed createItem service')
        return axios.post(`${ALL_ITEMS_API_URL}`, item);
    } 
}