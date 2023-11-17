import Box from '@mui/material/Box';
import * as React from 'react';
import Shopping from '../../images/logo_secure_cart.png';
import { useState } from 'react';
import axios from "axios";
import OtpInput from 'react-otp-input';
import { useNavigate } from 'react-router-dom';
import {
  MDBContainer,
  MDBRow,
  MDBCol,
  MDBCard,
  MDBCardBody,
}
  from 'mdb-react-ui-kit';
import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import Button from '@mui/material/Button';
// import FacebookButton from '../FacebookButton';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContentText from '@mui/material/DialogContentText';
import CircularProgress from '@mui/material/CircularProgress';
import './Login.css';
import TextField from '@mui/material/TextField';
import GoogleLoginButton from '../GoogleLogin/GoogleLoginButton';
import CaptchaTest from '../Captcha/CaptchaTest';
import { FormControl, InputLabel, MenuItem, Select } from '@mui/material';
import { loginUser, twoStepVerify } from '../../ServiceApi/ServiceApi';
import { url } from "../../ServiceApi/ServiceApi";
export default function Login() {
  const navigate = useNavigate();

  const [open, setOpen] = React.useState(false);
  const clientId = "151312583537-a4c84kqo8v2vjmm4gn8b7ine9492v814.apps.googleusercontent.com";
  // const onLoginSuccess = (res)=> {
  //     console.log(res.profileObj);
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
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [role, setRole] = useState('')
  const [isCaptchaValid, setIsCaptchaValid] = useState(false);

  const handleCaptchaValidation = (isValid) => {
    setIsCaptchaValid(isValid);
  };

  const handleRole = (e) => {
    setRole(e.target.value)
  }

  const handleEmail = (e) => {
    setEmail(e.target.value)
  }
  const handlePassword = (e) => {
    setPassword(e.target.value)
  }
  const handlesignup = (e) => {
    navigate('/register');
  }
  const emailPattern = /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/;
  const passwordPattern = /^(?=.*\d)(?=.*[a-z])(?=.*[a-zA-Z]).{8,}$/;
  const [message, setmessage] = useState('');
  const handleApi = () => {
    if(!isCaptchaValid){
      setmessage('Entered captcha is not valid');
      setOpen(true);
      return;
    }
    if (!email) {
      setmessage('Please enter the  email ');
      setOpen(true);
      return;
    }
    if (!emailPattern.test(email)) {
      setmessage('Please enter a valid email address');
      setOpen(true);
      return;
    }
    if (!passwordPattern.test(password)) {
      setmessage('Password must be at least 8 characters long and contain  one lowercase letter, and one number');
      setOpen(true);
      return;
    }

    twoStepVerify(email, password).then(result => {
      setOpen(true);
      setmessage('OTP sent to your email');
      setTwoStep(true);
    })
      .catch(error => {
        console.log(error);
        setOpen(true);
        setmessage('Incorrect Email or Password');
        setEmail('')
        setPassword('')
        console.log(error)
      })


  //  loginUser(email, password).then(result => {
  //     setOpen(true);
  //     setmessage('login successfully');
  //     localStorage.setItem('Token', result.data.accessToken);
  //     localStorage.setItem('username', result.data.firstName+" "+ result.data.lastName);
  //     localStorage.setItem('email', result.data.email);
  //     localStorage.setItem('roles', result.data.roles);
  //     localStorage.setItem('userId', result.data.id);
  //     if (localStorage.getItem('Token') !== "" && result.data.status==="APPROVED") {
  //       navigate('/home');
  //     } else if (localStorage.getItem('Token') !== "" && result.data.status==="PENDING") {
  //       navigate('/noaccess');
  //     }
  //     else {
  //       return navigate('/');
  //     }
  //   })
  //     .catch(error => {
  //       console.log(error);
  //       setOpen(true);
  //       setmessage('Incorrect Email or Password');
  //       setEmail('')
  //       setPassword('')
  //       setmessage('');
  //       setOpen(true);
  //       console.log(error)
  //     })
  }
  const [forgetopen, setforgetOpen] = React.useState(false);
  const [twoStep, setTwoStep] = React.useState(false);
  const handleForgetPassword = () => {
    setforgetOpen(true);
    setEmail('');
    setPassword('');
  }
  const handleForgetPasswordClose = (event, reason) => {
    setforgetOpen(false);
    setforGetemail('');
    setotp('');
    setDisabled(true);
  };
  const responseFacebook = (response) => {
    console.log(response);
  }
  const [loading, setLoading] = useState(false);
  const [disable, setDisabled] = useState(true);
  const [forGetemail, setforGetemail] = useState("");
  const [otp, setotp] = useState("");
  const [hiddenBtnSend, setHiddenBtnSend] = useState(false);
  const [hiddenBtnOk, setHiddenBtnOk] = useState(true);
  
  
  const handleApiforgetpassword = () => {
    if (!forGetemail) {
      setOpen(true);
      setmessage('Please enter the email Id');
      return;
    }
    setLoading(true);
    axios.post(url+'forget-password?email=' + forGetemail, {
    }).then(result => {
      setLoading(false);
      console.log(result.data);
      setOpen(true);
      setmessage('send otp to your Given Email Id');
      setDisabled(false);
      setHiddenBtnSend(true);
      setHiddenBtnOk(false);
    }).catch(error => {
      setLoading(false);
      setOpen(true);
      setmessage('Incorrect Email Id');
      console.log(error);
    })
  }

  const handleTwoStepVerificationClose = (event, reason) => {
    setTwoStep(false);
    navigate("/");
  };

  const handleTwoStepVerification = () => {
    
    setLoading(true);
    axios.post(url+'user/verifytwostepotp?email=' + email+"&otp="+otp, {
    }).then(result => {
      setLoading(false);
      console.log(result.data);
          setOpen(true);
      setmessage('login successfully');
      localStorage.setItem('Token', result.data.accessToken);
      localStorage.setItem('username', result.data.firstName+" "+ result.data.lastName);
      localStorage.setItem('email', result.data.email);
      localStorage.setItem('roles', result.data.roles);
      localStorage.setItem('userId', result.data.id);
      localStorage.setItem('profilePicture', result.data.profilePic);
      if (localStorage.getItem('Token') !== "" && result.data.status==="APPROVED") {
        navigate('/home');
      } else if (localStorage.getItem('Token') !== "" && result.data.status==="PENDING") {
        navigate('/noaccess');
      }
      else {
        return navigate('/');
      }
    }).catch(error => {
      setLoading(false);
      setOpen(true);
      console.log(error);
      setmessage(error.response.data.message);
      console.log(error);
    })
  }

  const handleApiOtp = () => {
    if (!otp) {
      setOpen(true);
      setmessage('Please Enter the one time password check your email');
      return;
    }
    setLoading(true);
    axios.post(url+'forget-password/otp?otp=' + otp + '&email=' + forGetemail, {
    }).then(result => {
      setLoading(false);
      console.log(result.data);
      setOpen(true);
      setmessage('Reset your password');
      setforgetOpen(false);
      setResetOpen(true);
      setotp('');
    }).catch(error => {
      setLoading(false);
      setmessage('Something went wrong..!');
      console.log(error);
    })
  }
  const [resetOpen, setResetOpen] = useState(false);
  const handleResetPasswordClose = () => {
    setResetOpen(false);
  }
  const [newPassword, setNewPassword] = useState();
  const [confirmPassword, setConFirmPassword] = useState();
  const handleResetPassword = () => {
    if (!newPassword) {
      setOpen(true);
      setmessage('Please Enter New password');
      return;
    }
    if (!passwordPattern.test(newPassword)) {
      setOpen(true);
      setmessage('Password must be at least 8 char long and one lowercase letter, and one number');
      return;
    }
    if (!confirmPassword) {
      setOpen(true);
      setmessage('confirm password is must');
      return;
    }
    if (newPassword != confirmPassword) {
      setOpen(true);
      setmessage('Password mismatch');
      return;
    }
    setLoading(true);
    axios.post(url+'forget-password/reset-password?email=' + forGetemail + '&password=' + confirmPassword, {
    }).then(result => {
      setLoading(false);
      console.log(result.data);
      setOpen(true);
      setmessage('New password changed You can login with new password');
      setResetOpen(false);
      setotp('');
    }).catch(error => {
      setLoading(false);
      console.log(error);
    })
  }
  return (
    <div className="App">
      <MDBContainer fluid style={{backgroundColor:'aliceblue'}}>
        <MDBRow className='d-flex justify-content-center align-items-center h-100'>
          <MDBCol col='12'>
            <MDBCard className='my-2 mx-auto' style={{ borderRadius: '1rem', maxWidth: '500px' }}>
              <MDBCardBody className='w-100 d-flex flex-column' >
                <div style={{ textAlign: 'center', padding:'0px 30px 2px 30px' }}>
                  <img style={{ height: 130, width: 130, cursor: 'pointer' }} className='text-center' src={Shopping} />
                  <h5 className="fw-bold text-center">Sign in</h5>
                  {/* <label class="form-label" for="typeEmailX-2">Email</label>
        <input value={email} onChange={handleEmail} type="email" id="typeEmailX-2" class="form-control form-control-lg" /> */}
                  <TextField
                    style={{ marginTop: 20 }}
                    fullWidth
                    label="Email"
                    size='small'
                    value={email}
                    onChange={handleEmail}
                  />
                 
                  <TextField fullWidth style={{ marginTop: 25, marginBottom: 20 }} size='small' type={'password'} label={'Password'} id="margin-dense" margin="dense" value={password} onChange={handlePassword} />
                  
                  <div>
                  <CaptchaTest onCaptchaValidation={handleCaptchaValidation} />
                </div>
                  
                  <Button variant="contained" size='small' style={{width:'60%'}} onClick={handleApi} >Login</Button>
                  <div className="d-flex justify-content-between mt-2">
                  <a style={{ textDecoration: 'none', cursor: 'pointer', color: 'dodgerblue', alignItems: 'left' }} onClick={handleForgetPassword} >Forgot password?</a>
                </div>
                
                </div>
                
                <Box textAlign='center'>
                  <hr className="my-4" />

<div style={{display:'flex'}}>
                  <FormControl style={{width:'50%'}} size='small'>
  <InputLabel id="demo-simple-select-label">Are you a </InputLabel>
  <Select
    labelId="demo-simple-select-label"
    id="demo-simple-select"
    value={role}
    label="Are you a"
    onChange={handleRole}
    style={{height:'44px'}}
  >
    <MenuItem value={"seller"}>SELLER</MenuItem>
    <MenuItem value={"buyer"}>BUYER</MenuItem>
  </Select>
</FormControl>&nbsp;&nbsp;&nbsp;
                  <GoogleLoginButton
                    clientId={clientId}
                    autoLoad={false}
                    roles={role}
                  // callback={onLoginSuccess}
                  // onSuccess={onLoginSuccess}
                  // onFailure={onLoginFailure}
                  // cookiePolicy={'single_host_origin'}
                  // style={{border:"none",borderRadius:5,fontSize:16, fontWeight: "bold", backgroundColor:'#4285f4',color:"#fff"}}
                  />

</div>
                  <p sx={{ marginTop: 2 }}> Don't have a account? <a style={{ color: 'blue', cursor: 'pointer', textDecoration: 'none' }} onClick={handlesignup}>Register</a>
                  </p>
                </Box>
              </MDBCardBody>
            </MDBCard>
          </MDBCol>
        </MDBRow>
      </MDBContainer>
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
      <Dialog
        open={forgetopen}
        aria-labelledby="responsive-dialog-title"
      >
        <DialogTitle id="responsive-dialog-title">
          Reset Your Password
        </DialogTitle>
        <DialogContent>
          <DialogContentText>
            <small>The Verification email will be sent to the mailbox Please check it</small>
          </DialogContentText>
          {/* <label class="form-label" for="typeEmailX-2">Email</label> */}
          <TextField fullWidth value={forGetemail} onChange={(e) => setforGetemail(e.target.value)} label={'Enter your emails'} id="margin-dense" margin="dense" />
          {/* <input value={forGetemail} onChange={(e) => setforGetemail(e.target.value)} type="email"
          placeholder='Enter your email'
          id="typeEmailX-2" class="form-control form-control-lg" /> */}
          {/* <label style={{marginTop:5}} class="form-label" for="typeEmailX-2">Verification Code</label>
          <input value={otp} onChange={(e) => setotp(e.target.value)} type="number" id="typeEmailX-2" class="form-control form-control-lg" disabled={disable} /> */}
          <div className='otp-input'>
            <OtpInput
              value={otp}
              onChange={setotp}
              numInputs={6}
              renderSeparator={<span>-</span>}
              renderInput={(props) => <input {...props} />}
              hidden={disable}
            />
          </div>
          <div>
            <small>The otp will expired in 10 minutes</small>
          </div>
          <Button
            style={{ padding: 2, borderRadius: 20, backgroundColor: '#33539E', marginTop: 10, color: 'white', fontSize: 14, float: 'right' }}
            onClick={handleApiforgetpassword}
            hidden={hiddenBtnSend}
          >Send</Button>
          <Button
            style={{ padding: 2, borderRadius: 20, backgroundColor: '#C70039', marginTop: 10, color: 'white', fontSize: 14, float: 'right' }}
            onClick={handleForgetPasswordClose}
          >Cancel</Button>
          <Button
            style={{ padding: 2, borderRadius: 20, backgroundColor: '#33539E', marginTop: 10, color: 'white', fontSize: 14, float: 'right' }}
            onClick={handleApiOtp}
            hidden={hiddenBtnOk}
          >Ok</Button>
          {loading && <Box sx={{ display: 'flex' }}>
            <CircularProgress />
          </Box>}
        </DialogContent>
      </Dialog>
      <Dialog
        fullWidth={'md'}
        open={resetOpen}
        aria-labelledby="responsive-dialog-title"
      >
        <DialogTitle id="responsive-dialog-title">
          Reset Your Password
        </DialogTitle>
        <DialogContent>
          <DialogContentText>
          </DialogContentText>
          <label class="form-label" for="typeEmailX-2">New Password</label>
          <input value={newPassword} onChange={(e) => setNewPassword(e.target.value)} placeholder='Enter your Password' type="password" id="typeEmailX-2" class="form-control form-control-lg" />
          <label style={{ marginTop: 5 }} class="form-label" for="typeEmailX-2">Confirm Password</label>
          <input value={confirmPassword} onChange={(e) => setConFirmPassword(e.target.value)} type="password" id="typeEmailX-2" class="form-control form-control-lg" disabled={disable} />
          <Button
            style={{ padding: 2, borderRadius: 20, backgroundColor: '#C70039', marginTop: 10, color: 'white', fontSize: 14, float: 'right' }}
            onClick={handleResetPasswordClose}
          >Cancel</Button>
          <Button
            style={{ padding: 2, backgroundColor: '#33539E', marginTop: 10, color: 'white', fontSize: 14, float: 'right' }}
            onClick={handleResetPassword}
          >Reset your password</Button>
          {loading && <Box sx={{ display: 'flex' }}>
            <CircularProgress />
          </Box>}
        </DialogContent>
      </Dialog>



      <Dialog
        open={twoStep}
        aria-labelledby="responsive-dialog-title"
      >
        <DialogTitle id="responsive-dialog-title">
          TWO STEP VERIFICATION
        </DialogTitle>
        <DialogContent>
          <DialogContentText>
            <small>The Verification OTP will be sent to the your email Please check it</small>
          </DialogContentText>
          {/* <label class="form-label" for="typeEmailX-2">Email</label> */}
          {/* <input value={forGetemail} onChange={(e) => setforGetemail(e.target.value)} type="email"
          placeholder='Enter your email'
          id="typeEmailX-2" class="form-control form-control-lg" /> */}
          {/* <label style={{marginTop:5}} class="form-label" for="typeEmailX-2">Verification Code</label>
          <input value={otp} onChange={(e) => setotp(e.target.value)} type="number" id="typeEmailX-2" class="form-control form-control-lg" disabled={disable} /> */}
          <div className='otp-input'>
            <OtpInput
              value={otp}
              onChange={setotp}
              numInputs={6}
              renderSeparator={<span>-</span>}
              renderInput={(props) => <input {...props} />}
              hidden={disable}
            />
          </div>
          <div>
            <small>The otp will expired in 10 minutes</small>
          </div>
          <Button
            style={{ padding: 2, borderRadius: 20, backgroundColor: '#33539E', marginTop: 10, color: 'white', fontSize: 14, float: 'right' }}
            onClick={handleTwoStepVerification}
           
          >SUBMIT</Button>
          <Button
            style={{ padding: 2, borderRadius: 20, backgroundColor: '#C70039', marginTop: 10, color: 'white', fontSize: 14, float: 'right' }}
            onClick={handleTwoStepVerificationClose}
          >CANCEL</Button>
          {loading && <Box sx={{ display: 'flex' }}>
            <CircularProgress />
          </Box>}
        </DialogContent>
      </Dialog>
    </div>
  );
}