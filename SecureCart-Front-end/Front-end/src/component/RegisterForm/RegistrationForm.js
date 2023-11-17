import Box from '@mui/material/Box';
import * as React from 'react';
import Shopping from './logo_secure_cart.png';
import { useState } from "react";
import FormControl from '@mui/material/FormControl';
import { useNavigate } from 'react-router-dom';
import TextField from '@mui/material/TextField';
import axios from "axios";
import { url } from "../../ServiceApi/ServiceApi";
import {
  MDBBtn,
  MDBContainer,
  MDBCard,
  MDBCardBody,
  MDBCol,
  MDBRow,
}
  from 'mdb-react-ui-kit';
import Button from '@mui/material/Button';
import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import CaptchaTest from '../Captcha/CaptchaTest';
import { InputLabel, MenuItem, Select } from '@mui/material';
function RegistrationForm() {
  const navigate = useNavigate();
  const [open, setOpen] = React.useState(false);
  const [isCaptchaValid, setIsCaptchaValid] = useState(false);
  const handleCaptchaValidation = (isValid) => {
    setIsCaptchaValid(isValid);
  };
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
  const [fname, setfName] = useState('')
  const [lName, setlName] = useState('')
  const [role, setRole] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [street, setStreet] = useState('')
  const [city, setCity] = useState('')
  const [state, setState] = useState('')
  const [country, setCountry] = useState('')
  const [bankName, setBankName] = useState('')
  const [bankAccountNumber, setBankAccountNumber] = useState('')
  const [reload, setReload] = useState(false)
  const [file, setFile] = useState(null);
  function clearData() {
    setfName('');
    setlName('');
    setEmail('');
    setPassword('');
    setStreet('');
    setCity('');
    setState('');
    setCountry('');
  }
  const handleFname = (e) => {
    setfName(e.target.value)
  }
  const handleBankname = (e) => {
    setBankName(e.target.value)
  }
  const handleBankAccountNumber = (e) => {
    setBankAccountNumber(e.target.value)
  }
  const handlelName = (e) => {
    setlName(e.target.value)
  }
  const handleRole = (e) => {
    setRole(e.target.value)
  }
  const handleEmail = (e) => {
    setEmail(e.target.value)
  }
  const handleStreet = (e) => {
    setStreet(e.target.value)
  }
  const handleCity = (e) => {
    setCity(e.target.value)
  }
  const handleState = (e) => {
    setState(e.target.value)
  }
  const handleCountry = (e) => {
    setCountry(e.target.value)
  }
  const handlePassword = (e) => {
    setPassword(e.target.value)
  }
  const emailPattern = /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/;
  const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%?=*&]).{8,}$/;
  const [message, setmessage] = useState('');
  const handleApi = () => {
    setOpen(true);

    if(role==="seller" && !bankName){
      setmessage('Please enter bank name');
      return;
    }

    if(role==="seller" && !bankAccountNumber){
      setmessage('Please enter bank account number');
      return;
    }

    if (!fname) {
      setmessage('Please enter your first name.')
      //clearData();
      return;
    }
    if (!role) {
      setmessage('Role cannot be blank')
      //clearData();
      return;
    }
    if (!lName) {
      setmessage('Please enter your last name.')
      //clearData();
      return;
    }
    if (!isCaptchaValid) {
      setmessage('Please enter Captcha.');
      //clearData();
      return;
    }
    if (!city) {
      setmessage('Please enter City');
      //clearData();
      return;
    }
    if (!country) {
      setmessage('Please enter Country');
      //clearData();
      return;
    }

    if (!street) {
      setmessage('Please enter Street');
      //clearData();
      return;
    }

    if (!state) {
      setmessage('Please enter State');
      //clearData();
      return;
    }
    if (!file) {
      setmessage('Please choose profile picture');
      //clearData();
      return;
    }
    if (!emailPattern.test(email)) {
      setmessage('Please enter a valid email address');
      //clearData();
      return;
    }
    if (!passwordPattern.test(password)) {
      setmessage('Password must be at least 8 characters, contain atleast one Uppercase letter and special character');
      //clearData();
      return;
    }

    
    const formData = new FormData();
    formData.append('profile', file);
    formData.append('userData', JSON.stringify({
      firstName: fname,
      lastName: lName,
      emailId: email,
      password: password,
      street: street,
      city: city,
      role: role,
      state: state,
      country: country,
      bankName: bankName,
      bankAccountNumber: bankAccountNumber
    }))


    axios.post(url+'user/signup', formData,  {
      
          }).then(result => {
      setmessage('Registered successfully');
      console.log(result.data)
      setTimeout(function () {
        navigate('/');
      }, 2000);
    })
      .catch(error => {
        console.log(error)
        setmessage(error.response.data.message);
        setReload(true);
        clearData();
      })
  }
  const handlesignin = () => {
    navigate('/')
  }
  return (
    <div>
      <MDBContainer fluid style={{backgroundColor:'aliceblue'}}>
        <MDBRow>
          <MDBCol md='3'></MDBCol>
          <MDBCol md='6'>
            <MDBCard className='my-2'>
              <MDBCardBody style={{ textAlign: 'center' }}>
                <img style={{ height: 130, width: 130, cursor: 'pointer' }} src={Shopping} />
                <h5 className="fw-bold mb-2 text-center">Create your Account</h5>
                <MDBRow>
                  <MDBCol col='12'>
                    <TextField fullWidth size='small' value={fname} onChange={handleFname} label={'First Name'} id="margin-dense" margin="dense" required />
                    <TextField fullWidth size='small' value={lName} onChange={handlelName} label={'Last Name'} id="margin-dense" margin="dense" required />
                    <TextField fullWidth size='small' value={email} onChange={handleEmail} label={'Email'} id="margin-dense" margin="dense" required />
                    <TextField fullWidth size='small' value={password} onChange={handlePassword} label={'Password'} id="margin-dense" type={'password'} margin="dense" required />
                    <FormControl fullWidth size='small'  margin="dense">
                    <InputLabel id="demo-simple-select-label">Are you a </InputLabel>
                    <Select
                      labelId="demo-simple-select-label"
                      id="demo-simple-select"
                      value={role}
                      size='small'
                      label="Are you a"
                      onChange={handleRole}
                    >
                      <MenuItem value={"seller"}>SELLER</MenuItem>
                      <MenuItem value={"buyer"}>BUYER</MenuItem>
                    </Select>
                  </FormControl>
                  {
                    role==="seller" ? 
                    ( <TextField fullWidth size='small' value={bankName} onChange={handleBankname} label={'Bank name'} id="margin-dense" type={'text'} margin="dense" required />) : ""
                  }

                  </MDBCol>
                  <MDBCol col='12' className='mb-3'>
                    <TextField fullWidth size='small' value={street} onChange={handleStreet} label={'Street'} id="margin-dense" margin="dense" required />
                    <TextField fullWidth size='small' value={city} onChange={handleCity} label={'City'} id="margin-dense" margin="dense" required />
                    <TextField fullWidth size='small' value={state} onChange={handleState} label={'State'} id="margin-dense" margin="dense" required />
                    <TextField fullWidth size='small' value={country} onChange={handleCountry} label={'Country'} id="margin-dense" margin="dense" required />
                    <TextField fullWidth size='small' type="file" onChange={(e) => setFile(e.target.files[0])} id="margin-dense" margin="dense" required />
                    {
                    role==="seller" ? 
                    ( <TextField fullWidth size='small' value={bankAccountNumber} onChange={handleBankAccountNumber} label={'Bank account number'} id="margin-dense" type={'text'} margin="dense" required />) : ""
                  }
                  </MDBCol>
                  
                </MDBRow>
                
                <div>
                  <CaptchaTest onCaptchaValidation={handleCaptchaValidation} />
                </div>
                
                <FormControl required fullWidth sx={{ m: 1, minWidth: 150 }} size="medium">
                </FormControl>
                <Box >
                  <div style={{ display: 'flex', justifyContent: 'center' }}>
                    <Button variant="contained" size='small' style={{width:'70%'}} fullWidth onClick={handleApi} >Sign Up</Button>
                  </div>
                  <p>Already have an account?  <a style={{ color: 'blue', cursor: 'pointer', textDecoration: 'none' }} onClick={handlesignin}>Login</a>
                  </p>
                </Box>
              </MDBCardBody>
            </MDBCard>
          </MDBCol> <MDBCol md='3'></MDBCol>
        </MDBRow>
      </MDBContainer>
      <Snackbar
        open={open}
        autoHideDuration={6000}
        anchorOrigin={{
          vertical: "top",
          horizontal: "center"
        }}
        onClose={handleClose}
        message={message}
        action={action}
      />
    </div>
  );
}
export default RegistrationForm;
