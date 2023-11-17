import React from 'react'
import axios from 'axios';

import { useState } from 'react';

import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import PayOrder from '../PayOrder';
import { useNavigate } from 'react-router-dom';



function MakePayment({ cartInfo, date, setLoading, paymentData }) {

   




  console.log(cartInfo);
  console.log(date);

  setLoading(true);
  var config = {
    "root": "",
    "style": {
      "bodyBackgroundColor": "#fafafb",
      "bodyColor": "",
      "themeBackgroundColor": "#0FB8C9",
      "themeColor": "#ffffff",
      "headerBackgroundColor": "#284055",
      "headerColor": "#ffffff",
      "errorColor": "",
      "successColor": "",
      "card": {
        "padding": "",
        "backgroundColor": ""
      }
    },
    "data": {
      "orderId": paymentData.order,
      "token": paymentData.token,
      "tokenType": "TXN_TOKEN",
      "amount": paymentData.amount /* update amount */
    },
    "payMode": {
      "labels": {},
      "filter": {
        "exclude": []
      },
      "order": [
        "CC",
        "DC",
        "NB",
        "UPI",
        "PPBL",
        "PPI",
        "BALANCE"
      ]
    },
    "website": "WEBSTAGING",
    "flow": "DEFAULT",
    "merchant": {
      "mid": paymentData.mid,
      "redirect": false
    },
    "handler": {
      "transactionStatus": function transactionStatus(paymentStatus) {
        console.log("paymentStatus => ", paymentStatus);
        handlePaymentApi(paymentStatus);
        console.log(paymentStatus.body.resultInfo.resultMsg);


        setLoading(false);
      },
      "notifyMerchant": function notifyMerchant(eventName, data) {
        console.log("Closed", eventName, data);
        setLoading(false);
      }
    }
  };

  if (window.Paytm && window.Paytm.CheckoutJS) {
    setLoading(false);
    // initialze configuration using init method
    window.Paytm.CheckoutJS.init(config).then(function onSuccess() {
      console.log('Before JS Checkout invoke');
      // after successfully update configuration invoke checkoutjs
      window.Paytm.CheckoutJS.invoke();
    }).catch(function onError(error) {
      setLoading(false);
      console.log("Error => ", error);
    });
  }

  
  function handlePaymentApi(paymentStatus) {
    console.log("------------------------------");
    console.log(paymentStatus.BANKNAME);

    const paymentStatus1 = {
      resultMsg: paymentStatus.RESPMSG,
      bankTxnId: paymentStatus.BANKTXNID,
      bankName: paymentStatus.BANKNAME,
      checkSumHash: paymentStatus.CHECKSUMHASH,
      currency: paymentStatus.CURRENCY,
      gatewayName: paymentStatus.GATEWAYNAME,
      merchantId: paymentStatus.MID,
      orderId: paymentStatus.ORDERID,
      paymentMode: paymentStatus.PAYMENTMODE,
      respoCode: paymentStatus.RESPCODE,
      status: paymentStatus.STATUS,
      txnAmount: paymentStatus.TXNAMOUNT,
      txnDate: paymentStatus.TXNDATE,
      txnId: paymentStatus.TXNID
    }

    console.log(paymentStatus1);

    axios.post('http://localhost:8080/orderPay', paymentStatus1, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('Token')}`,

      }
    })
      .then((response) => {

        console.log(response);

        if (response.data.status == 'PENDING') {
          console.log("--------------------")
          console.log(response.data.orderPayId);

          console.log("================================order payment call");
          const orderDetails = {
            "cartId": cartInfo,
            "userId": localStorage.getItem("userId"),
            "date": date,
            "orderPayId": response.data.orderPayId

          };
           orderApi(orderDetails);
          
  
        }
 
      })
      .catch((error) => {
        console.log(error);
      });



  }
  const navigate = useNavigate();
    
  const [open, setOpen] = React.useState(false); 
  const [message, setmessage] = useState('');


  const handleClose = (event, reason) => {
  
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
   
    function orderApi(orderDetails){
        console.log("================================order api call");
      axios.post('http://localhost:8080/order', orderDetails
      , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
      .then(res => {
        
        console.log(res.data);
        setOpen(true);
        setmessage('your order placed successfully...')
         
         setTimeout(function() {
          navigate('/myorders');
      }, 1000);
       
        
      })
      .catch(e => {
  
        console.log(e.response.data.message);
         setmessage(e.response.data.message);
      })
  
    }



  return (
    <div>

     
    <Snackbar
        open={open}
        autoHideDuration={6000}
        onClose={handleClose}
        message={message}
        anchorOrigin={{
          vertical: "top",
          horizontal: "right"
       }}
        action={action}
      />
 

 

    </div>
  )
}

export default MakePayment
