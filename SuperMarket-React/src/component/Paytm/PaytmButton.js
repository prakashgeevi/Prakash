 
import React, { useEffect, useState } from "react"
import MakePayment from "./MakePayment";

import './paytm.css';


const PaytmChecksum = require('./Paytmchecksum');
const https = require('https');


function PaytmButton({cartInfo, date, totalAmount, loadingValue}) {

  console.log(cartInfo);

    const [paymentData, setPaymentData] = useState({
        token: "", 
        order: "", 
        mid: "",
        amount: ""
    });
    const [loading, setLoading] = useState(loadingValue);

          

    useEffect(() => {
        initialize();
       
    }, []);

    const initialize = () => {
        let orderId = 'Order_'+new Date().getTime();

        // Sandbox Credentials
        let mid = "ZNTnDY57303338319383"; // Merchant ID
        let mkey = "5cckQkbkekq8fuJ&"; // Merhcant Key
        var paytmParams = {};

        paytmParams.body = {
           "requestType" : "Payment",
            "mid"    : mid,
          "websiteName"  : "WEBSTAGING",
          "orderId"   : orderId,
         "callbackUrl"  : "https://merchant.com/callback",
         "txnAmount"  : {
          "value"  : totalAmount,
          "currency" : "INR",
         },
         "userInfo"  : {
          "custId" : '1001',
         }
        };

        PaytmChecksum.generateSignature(JSON.stringify(paytmParams.body), mkey).then(function(checksum){
            console.log(checksum);
         paytmParams.head = {
          "signature": checksum
         };

         var post_data = JSON.stringify(paytmParams);

         var options = {
          /* for Staging */
          //hostname: 'securegw-stage.paytm.in',

          /* for Production */
             hostname: 'securegw.paytm.in',

          port: 443,
          path: `/theia/api/v1/initiateTransaction?mid=${mid}&orderId=${orderId}`,
          method: 'POST',
          data: post_data,
          headers: {
           'Content-Type': 'application/json',
           'Content-Length': post_data.length
          }
         };

         var response = "";
         var post_req = https.request(options, function(post_res) {
          post_res.on('data', function (chunk) {
            console.log(chunk);
           response += chunk;
          });
                post_res.on('end', function(){
           console.log('Response: ', response);
                    // res.json({data: JSON.parse(response), orderId: orderId, mid: mid, amount: amount});
                    
                    console.log(JSON.parse(response).body.txnToken);
                    makePayment();
                    setPaymentData({
                        ...paymentData,
                        token: JSON.parse(response).body.txnToken, 
                        order: orderId, 
                        mid: mid,
                        amount: totalAmount
                    })
                    
          });
         });

         post_req.write(post_data);
         post_req.end();
        });
    }

    const makePayment = () => {
        setLoading(true);
        var config = {
            "root":"",
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
              "transactionStatus":function transactionStatus(paymentStatus){
                console.log("paymentStatus => ",paymentStatus);
                setLoading(false);
              },
              "notifyMerchant":function notifyMerchant(eventName,data){
                console.log("Closed",eventName, data);
                setLoading(false);
              }
            }
        };
      
        if (window.Paytm && window.Paytm.CheckoutJS) {
        // initialze configuration using init method
        window.Paytm.CheckoutJS.init(config).then(function onSuccess() {
            console.log('Before JS Checkout invoke');
            // after successfully update configuration invoke checkoutjs
            window.Paytm.CheckoutJS.invoke();
        }).catch(function onError(error) {
            console.log("Error => ", error);
        });
        }
    }




  return (
    <div>
       <div>
            {
                loading ? (
                    <img className="paytm-load" src="https://c.tenor.com/I6kN-6X7nhAAAAAj/loading-buffering.gif" />
                ) : (
                      <MakePayment cartInfo={cartInfo} date={date} setLoading={setLoading}  paymentData={paymentData}/>
                )
            }
        </div>
    </div>
  )
}

export default PaytmButton
