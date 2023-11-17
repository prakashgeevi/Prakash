
import React, { useState, useEffect } from "react";
import axios from "axios";

import Avatar from '@mui/material/Avatar';

import Tooltip from '@mui/material/Tooltip';
import Typography from '@mui/material/Typography';

import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
 
 
import { styled } from '@mui/material/styles';

import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';

import Badge from '@mui/material/Badge';
import MenuItem from '@mui/material/MenuItem';
import Menu from '@mui/material/Menu';
import MenuIcon from '@mui/icons-material/Menu';
import AccountCircle from '@mui/icons-material/AccountCircle';
import DeleteIcon from '@mui/icons-material/Delete';

import MailIcon from '@mui/icons-material/Mail';

import MoreIcon from '@mui/icons-material/MoreVert';

 
import AddShoppingCartIcon from '@mui/icons-material/AddShoppingCart';

import { useNavigate } from 'react-router-dom';
import '../CSS/Product.css';

import DialogContentText from '@mui/material/DialogContentText';

import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';

import SearchIcon from '@mui/icons-material/Search';
import { alpha } from '@mui/material/styles';
import Paper from '@mui/material/Paper';
import InputBase from '@mui/material/InputBase';

import Button from '@mui/material/Button';
 

import AppBar from '@mui/material/AppBar';

 
import Slide from '@mui/material/Slide';
 


import Shopping from '../images/quickbuy-1-logo (1).png';
import ProductAdd from "./ProductAdd";

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: 'center',
  color: theme.palette.text.secondary,
}));

const useStyles = styled({
  root: {
    '&:hover': {
      textDecoration: 'underline', // add any other styles you want here
    },
  },
});




function Navbar({itemCount2}) {

  const [itemCount, setitemCount] = useState(itemCount2);
  console.log("---navbar cat number--",itemCount2);

    // setitemCount(itemCount2);

  const isLoggedIn = localStorage.getItem('roles') == 'ADMIN';

  const classes = useStyles();
 

  const data = {
    "userId": localStorage.getItem("userId"),
    "orderStatus": "ACTIVE"
  };

 

  const [cartInfo, setCartInfo] = useState([]);
  const [cartid, setCartId] = useState(0);

   async function handleClickOpenCart() {

          navigate('/mycart');
     

    try {
      await axios.post('http://localhost:8080/cart/data', data
      , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
      .then(res => {
       console.log(res.data);
        setCartId(res.data.cartId)
        setCartInfo(res.data.item);

        console.log(cartInfo);

      })
    } catch (error) {

      console.error(error);

      if(!cartInfo){
        return navigate('/notfound');
      }


    }
 
 

  };

 


  const navigate = useNavigate();


  const handleCategory = () => {

    navigate('/category');

  };

  const handleMyOrders = () => {

    navigate('/myorders');

  };



  const handleProduct = () => {

    navigate('/home');

  };


 

  

  const handleLogout = () => {


    setAnchorEl(null);
    handleMobileMenuClose();
    navigate('/');
    localStorage.removeItem('Token')
    localStorage.removeItem('roles')
    localStorage.removeItem('username')
    localStorage.removeItem('email')
    localStorage.removeItem('profile')
    localStorage.removeItem('lastname');
    localStorage.removeItem('userId');
    
     
  };



  const [anchorEl, setAnchorEl] = React.useState(null);
  const [mobileMoreAnchorEl, setMobileMoreAnchorEl] = React.useState(null);

  const isMenuOpen = Boolean(anchorEl);
  const isMobileMenuOpen = Boolean(mobileMoreAnchorEl);

  const handleProfileMenuOpen = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMobileMenuClose = () => {
    setMobileMoreAnchorEl(null);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
    handleMobileMenuClose();
  };

  const handleMobileMenuOpen = (event) => {
    setMobileMoreAnchorEl(event.currentTarget);
  };

  const menuId = 'primary-search-account-menu';
  const renderMenu = (
    <Menu
      anchorEl={anchorEl}
      anchorOrigin={{
        vertical: 'top',
        horizontal: 'right',
      }}
      id={menuId}
      keepMounted
      transformOrigin={{
        vertical: 'top',
        horizontal: 'right',
      }}
      open={isMenuOpen}
      onClose={handleMenuClose}
    >
      <MenuItem onClick={handleLogout}>Log out</MenuItem>

    </Menu>
  );

 

      const[color, setColor] = useState('default')
       
     

 

  const handleClickChild = () => {
    // if(!isLoggedIn){

        setColor('error');
      
    //   axios.post('http://localhost:8080/cart/data', data
    //     , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
    //     .then(res => {
   
    //       {
    //         res.data.item.map(name => (
  
    //           cartCount.push(name.itemId)
  
  
    //         ))
    //       }
  
    //       setitemCount(cartCount.length);

    //       console.log(itemCount);

    //       setColor('error');
  
    //     })
    //     .catch(e => {
  
    //       console.log(e.message);
  
    //     })
    //   }
  
  }

  useEffect(() => {
    handleClickChild();
   
  }, []);

    







  const mobileMenuId = 'primary-search-account-menu-mobile';
  const renderMobileMenu = (
    <Menu
      anchorEl={mobileMoreAnchorEl}
      anchorOrigin={{
        vertical: 'top',
        horizontal: 'right',
      }}
      id={mobileMenuId}
      keepMounted
      transformOrigin={{
        vertical: 'top',
        horizontal: 'right',
      }}
      open={isMobileMenuOpen}
      onClose={handleMobileMenuClose}
    >

    

      
                 
                <MenuItem>
        <Button onClick={handleProduct} sx={{ color: '#000000' }}>
          Product
        </Button>
        </MenuItem>
        {isLoggedIn ? (
          <>
            <MenuItem>
            <Button onClick={handleCategory} sx={{ color: '#000000' }}>
              Category
            </Button>
            </MenuItem>
          </>
        ) : (
          ''
        )}
          

          {isLoggedIn ? (
        <>

          
        </>
      ) : (
        <MenuItem>
          <IconButton
            size="large"
            aria-label="show 1 new notifications"
            color="inherit"
            onClick={handleClickOpenCart}
          >
            <Badge badgeContent={
              itemCount2
              } color={color}>
              <AddShoppingCartIcon />
            </Badge>
          </IconButton>
          <p>Cart</p>
        </MenuItem>
      )}


        <MenuItem>
        {isLoggedIn ? (
                <>

                </>
              ) : (
                <Button onClick={handleMyOrders} sx={{ fontFamily: "revert-layer", color: '#000000' }}>
                  My Orders
                </Button>
              )}
        </MenuItem>
      
    
       


      <MenuItem onClick={handleProfileMenuOpen}>
       
        <IconButton
          size="large"
          aria-label="account of current user"
          aria-controls="primary-search-account-menu"
          aria-haspopup="true"
          color="inherit"
        >
           <Avatar src={localStorage.getItem('profile')}>
                    {localStorage.getItem('username')[0]}
                    </Avatar>
        </IconButton>
        
      </MenuItem>
    </Menu>
  );


 

       
        // const handlePlaceOrder = () => {
        //   const date = new Date();
        //   const orderDetails = {
        //     "cartId": cartid,
        //     "userId": localStorage.getItem("userId"),
        //     "date": date
    
        //   };

        //   axios.post('http://localhost:8080/order/user', orderDetails
        //   , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
        //   .then(res => {
            
        //     console.log(res.data);
           
        //   })
        //   .catch(e => {
    
        //     console.log(e.message);
    
        //   })
    
    

        // };

        // /*  End order APi  */

const handleOpenHome =() => {
    navigate('/home');
}


  return (
    <div>
      <Box sx={{ flexGrow: 1 }}>
        <AppBar sx={{ background: '#063970' }} position="fixed">
          <Toolbar>
            
              <img onClick={handleOpenHome} style={{height:40, width:50, cursor:'pointer', display: { xs: 'none', md: 'flex' }, mr: 1}} src={Shopping}/>
             
              {/* <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
              QuickBuy
          </Typography> */}



            <Typography
              variant="h6"
              noWrap

              component="div"
              sx={{ display: { xs: 'none', sm: 'block' } }}
            >
             
            </Typography>
            <Typography
              variant=""
              noWrap
              component="div"
              sx={{ paddingLeft: 5, display: { xs: 'none', sm: 'block' } }}
            >

            </Typography>



            <Box
              component="form"
              sx={{
                paddingLeft: 5,
                color: 'white'
              }}
              noValidate
              autoComplete="off"
            >

            </Box>
            <Box sx={{ display: { xs: 'none', sm: 'block' } }}>

           


            </Box>

 


            <Box sx={{ flexGrow: 1 }} />

 

            <Box sx={{ display: { xs: 'none', md: 'flex' } }}>

            <Button 
            className={classes.root} 
            onClick={handleProduct} sx={{ color: '#fff' }}>
                <Typography >Product</Typography>
                
              </Button>
              {isLoggedIn ? (
                <>
                  <Button 
                  className={classes.root}  
                  onClick={handleCategory} sx={{ color: '#fff' }}>
                  <Typography >Category</Typography>  
                  </Button>

                </>
              ) : (
                ''
              )}




              {isLoggedIn ? (
                <>


                </>
              ) : (
                <IconButton
                  size="large"
                  aria-label="show 17 new notifications"
                  color="inherit"
                  onClick={handleClickOpenCart}
                >
                  <Badge 
                  badgeContent={
                    itemCount2
                  } 
                  color={color}>

                    <AddShoppingCartIcon />
                  </Badge>
                </IconButton>
              )}


              {isLoggedIn ? (
                <>

                </>
              ) : (
                <Button onClick={handleMyOrders} sx={{ fontFamily: "revert-layer", color: '#fff' }}>
                 <Typography>My Orders</Typography> 
                </Button>
              )}









              <Toolbar>
                <Typography style={{cursor:'pointer'}}>
                  {localStorage.getItem('username')+' '}{localStorage.getItem('lastname')}
                </Typography>
              </Toolbar>

              <Tooltip title={localStorage.getItem('username')}>
                <IconButton onClick={handleProfileMenuOpen} aria-controls={menuId} sx={{ p: 0 }}>
                  <Avatar src={localStorage.getItem('profile')}>
                    {localStorage.getItem('username')[0]}
                    </Avatar>
                </IconButton>
              </Tooltip>

              {/* <IconButton
              size="large"
              edge="end"
              aria-label="account of current user"
              aria-controls={menuId}
              aria-haspopup="true"
              onClick={handleProfileMenuOpen}
              color="inherit"
            >



              <AccountCircle />
            </IconButton> */}
            </Box>
            <Box sx={{ display: { xs: 'flex', md: 'none' } }}>
              <IconButton
                size="large"
                aria-label="show more"
                aria-controls={mobileMenuId}
                aria-haspopup="true"
                onClick={handleMobileMenuOpen}
                color="inherit"
              >
                <MoreIcon />
              </IconButton>
            </Box>
          </Toolbar>
        </AppBar>
        {renderMobileMenu}
        {renderMenu}
      </Box>
     
   
    </div>
  )
}

export default Navbar
