import axios from "axios";

const ITEM_BASE_REST_API_URL = "http://localhost:8080/api/items";

class ItemService{

    getAllItems(){
        
        return axios.get(ITEM_BASE_REST_API_URL);
    }

    getItemByID(id){        
        return axios.get(ITEM_BASE_REST_API_URL +'/' +id);
    }

    createItem(item){
        return axios.post(ITEM_BASE_REST_API_URL, item);
    }

    updateItem(id, item)
    {
        return axios.put(ITEM_BASE_REST_API_URL +'/' + id, item);
    }

    deleteItem(id)
    {
        return axios.delete(ITEM_BASE_REST_API_URL + '/' + id);
    }

    
}

export default new ItemService();