import React, { useState, useEffect } from 'react'
import axios from 'axios';
 
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import Button from '@mui/material/Button';
import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
 
import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';
import Box from '@mui/material/Box';

import { styled } from '@mui/material/styles';

import Typography from '@mui/material/Typography';

import ButtonGroup from '@mui/material/ButtonGroup';
 
 
 


const ProductAdd = ({ productId, handleCartClose, setCartCount}) => {

  const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
  }));

const [product, setproduct]= useState([]);
const [price, setPrice] = useState(0);
const [price1, setPrice1] = useState(0);
const [image, setImage] = useState('');
const [stock, setStock] = useState(0);
const [unit, setUnit] = useState();
  
useEffect(() => {
    axios.get('http://localhost:8080/product/' + productId, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('Token')}`

      }
    })
      .then((res) => {
             
            setproduct(res.data);
            
            setPrice1(res.data.price);
            setPrice(res.data.price);
            setImage(res.data.imageData)
            setStock(res.data.stocks);
            setUnit(res.data.unit);
           
      })
      .catch((error) => {
       
          console.log(error.message);

         
      });

    },[]);

     
    
  

   
    
  const handleaddItem = () => {
    
    setOpen(true);
    const cartData = {
      "quantity": quantity,
			"productId": productId,
				"userId": localStorage.getItem("userId")   
    };
    const data = {
      "userId": localStorage.getItem("userId"),
      "orderStatus": "ACTIVE"
    };
   axios.post('http://localhost:8080/cart', JSON.stringify(cartData), {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('Token')}`,
        'Content-Type': 'application/json; charset=utf-8'
      }
    })
      .then((response) => {
        console.log(response.data);
        
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
       
      setmessage('product add to in cart');
       
      setTimeout(function() {
        handleCartClose();
    }, 1000);


       
      

      })
      .catch((error) => {
        
        setmessage(error.response.data.message);
        
         
      });

     
     
  }
 
  
  const [quantity, setQuantity] = useState(1);
   
 
 
   

  const handleQuantityChange = () => {
      



    if(quantity < stock)
    {
        
      setQuantity(quantity + 1);
      setPrice(price1 * (quantity + 1));
    }
    else {
      setOpen(true);
      setmessage("Can't add more then "+stock+" quantity. Because low stock");
      return;
    }
   
   
    
     
   

   


  };

  const handleQuantityLess = () => {
     if(quantity>1){
    setQuantity(quantity - 1);
     }
     if(quantity>1){
      setPrice(price1 * (quantity - 1));
       }
  
  };

  const [message, setmessage] = useState('');

 const [open, setOpen] = React.useState(false);

  

 const handleClose = (event, reason) => {
   if (reason === 'clickaway') {
      handleCartClose();
      
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


 const CustomButton = ({ onClick, color, text }) => {
  return (
    <Button style={{borderRadius:50}} variant="contained" color={color} onClick={onClick}>
      {text}
    </Button>
  );
}







  return (
    <div className="product1">
   
          
              <DialogContent>
              
              <Box sx={{ width: '100%' }}>
      <Grid container rowSpacing={1} columnSpacing={{ xs: 1, sm: 2, md: 3 }}>
        <Grid item xs={4}>
          

              <img src={"data:image/png;base64,"+image} style={{   border:'1px solid #ddd', borderRadius:'4px', padding:5, width:'200px' }} alt={product.productName}/>
               
              </Grid>
              <Grid item xs={3}> </Grid>
        <Grid   item xs={5}>
           <Item>
            <h2>{product.productName}</h2>
             
            <p style={{fontSize:16}}><strong>Price: {price1}</strong></p>
           
            <ButtonGroup size="small" aria-label="small outlined button group">
           
            <Button onClick={handleQuantityLess}>-</Button>
            <Button > {quantity} </Button>
            <Button onClick={handleQuantityChange}>+</Button>
          

            
            </ButtonGroup>
            <span style={{paddingLeft:5, fontSize:14}}><strong>{unit}</strong></span>
            {/* <button onClick={handleQuantityChange}>+</button>
              <span>
                   
              </span>
     
              <button onClick={handleQuantityLess}>-</button> */}
              </Item>
              <Typography style={{marginTop:10, marginLeft:7, color:'Highlight'}} variant="h6" component="h6">
              Total Price:   <span style={{color:'#063970'}}>&#x20B9; {price} </span>
</Typography>

              
              </Grid>
      </Grid>
    </Box>
                   
              </DialogContent>
              <DialogActions>
              <CustomButton color="secondary" text='Cancel'  onClick={handleCartClose}>Cancel</CustomButton >
        <CustomButton color="primary" text='Add To Cart'  onClick={handleaddItem} >Add To Cart</CustomButton >
              </DialogActions>
              
             


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

export default ProductAdd
