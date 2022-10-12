import userEvent from '@testing-library/user-event';
import React, {useEffect, useState} from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import ItemService from '../services/ItemService';

const AddItemComponent = () => {

    const [name, setName] = useState('');
    const [quantity, setQuantity] = useState('');
    const [category, setCategory] = useState('');
    const {id} =useParams();
    const navigate =useNavigate();
    const saveOrUpdateItem = (e) => {
        console.log('run saveorupdate');
        console.log(name + '  ' + quantity + '  ' + category)
        e.preventDefault();

        const item = {name, quantity, category};

        if (id) {
            ItemService.updateItem(id, item).then((res) => {
                navigate('/items');
            }).catch(err => console.log(err));
        } else {
            ItemService.createItem(item).then((res) => {
                console.log(res.data);
                navigate('/items');
            }).catch(err => console.log(err));
        }
    }

    useEffect(() => {
        ItemService.getItemByID(id).then((res) => {
            setName(res.data.name);
            setQuantity(res.data.quantity);
            setCategory(res.data.category);
        }).catch(err => console.log(err));
    },[])

    const title = () => {

        if(id){
            return <h2 className = "text-center">Update Item</h2>
        }else{
            return <h2 className = "text-center">Add Item</h2>
        }
    }

    return (
        <div>
           <br /><br />
           <div className = "container">
                <div className = "row">
                    <div className = "card col-md-6 offset-md-3 offset-md-3">
                       {
                           title()
                       }
                        <div className = "card-body">
                            <form>
                                <div className = "form-group mb-2">
                                    <label className = "form-label"> Item Name :</label>
                                    <input
                                        type = "text"
                                        placeholder = {name}
                                        name = "name"
                                        className = "form-control"
                                        
                                        onChange = {(e) => {
                                            console.log(e.target.value);
                                            setName(e.target.value);

                                        }}
                                    >
                                    </input>
                                </div>

                                <div className = "form-group mb-2">
                                    <label className = "form-label"> Quantity :</label>
                                    <input
                                        type = "text"
                                        placeholder = {quantity}
                                        name = "quantity"
                                        className = "form-control"
                                        onChange = {(e) => setQuantity(e.target.value)}
                                    >
                                    </input>
                                </div>

                                <div className = "form-group mb-2">
                                    <label className = "form-label"> Category</label>
                                    <input
                                        type = "text"
                                        placeholder = {category}
                                        name = "category"
                                        className = "form-control"
                                        onChange = {(e) => setCategory(e.target.value)}
                                    >
                                    </input>
                                </div>

                                <button className = "btn btn-success" onClick = {(e) => saveOrUpdateItem(e)} >Submit </button>
                                <Link to="/items" className="btn btn-danger"> Cancel </Link>
                            </form>

                        </div>
                    </div>
                </div>

           </div>

        </div>
    )
}

export default AddItemComponent;