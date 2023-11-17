import React from 'react'
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useState, useEffect } from 'react';
import Grid from '@mui/material/Grid';
import Button from '@mui/material/Button';
import GridItem from './GridItem';
import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import '../Cart/CSS/Shopping.css'
import Navbar from '../Navbar/Navbar';
import { sendOtp, url, verifyOtp } from '../../ServiceApi/ServiceApi';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';
import { FormControl, InputLabel, MenuItem, Select, Typography } from '@mui/material';
import DuitnowImg from './images/Duitnow.png';
import GrabpayImg from './images/Grabpay.png';
import OTPInput from 'react-otp-input';


function AddToCartIcon({ cartCount1 }) {

  if (!cartCount1) {
    cartCount1 = 0;
  }

  const [orderOpen, orderSetOpen] = React.useState(false);
  


  const handlePaymentDialogClose = () => {
    orderSetOpen(false);
  };








  const navigate = useNavigate();
  const [open, setOpen] = React.useState(false);
  const [cartCount, setCartCount] = React.useState(cartCount1);

  const handleClose = (event, reason) => {
    //navigate('/myorders');
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
  async function handleGetCartItem() {
    try {
      await axios.post(url + 'cart/data', data
        , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
        .then(res => {
          if(res.data){
            console.log(res.data);
            setCartId(res.data.cartId)
            setCartInfo(res.data.item);
            console.log(res.data);
            console.log(res.data.cartId);
            axios.post(url + 'cart/data', data
              , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
              .then(res => {
                {
                  setCartCount(res.data.item.length);
                }
              })
              .catch(e => {
                console.log(e.message);
              });
          }
          
        })
    } catch (error) {
      console.error(error);
      if (!cartInfo) {
        return navigate('/not-found');
      }
    }
  }
  const totalAmount = [];
  {
    cartInfo.map((e) => (
      totalAmount.push(e.totalPrice)
    ))
  }
  let sum = 0;
  for (let i = 0; i < totalAmount.length; i++) {
    sum += totalAmount[i];
  }
  const [message, setmessage] = useState('');
  const [payButton, setPayButton] = useState(false);

  const date = new Date();


  const [otpOpen, setOtpOpen] = useState("");
  const [otp, setotp] = useState("");
  const [cardType, setCardType] = useState("");
  const [cvvNumber, setCvvNumber] = useState("");

  const [paymentOption, setPaymentOption] = React.useState("");
  const [referenceNumber, setReferenceNumber] = React.useState("");
  const closeOtpModal = () => {
    setOtpOpen(false);
  }
  const handlePaymentOption  = (e) => {
    setPaymentOption(e.target.value);
    if(e.target.value !== ""){
      setReferenceNumber(Math.floor(100000000 + Math.random() * 900000000));
    }
  }

  const handleMyOrders = () => {

    sendOtp(localStorage.getItem('email')).then(result => {
      setOpen(true);
      setmessage('OTP sent to your email');
      setOtpOpen(true);
    })
      .catch(error => {
        console.log(error);
        setOpen(true);
        setmessage('OTP fail to sent your email');
        console.log(error)
      })

    



    if (cartid == 0) {
      setmessage('Cart is empty You Add to some item your cart');
      return;
    } else {
    }
  }


  const submitOtp = () => {
    verifyOtp(localStorage.getItem('email'), otp).then(result => {
      setOtpOpen(false);
      setOpen(true);
      setmessage('OTP verified successfully!');
      orderSetOpen(true);
    })
      .catch(error => {
        console.log(error);
        setOpen(true);
        setmessage('OTP verification failed');
        console.log(error)
      })
  }

  const submitMyOrders = () => {

    if (cartid == 0) {
      setmessage('Cart is empty You Add to some item your cart');
      setOpen(true);
      return;
    } else {
    }
    if (!paymentOption || !referenceNumber) {
      setmessage('Fields cannot be blank')
      setOpen(true);
    } else {
      const orderDetails = {
        "referenceNumber": referenceNumber,
        "paymentOption": paymentOption,
        "cartId": cartid,
        "userId": localStorage.getItem("userId"),
        "date": date
      };
      axios.post(url + 'order', orderDetails
        , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
        .then(res => {
          console.log(res.data);
          setOpen(true);
          setmessage('your order placed successfully...Please check the status in My orders tab')
          setCartId(0);
          orderSetOpen(false);
          navigate('/myorders');
        })
        .catch(e => {
          console.log(e);
          //setmessage(e.response.data.message);
        })
    }



  };




  return (
    <div>
      <Navbar itemCount2={cartCount} />
      <br />
      <br />
      <br />
      {payButton ? (
        ""
      ) : (
        <>
        </>
      )
      }
      <Grid container spacing={2}>
        <Grid item xs={10} style={{ marginTop: 13, backgroundColor: '#ffffff' }}>
          <h2><b>Shopping Cart</b></h2>
          <Grid container spacing={2}  >
            {cartInfo.map((e) => (
              <Grid item xs={12}>
                <GridItem handleGetCartItem={handleGetCartItem} productImage={e.product.imageData} productId={e.product.productId} productName={e.product.productName} stock={e.product.stocks}
                  quantity={e.quantity} price={e.product.price} totalprice={e.totalPrice} itemid={e.itemId}
                  cartId={cartid} />
              </Grid>
            ))}
          </Grid>
        </Grid>
        <Grid item xs={2}>
          <div style={{ position: 'fixed', marginTop: 50 }}>
            <p style={{}}><strong>Total:</strong> &nbsp; <span><b>RM</b></span>&nbsp;<b>{sum}</b></p>
            
            {
              sum!==0 ? (
                <p style={{ color: 'green', paddingRight: 10 }}>Your order is eligible to check out</p>
              ): (
                <p style={{ color: 'green', paddingRight: 10 }}>Not items in cart, Your order is not eligible to check out</p>
              )
            }
            
            <Button onClick={handleMyOrders} 
            disabled={sum===0}
            style={{ borderRadius: 20, backgroundColor: '#f0de16', color: '#000000' }}>
              <span style={{ fontWeight: 500, fontSize: 14 }}><b>Proceed to Buy</b></span>
            </Button>
          </div>
        </Grid>
      </Grid>

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


      <Dialog open={orderOpen}>
        <DialogTitle>
          <div className="d-flex justify-content-between">
            <Typography fontSize={'1.7rem'}> Payment Details</Typography>
            <Typography fontSize={'1.7rem'}>Total&nbsp;&nbsp; RM&nbsp;{sum}</Typography>
          </div>

        </DialogTitle>
        <DialogContent>
          <DialogContentText>

          </DialogContentText>
          <FormControl fullWidth size='small'  margin="dense">
                    <InputLabel id="demo-simple-select-label">Select payment option</InputLabel>
                    <Select
                      labelId="demo-simple-select-label"
                      id="demo-simple-select"
                      value={paymentOption}
                      size='small'
                      label="Select payment option"
                      onChange={handlePaymentOption}
                    >
                      <MenuItem value={"DuitNow"}>DuitNow</MenuItem>
                      <MenuItem value={"Grabpay"}>Grabpay</MenuItem>
                    </Select>
                  </FormControl>

                {
                  paymentOption==="DuitNow" ?
                  (
                        <img src={DuitnowImg} height='300' width="300" ></img>
                  ) : ""
                }
                 {
                  paymentOption==="Grabpay" ?  
                  (
                        <img src={GrabpayImg} height='300' width="300"></img>
                  ):""
                  
                }


          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Reference number"
            fullWidth
            disabled
            variant="outlined"
            value={referenceNumber}
            onChange={(e) => setReferenceNumber(e.target.value)}
          />
          


        </DialogContent>
        <DialogActions>
          <Button variant='contained' color='secondary' onClick={handlePaymentDialogClose}>Cancel</Button>
          <Button variant='contained' color='success' onClick={submitMyOrders}>PAYMENT DONE</Button>
        </DialogActions>
      </Dialog>



      <Dialog open={otpOpen} >
        <DialogTitle>
          <div className="d-flex justify-content-between">
            <Typography fontSize={'1.7rem'}>Enter your OTP</Typography>
           
          </div>

        </DialogTitle>
        <DialogContent>
        <OTPInput
              value={otp}
              onChange={setotp}
              inputStyle={{width:'40px'}}
              numInputs={6}
              renderSeparator={<span>-</span>}
              renderInput={(props) => <input {...props} />}
            />

        </DialogContent>
        <DialogActions>
          <Button variant='contained' color='secondary' onClick={closeOtpModal}>Cancel</Button>
          <Button variant='contained' color='success' onClick={submitOtp}>SUBMIT</Button>
        </DialogActions>
      </Dialog>








    </div>
  )
}
export default AddToCartIcon
