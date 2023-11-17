import React from 'react'
import { useState } from 'react';
import { GoogleLogin } from 'react-google-login';
import { gapi } from 'gapi-script';
import { url } from "../../ServiceApi/ServiceApi";
import axios from 'axios';
import { useNavigate } from 'react-router-dom'
import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import Button from '@mui/material/Button';

function GoogleLoginButton({roles}) {

 console.log(roles);
  const[firstName, setFirstName] = useState();
  const[lastName, setLastName] = useState();
  const[emailGoogle, setEmailGoogle] = useState();
  const[googlePassword, setgooglePassword] = useState();
  var SCOPES = 'https://www.googleapis.com/auth/drive.metadata.readonly';

  const[open, setOpen]= useState(false);
  const [message, setMessage] = useState();
  const navigate = useNavigate();
  // if(!role){
  //   setMessage("Please select your role");
  //   setOpen(true);
  //   return false;
  // }
   
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



 

  const onLoginSuccess = (res)=> {

    gapi.load('client:auth2', () => {
      gapi.auth2.init({
          clientId: clientId,
          scope: SCOPES
      })
  })



    console.log(res.profileObj);
    console.log(res.profileObj.givenName);
  setFirstName(res.profileObj.givenName);
  setLastName(res.profileObj.familyName);
  setEmailGoogle(res.profileObj.email);
  setgooglePassword(res.profileObj.googleId);
  
  localStorage.setItem('profile', res.profileObj.imageUrl);
  //localStorage.setItem('lastname', res.profileObj.familyName);

  axios.post(url+'user/socialLogin', {
    "firstName": res.profileObj.givenName,
    "lastName": res.profileObj.familyName,
    "email": res.profileObj.email,
    "password": res.profileObj.googleId,
    "role": roles
  }).then(result => {
      
    console.log(result.data)

  localStorage.setItem('Token', result.data.accessToken);
  localStorage.setItem('username', result.data.firstName+" "+ result.data.firstName);
  localStorage.setItem('email', result.data.email);
  localStorage.setItem('roles',result.data.roles);
  localStorage.setItem('userId', result.data.id)

  if (localStorage.getItem('Token') !== "" && result.data.status==="APPROVED") {
    navigate('/home');
  } else if (localStorage.getItem('Token') !== "" && result.data.status==="PENDING") {
    navigate('/noaccess');
  } else {
    navigate('/');
  }
  
       

  })
    .catch(error => {
     
      
    console.log(error)
  
})
  
  if(!firstName || !lastName || !emailGoogle || !googlePassword || !firstName){
    return false;
  }
}

const onLoginFailure = (res)=> {

  gapi.load('client:auth2', () => {
    gapi.auth2.init({
        clientId: clientId
    })
})

setOpen(true);
setMessage("Error occured in google login, please refresh the page and try again");
}


const clientId = "181120529079-9bsdofcf2j11bnv2scbi0enq29jvdgo8.apps.googleusercontent.com";








           
 
 
    
  return (
    <div>
     
         
      <GoogleLogin
                    clientId={clientId}
                    buttonText="LOGIN WITH GOOGLE"
                    onSuccess={onLoginSuccess}
                    onFailure={onLoginFailure}
                    cookiePolicy={'single_host_origin'}
                    style={{border:"none",borderRadius:5,fontSize:16, fontWeight: "bold", backgroundColor:'blue',color:"#fff"}}
                     disabled={(roles==="" || roles===undefined || roles===null)?true:false}
                     autoLoad={false}
                     theme='dark'
                />

                <br/>
                <br/>

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

export default GoogleLoginButton
