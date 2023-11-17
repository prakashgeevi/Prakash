import React from 'react'

import FacebookLogin from 'react-facebook-login';

import { useNavigate } from 'react-router-dom';

import axios from 'axios';
import { useState } from 'react';
import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import Button from '@mui/material/Button';

const FacebookButton = () => {
   
    const navigate = useNavigate();

    const[open, setOpen]= useState(false);
    const[message, setmessage]= useState();
     
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
  
  
  
   
  
   
  






    

    const responseFacebook = (response) => {
        
        console.log(response);
        console.log(response.email);

        localStorage.setItem('username', response.name);

        localStorage.setItem('profile', response.picture.data.url);

       
        axios.post('http://localhost:8080/user/socialLogin', {
          firstName: response.name,
          lastName: response.name,
          email: response.email,
          password: response.id,
          userRole: "Customer",
          userName: response.name
        }).then(result => {
            
          console.log(result.data)

        localStorage.setItem('Token', result.data.accessToken);
       
        localStorage.setItem('email', result.data.email);
        localStorage.setItem('roles',result.data.roles);
        localStorage.setItem('userId', result.data.id)

        
        // setmessage('login successfully');
          
                navigate('/home');
            
    
        })
          .catch(error => {
            setOpen(true);
            setmessage('Server not found');
            console.log(error.message)
          })
       


        
    }


  return (
    <div>

    <FacebookLogin
      appId="3109137125899182"
      autoLoad={false}
      fields="name,email,picture"
      callback={responseFacebook} 
      
    />
      
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

export default FacebookButton
