import Box from '@mui/material/Box';
import * as React from 'react';

import { useState } from "react";
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import InputLabel from '@mui/material/InputLabel';
import Select from '@mui/material/Select';
import { useNavigate } from 'react-router-dom';
import TextField from '@mui/material/TextField';

import axios from "axios";
import {
  MDBBtn,
  MDBContainer,
  MDBCard,
  MDBCardBody,
  MDBCol,
  MDBRow,
  MDBInput,

}
  from 'mdb-react-ui-kit';

import Button from '@mui/material/Button';
import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';


function RegistrationForm() {

  const navigate = useNavigate();

  const [open, setOpen] = React.useState(false);



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
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [role, setRole] = useState('')
  const [username, setusername] = useState('')

 function clearData(){
  setfName('');
  setlName('');
  setEmail('');
  setPassword('');
  setRole('');
  setusername('');
 }



  const handleFname = (e) => {
    setfName(e.target.value)
  }

  const handlelName = (e) => {
    setlName(e.target.value)
  }

  const handleEmail = (e) => {
    setEmail(e.target.value)
  }

  const handlePassword = (e) => {
    setPassword(e.target.value)
  }

  const handleRole = (e) => {
    setRole(e.target.value)
  }
  const handleusername = (e) => {
    setusername(e.target.value)
  }


  const emailPattern = /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/;
  const passwordPattern = /^(?=.*\d)(?=.*[a-z])(?=.*[a-zA-Z]).{8,}$/;



  const [message, setmessage] = useState('');

  const handleApi = () => {
    setOpen(true);
    if (!fname) {

      setmessage('Please enter your first name.')
      clearData();
      return;
    }
 
    if (!lName) {
      setmessage('Please enter your last name.')
      clearData();
      return;
    }
 
    if (!username || username.includes(' ')) {
      setmessage('Please enter a valid username without spaces.')
      clearData();
      return;
    }

   

    if (!emailPattern.test(email)) {
      setmessage('Please enter a valid email address');
      clearData();
      return;
    }

    if (!passwordPattern.test(password)) {
      setmessage('Password must be at least 8 characters long and contain  one lowercase letter, and one number');
      clearData();
      return;
    }

    if (!role) {
      setmessage('Please select your role')
      clearData();
      return;
    }
 
    axios.post('http://localhost:8080/user/signup', {
      firstName: fname,
      lastName: lName,
      emailId: email,
      password: password,
      userRole: role,
      userName: username
    }).then(result => {
        
      setmessage('Registered successfully');
      console.log(result.data)
      setTimeout(function() {
            navigate('/');
        }, 2000);

    })
      .catch(error => {
        console.log(error)
        setmessage(error.response.data.message);
        clearData();
        
      })
  }

  const handlesignin = () => {

    navigate('/')

  }


  return (
    <div>
      <MDBContainer fluid >

        <MDBRow>


          <MDBCol md='4'></MDBCol>
          <MDBCol md='4'>

            <MDBCard className='my-5'>
              <MDBCardBody className='p-5'>
                <h2 className="fw-bold mb-4 text-center">Create your Account</h2>
                <MDBRow>
                  <MDBCol col='12'>
                  <TextField   value={fname} onChange={handleFname} label={'First Name'} id="margin-dense" margin="dense" required/>
                  
                    {/* <MDBInput value={fname} onChange={handleFname} wrapperClass='mb-4'  id='form1' type='text' required >First Name <span style={{color:'red'}}>*</span></MDBInput> */}
                  </MDBCol>

                  <MDBCol col='12' className='mb-3'>
                  <TextField   value={lName} onChange={handlelName} label={'Last Name'} id="margin-dense" margin="dense" required/>

                    {/* <MDBInput value={lName} onChange={handlelName} wrapperClass='mb-4'   id='form1' type='text' required >Last Name<span style={{color:'red'}}>*</span></MDBInput> */}
                  </MDBCol>
                </MDBRow>

                <TextField fullWidth  value={username} onChange={handleusername} label={'User Name'} id="margin-dense" margin="dense" required/>

                {/* <MDBInput value={username} onChange={handleusername} wrapperClass='mb-4' id='form1' type='text' required>User Name<span style={{color:'red'}}>*</span></MDBInput> */}

                <TextField fullWidth   value={email} onChange={handleEmail} label={'Email'} id="margin-dense" margin="dense" required/>
                
                {/* <MDBInput value={email} onChange={handleEmail} wrapperClass='mb-4' id='form1'  required>Email<span style={{color:'red'}}>*</span></MDBInput> */}
                
                <TextField fullWidth  value={password} onChange={handlePassword} label={'Password'} id="margin-dense" type={'password'} margin="dense" required/>


                {/* <MDBInput value={password} onChange={handlePassword} wrapperClass='mb-4'   id='form1' type='password' required >Password<span style={{color:'red'}}>*</span></MDBInput> */}


                <FormControl required fullWidth  sx={{ m: 1, minWidth: 150 }} size="medium">
                  <InputLabel id="demo-select-small">User Role </InputLabel>

                  <Select
                    labelId="demo-select-small"
                    id="demo-select-small"
                    value={role}
                    label="userRole"
                    onChange={handleRole}
                  >

                    <MenuItem value='Customer'>Customer</MenuItem>
                    <MenuItem value='Admin'>Admin</MenuItem>

                  </Select>

                </FormControl>

                <Box sx={{ marginTop: 2 }}>
          <div className='mb-3' style={{display: 'flex', justifyContent: 'center'}}>
           <Button  variant="contained" size='large' xs={{ padding: 180}} onClick={handleApi} >Sign Up</Button>
           </div>
                  <p>Already have an account?  <a style={{color:'blue', cursor:'pointer', textDecoration:'none'}} onClick={handlesignin}>Login</a>
                  </p>
                </Box>




              </MDBCardBody>
            </MDBCard>

          </MDBCol> <MDBCol md='4'></MDBCol>

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


