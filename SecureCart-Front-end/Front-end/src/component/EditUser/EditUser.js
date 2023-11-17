import React from 'react'
import Button from '@mui/material/Button';
import { styled } from '@mui/material/styles';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import Stack from '@mui/material/Stack';
import DialogActions from '@mui/material/DialogActions';
 
import axios from 'axios';
import { url } from '../../ServiceApi/ServiceApi';
import TextField from '@mui/material/TextField';
import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';


import { useState } from 'react';
import ProductAddButton from '../Product/ProductAddButton';
import { useEffect } from 'react';
 
  
  const EditUser = ({handleUserClose}) => {

    const [snackopen, setsnackOpen] = React.useState(false);
    const [message, setmessage] = useState('');
    const handleClose1 = (event, reason) => {
        if (reason === 'clickaway') {
          return;
        }
        setsnackOpen(false);
      };
      const action = (
        <React.Fragment>
          <IconButton
            size="small"
            aria-label="close"
            color="inherit"
            onClick={handleClose1}
          >
            <CloseIcon fontSize="small" />
          </IconButton>
        </React.Fragment>
      );



    const[userId, setuserId] = useState();
    const[firstName, setFirstName] = useState("");
    const[lastName, setLastName] = useState('');
    const[emailId, setEmailId] = useState('');
    const[street, setStreet] = useState('');
    const[city, setCity] = useState('');
    const[state, setState] = useState('');
   const[country, setCountry] = useState('');
    const[profilePicture, setProfile] = useState(null);
    const[bankName, setBankName] = useState('');
    const[bankAccountNumber, setBankAccountNumber] = useState('');
    const[role, setRole] = useState('');
    useEffect(() => {
    
    axios.get(url+'user/' + localStorage.getItem('userId'), {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('Token')}`
        }
      })
        .then((res) => {
          setuserId(res.data.userId);
          setFirstName(res.data.firstName);
          setLastName(res.data.lastName);
          setEmailId(res.data.emailId);
          setStreet(res.data.street);
          setCity(res.data.city);
          setRole(res.data.role);
          setState(res.data.state)
          setCountry(res.data.country);
          setProfile(res.data.profilePicture);
          setBankName(res.data.bankName)
          setBankAccountNumber(res.data.bankAccount);
        })
        .catch((error) => {
          setmessage('Cant empty field')
          setsnackOpen(true);
          console.log(error);
        });
        
    }, []);

    // const toBase64 = file => new Promise((resolve, reject) => {
    //     const reader = new FileReader();
    //     reader.readAsDataURL(file);
    //     reader.onload = () => resolve(reader.result);
    //     reader.onerror = reject;
    // });
    
    // async function main(file) {
    //    let img = toBase64(file);
    //    console.log(await toBase64(file));
    //    setProfile(img);
    // }

    // const handleFilechange = (e) => {
    //     console.log(e.target.files[0]);
    //         main(e.target.files[0])
    //    } 

       const handleFilechange = (e) => {
        console.log(e.target.files[0]);
        setProfile(e.target.files[0])
       } 
   
      function updateUser(){
        if (firstName == '') {
            setmessage('Please Give your first name')
            return;
          }
          if (lastName == '') {
            setmessage('Please Give your Last name')
            return;
          }
          if (emailId == '') {
            setmessage('Please Give tour Email Id')
            return;
          }
          if (street == '') {
            setmessage('Please update your street')
            return;
          }
          if (city == '') {
            setmessage('Please upload your city')
            return;
          }
          if (state == '') {
              setmessage('Please upload your state ')
              return;
            }
  
            if (country == '') {
              setmessage('Please upload country name')
              return;
            }

            if(role==="seller" && !bankName){
              setmessage('Please enter bank name');
              return;
            }
        
            if(role==="seller" && !bankAccountNumber){
              setmessage('Please enter bank account number');
              return;
            }
          
          const formData = new FormData();
          formData.append('updateprofile', profilePicture);
          formData.append('updateUserdata', JSON.stringify({
            firstName: firstName,
            lastName: lastName,
            emailId: emailId,
            street: street,
            city: city,
            state: state,
            country: country,
            bankName: bankName,
            bankAccountNumber: bankAccountNumber
          }))
          axios.put(url+'user/' + userId, formData, {
            headers: {
              Authorization: `Bearer ${localStorage.getItem('Token')}`,
              'Content-Type': 'multipart/form-data'
            }
          })
            .then((response) => {

                localStorage.setItem('username', response.data.firstName+" "+ response.data.lastName);
                localStorage.setItem('email', response.data.emailId);
                localStorage.setItem('profilePicture', response.data.profilePicture);
              setmessage('User updated')
              console.log(response)
              handleUserClose();
              
            })
            .catch((error) => {
              console.log(error);
              
            });
      }
       
  return (
    <div>
       <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="First Name"
            type="text"
            fullWidth
            variant="standard"
            value={firstName}
            onChange={(e) => setFirstName(e.target.value)}
          />
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Last name "
            type="text"
            fullWidth
            variant="standard"
            value={lastName}
            onChange={(e) => setLastName(e.target.value)}
          />
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Email"
            type="text"
            fullWidth
            variant="standard"
            value={emailId}
            onChange={(e) => setEmailId(e.target.value)}
          />
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Street"
            type="text"
            fullWidth
            variant="standard"
            value={street}
            onChange={(e) => setStreet(e.target.value)}
          />
 <TextField
            autoFocus
            margin="dense"
            id="name"
            label="City" 
            type="text"
            fullWidth
            variant="standard"
            value={city}
            onChange={(e) => setCity(e.target.value)}
          />
           <TextField
            autoFocus
            margin="dense"
            id="name"
            label="State"
            type="text"
            fullWidth
            variant="standard"
            value={state}
            onChange={(e) => setState(e.target.value)}
          />
           <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Country"
            type="text"
            fullWidth 
            variant="standard"
            value={country}
            onChange={(e) => setCountry(e.target.value)}
          />
  { role ==="seller" ? 
  ( <>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Bank Name"
            type="text"
            fullWidth
            variant="standard"
            value={bankName}
            onChange={(e) => setBankName(e.target.value)}
          />
           <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Bank account number"
            type="text"
            fullWidth 
            variant="standard"
            value={bankAccountNumber}
            onChange={(e) => setBankAccountNumber(e.target.value)}
          />
          </>):("")
  }

             {/* <img src={profilePicture} height={150} width={150} /> */}

         
          <Stack sx={{ marginTop: 2 }} direction="row" alignItems="center" spacing={2}>
            <Button variant="contained" component="label">
              Uploading user profile
              <input hidden value={''}
                 onChange={handleFilechange}
                type="file" />
            </Button>
          </Stack>
        </DialogContent>
        <DialogActions>
          
          <ProductAddButton label="Update" onClick={() => updateUser()} />
          <ProductAddButton label="Cancel" onClick={handleUserClose} />
        </DialogActions>

        <Snackbar
        open={snackopen}
        autoHideDuration={6000}
        onClose={handleClose1}
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

export default EditUser
