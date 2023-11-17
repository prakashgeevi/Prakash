import React, {    } from "react";
 
import Login from "./component/login/Login";
import RegistrationForm from "./component/RegistrationForm";
import Product from "./component/Product";
import Category from "./component/Category";
 
import { Route, Routes } from 'react-router-dom';
import MyOrders from "./component/MyOrders"; 
  
import AddToCartIcon from "./component/AddToCartIcon";
 
import FacebookButton from "./component/FacebookButton";
import Footer from "./component/Footer";
 
 
 import axios from "axios";
import Paypal from "./component/login/Paypal";   
import PaytmButton from "./component/Paytm/PaytmButton";
import XmlConversion from "./component/XmlConversion";
 




export default function App() {

  const [cartCount, setCartCount] = React.useState();

  function getCartCount(){
    const data = {
      "userId": localStorage.getItem("userId"),
      "orderStatus": "ACTIVE"
    };
    axios.post('http://localhost:8080/cart/data', data
        , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
        .then(res => {
   
          {
            console.log("------", res.data.item.length);
           setCartCount( res.data.item.length);
          }
  
  
        })
        .catch(e => {
  
          console.log(e.message);
  
        })
  }


  getCartCount();




//   const ProtectedRoutes = ({auth}) => {
    
//     return (auth === true ? <Outlet /> : <Navigate to="/" replace/>)
// }

  return (
    <div className="App">

    {/* <LoginWithGoogle clientId="147377984925-tu3112ho13hjftfgt4jncd742qnn838e.apps.googleusercontent.com"></LoginWithGoogle> */}



    <Routes>
    {/* <Route path='/' element={isAuth ? <Navigate to="/" /> : <Login />} /> */}
      <Route  path="/" element={<Login/>} />
      <Route path="/register" element={<RegistrationForm/>} /> 
      <Route path="/home" element={<Product setCartCount={setCartCount} />} />
      <Route path="/category" element={<Category/>} />
      <Route path="/myorders" element={<MyOrders setCartCount={setCartCount}/>}/>
      <Route path="/mycart" element={<AddToCartIcon setCartCount={setCartCount}/>}></Route>
      
      <Route path="/footer" element={<Footer/>}></Route>
      <Route path="/facebook" element={<FacebookButton/>}></Route>
     
      <Route path="/paypal" element={<Paypal/>}></Route>
      
      <Route path="/paytm" element={<PaytmButton/>}></Route>
      <Route path="/xml" element={<XmlConversion/>}></Route>
       
   
        
        {/* <Route path="/LoginWithGoogle" element={<LoginWithGoogle/>}></Route> */}
        {/* LoginWithGoogle */}
  
          {/* Protected Routes starts from here */}
          {/* <Route element={<ProtectedRoutes auth={isAuth}/>}>
      
          <Route path="/home" element={<Product/>} />
      
          </Route> */}

       
    </Routes>



      
       

 

    </div>
  );
}

