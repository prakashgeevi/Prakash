import React from 'react'
import Navbar from './Navbar'
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useState, useEffect } from 'react';
import Grid from '@mui/material/Grid';
import Button from '@mui/material/Button';
 
 
import GridItem from './GridItem';
 
import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import '../CSS/Shopping.css'
import PaytmButton from './Paytm/PaytmButton';

 

 



function AddToCartIcon({cartCount1}) {

const navigate = useNavigate();

const [open, setOpen] = React.useState(false);
 
const [cartCount, setCartCount] = React.useState(cartCount1);

const handleClose = (event, reason) => {
    navigate('/home');
  if (reason === 'clickaway') {
  
    return;
  }

  setOpen(false);
};

const action = (
  <React.Fragment>
     
    <IconButton
      size="small"
      aria-label="close"
      color="inherit"
      onClick={handleClose}
    >
      <CloseIcon fontSize="small" />
    </IconButton>
  </React.Fragment>
);




 
 




const data = {
    "userId": localStorage.getItem("userId"),
    "orderStatus": "ACTIVE"
  };

  const [cartInfo, setCartInfo] = useState([]);
    const [cartid, setCartId] = useState(0);
    
   useEffect(() => {
      
        handleGetCartItem();
  }, []);


  async function handleGetCartItem(){

    try {
    await axios.post('http://localhost:8080/cart/data', data
    , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
    .then(res => {
        setCartId(res.data.cartId)
        setCartInfo(res.data.item);
        console.log(res.data);
       console.log(res.data.cartId);
       
       axios.post('http://localhost:8080/cart/data', data
      , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
      .then(res => {

        {
          setCartCount(res.data.item.length);
        }

      

      })
      .catch(e => {

        console.log(e.message);

      });




    })
    } catch (error) {

    console.error(error);

    if(!cartInfo){
        return navigate('/not-found');
    }


    }

}


  const totalAmount = [];
  {cartInfo.map((e) => (
    
    totalAmount.push(e.totalPrice)
    
))}

 let sum=0;

for (let i = 0; i < totalAmount.length; i++) {
    sum += totalAmount[i];
}
 


                // async function handleClickOpenCart() {
                
                //   try {
                //     await axios.post('http://localhost:8080/cart/data', data
                //     , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
                //     .then(res => {
                //       setCartId(res.data.cartId)
                //       setCartInfo(res.data.item);

                //       console.log(cartInfo);

                //     })
                //   } catch (error) {

                //     console.error(error);

                //     if(!cartInfo){
                //       return navigate('/not-found');
                //     }


                //   }

                const [message, setmessage] = useState('');

 
      const[payButton, setPayButton] = useState(false);
         
      const handleMyOrders = () =>{
         
         if(cartid==0){
          setOpen(true);
          setmessage('Cart is empty You Add to some item your cart');
            return;
        }
            setPayButton(!payButton);

          } 

    const date = new Date();

  const handleMyOrders1 = () =>{
     
    setOpen(true);
    
    
    if(cartid==0){
      setmessage('Cart is empty You Add to some item your cart');
        return;
    }else {
 

    }
   
    const orderDetails = {
      "cartId": cartid,
      "userId": localStorage.getItem("userId"),
      "date": date

    };

    axios.post('http://localhost:8080/order/user', orderDetails
    , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
    .then(res => {
      
      console.log(res.data);
      setmessage('your order placed successfully...')
      setCartId(0);
      
      
    })
    .catch(e => {

      console.log(e.response.data.message);
      setmessage(e.response.data.message);
    })



  };
 


  


    
  return (
    <div>
      <Navbar itemCount2 = {cartCount}/>
        <br/>
        <br/>
        <br/>
        
 
        

                {payButton ? (

        <PaytmButton cartInfo={cartid}  date={date} totalAmount={sum} loadingValue={payButton}/>
        ) : (
        <>




        </>
        )
        }





<Grid container spacing={2}>
       
       <Grid item xs={10} style={{marginTop:13,backgroundColor:'#ffffff'}}>
         <h2><b>Shopping Cart</b></h2>
         
         
         <Grid container spacing={2}  >
        
         {cartInfo.map((e) => (
                <Grid item xs={12}>
    <GridItem handleGetCartItem ={handleGetCartItem} productImage={e.product.imageData} productId={e.product.productId} productName={e.product.productName} stock={e.product.stocks}
               quantity={e.quantity} price={e.product.price} totalprice={e.totalPrice} itemid={e.itemId}
               cartId={cartid} />
              
           </Grid>
         ))}

         </Grid>
      
          </Grid>  


          <Grid  item xs={2}>
           <div style={{position:'fixed', marginTop:50}}>
           <p style={{}}><strong>Total:</strong> &nbsp; <span><b>&#x20B9;</b></span>&nbsp;<b>{sum}</b></p>
           <p style={{color:'green', paddingRight:10}}>Your order is eligible to check out</p>
           
           <Button onClick={handleMyOrders}   style={{borderRadius:20, backgroundColor:'#f0de16' ,color:'#000000'}}>
             <span style={{fontWeight:500, fontSize:14}}><b>Proceed to Buy</b></span>
           </Button>

         
           </div>
       
       </Grid>

           </Grid>





    
           

           
  

 
            

            {/* <Grid item xs={2}> */}
               
    {/* <img src={"data:image/png;base64,"+e.product.productImage.imageData} style={{border:'1px solid #ddd', borderRadius:'4px', padding:5, width:'200px', objectFit:'contain' }} alt={e.product.productName}/>  */}
                {/* </Grid>
                </div> */}
                
    {/* {cartInfo.map((e) => (
                  
                <Grid item xs={10}>
                    
                <p>ProductName: {e.product.productName}</p>
                <p> Price: {e.product.price} x {e.quantity}</p>
                <p>TotalPrice: {e.totalPrice}</p>
              <Button color="secondary" 
            //   onClick={handleClose}
            >
                <DeleteIcon/></Button>

               </Grid>
              
            
          ))} */}



         
          {/* <Grid item xs={2}>
            <p style={{textAlign:'right'}}>product image</p>
          </Grid>
          <Grid item xs={10} >
          <p style={{textAlign:'center'}}>product content</p>
            </Grid> */}
          
        
            {/* </Grid>

            <h5 style={{textAlign:'right', paddingRight:10}}>subTotal: {sum}</h5>


        </Grid>

         */}
          
    <Snackbar
        open={open}
        autoHideDuration={6000}
        onClose={handleClose}
        message={message}
        anchorOrigin={{
          vertical: "top",
          horizontal: "center"
       }}
        action={action}
      />
      
      

     
      
      </div>  
        
 
  )
}

export default AddToCartIcon
