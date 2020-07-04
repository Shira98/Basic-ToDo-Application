import axios from 'axios';
 
const TODO_API_URL = 'http://localhost:8080';
const ALL_ITEMS_API_URL = `${TODO_API_URL}/ToDo`;

class ItemsDataService {

    retrieveAllItems( ) {
        return axios.get(`${ALL_ITEMS_API_URL}`);
    }
 
    deleteItem(id) {
        //console.log('executed service')
        return axios.delete(`${ALL_ITEMS_API_URL}/${id}`);
    }

    retrieveItem(id) {
        return axios.get(`${ALL_ITEMS_API_URL}/${id}`);
    }

    updateItem(item) {
        // console.log('UPDATE executed service')
        return axios.put(`${ALL_ITEMS_API_URL}`, item);
    }

    createItem(item) {
        // console.log('executed service')
        return axios.post(`${ALL_ITEMS_API_URL}`, item);
    }

}

export default new ItemsDataService() 

