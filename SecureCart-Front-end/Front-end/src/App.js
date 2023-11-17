import React, {    } from "react";
 
import Login from "./component/login/Login";
import RegistrationForm from "./component/RegisterForm/RegistrationForm";
import Product from "./component/Product/Product";
import { createTheme } from "@mui/material/styles";
import { ThemeProvider } from "@mui/material/styles";
import { Route, Routes } from 'react-router-dom';
import MyOrders from "./component/Order/MyOrders"; 
  
import AddToCartIcon from "./component/Cart/AddToCartIcon";
 
 
 
 
 import axios from "axios";
 
 
import InActive from "./component/login/InActive";
import UserApprove from "./SuperAdmin/UserApprove";
import { url } from "./ServiceApi/ServiceApi";
import Category from "./component/Category/Category";
import Questions from "./component/Questions/Questions";
import UserMgmt from "./component/Usermgmt/Usermgmt";
import SellerPayment from "./component/SellerPayments/sellerPayments";
import AdminPayment from "./component/AdminPayments/adminPayments";
import AdminOrders from "./component/Order/AdminOrders";
// import Category from "./component/Category/Category";
 




export default function App() {

  const [cartCount, setCartCount] = React.useState();

  function getCartCount(){
    const data = {
      "userId": localStorage.getItem("userId"),
      "orderStatus": "ACTIVE"
    };
    axios.post(url+'cart/data', data
        , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
        .then(res => {
   
          {
            
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
const theme = createTheme({
  typography: {
   "fontFamily": `Saira`,
   "fontSize": '1.5 rem',
   "fontWeightLight": 300,
   "fontWeightRegular": 400,
   "fontWeight": 700
  }
});

  return (
    <ThemeProvider theme={theme}>
    <div className="App">

    {/* <LoginWithGoogle clientId="147377984925-tu3112ho13hjftfgt4jncd742qnn838e.apps.googleusercontent.com"></LoginWithGoogle> */}



    <Routes>
    {/* <Route path='/' element={isAuth ? <Navigate to="/" /> : <Login />} /> */}
      <Route  path="/" element={<Login/>} />
      <Route path="/register" element={<RegistrationForm/>} /> 
      <Route path="/home" element={<Product setCartCount={setCartCount} />} />
      <Route path="/user" element={<UserMgmt/>} />
      <Route path="/category" element={<Category/>} />
      <Route path="/myorders" element={<MyOrders setCartCount={setCartCount}/>}/>
      <Route path="/mycart" element={<AddToCartIcon setCartCount={setCartCount}/>}></Route>
      <Route path="/noaccess" element={<InActive/>}></Route>
      <Route path="/payments" element={<SellerPayment/>}></Route>
      <Route path="/questions" element={<Questions/>}></Route>
      <Route path="/admin-payments" element={<AdminPayment/>}></Route>
      <Route path="/orders" element={<AdminOrders/>}></Route>
     
      
     
   
      
     
    
   
        
        {/* <Route path="/LoginWithGoogle" element={<LoginWithGoogle/>}></Route> */}
        {/* LoginWithGoogle */}
  
          {/* Protected Routes starts from here */}
          {/* <Route element={<ProtectedRoutes auth={isAuth}/>}>
      
          <Route path="/home" element={<Product/>} />
      
          </Route> */}

       
    </Routes>



      
       

 

    </div>
    </ThemeProvider>
  );
}

