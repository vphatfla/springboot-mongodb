import logo from './logo.svg';
import './App.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import FooterComponent from './components/FooterComponent';
import HeaderComponent from './components/HeaderComponent';
import ListItemComponent from './components/ListItemComponent';
import AddItemComponent from './components/AddItemComponent';

function App() {

  return (
    <div>
      <Router>
        <HeaderComponent/>
          <div className='container'>
            <Routes>
              <Route exact path='/' element={<ListItemComponent />}></Route>
              <Route path='/items' element={<ListItemComponent />}></Route>
              <Route path="/add-item" element={<AddItemComponent />}></Route>
              <Route path="/edit-item/:id" element={<AddItemComponent />}></Route>
            </Routes>
          </div>
        <FooterComponent/>
      </Router>
    </div>
  );
}

export default App;
