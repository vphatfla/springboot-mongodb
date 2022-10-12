
import React, {useState, useEffect} from "react";
import {Link} from 'react-router-dom';
import ItemService from "../services/ItemService";

const ListItemComponent =() => {

    const [items, setItems] = useState([]);


    useEffect(() => {
        getAllItems();
    },[]);

    const getAllItems =() =>{
        ItemService.getAllItems().then((res) => {
            setItems(res.data._embedded.groceryItemList);
        }).catch(err => {
            console.log(err);
        })
    }

    const deleteItem =(id) => {
        ItemService.deleteItem(id).then((res) => {
            getAllItems();
        }).catch(err => console.log(err));
    }

    return(
        <div className = "container">
            <h2 className = "text-center"> List Items </h2>
            <Link to = "/add-item" className = "btn btn-primary mb-2" > Add Items </Link>
            <table className="table table-bordered table-striped">
                <thead>
                    <th> Item Name </th>
                    <th> Item Quantity </th>
                    <th> Item Category </th>
                    <th> Actions </th>
                </thead>
                <tbody>
                    {
                        items.map(
                            item =>
                            <tr key = {item.id}> 
                                
                                <td> {item.name} </td>
                                <td>{item.quantity}</td>
                                <td>{item.category}</td>
                                <td>
                                    <Link className="btn btn-info" to={`/edit-item/${item.id}`} >Update</Link>
                                    <button className = "btn btn-danger" onClick = {() => deleteItem(item.id)}
                                    style = {{marginLeft:"10px"}}> Delete</button>
                                </td>
                            </tr>
                        )
                    }
                </tbody>
            </table>
        </div>    
    
    )

}

export default ListItemComponent;