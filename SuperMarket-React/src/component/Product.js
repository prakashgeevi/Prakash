// import ReactDOM from "react-dom";
import React, { useState, useEffect, useTransition } from "react";
import axios from "axios";
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import AddIcon from '@mui/icons-material/Add';
import SearchIcon from '@mui/icons-material/Search';
import { InputAdornment } from '@mui/material';
import '../CSS/SearchBox.css'


import Typography from '@mui/material/Typography';

import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';

import MenuItem from '@mui/material/MenuItem';

import { styled } from '@mui/material/styles';



import { useNavigate } from 'react-router-dom';
import '../CSS/Product.css';



import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';


import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';



import InputLabel from '@mui/material/InputLabel';


import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';


import EditIcon from '../images/editIcon.png';


import DeleteIcon from '../images/icons8-ecommerce-64.png';


import Navbar from "./Navbar";

import ProductAdd from "./ProductAdd";


import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';

 

// import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

import ProductAddButton from './ProductAddButton';
import StockCount from "./StockCount";
import Footer from "./Footer";
 
 
 






// const drawerWidth = 240;
const navItems = ['Product', 'Category'];


const styles = {
  dialog: {
    backgroundImage: "url(https://i.imgur.com/HeGEEbu.jpg)"
  }
};

const config = {

  headers: {
    "Authorization": `Bearer ${localStorage.getItem('Token')}`
  }
};



export default function Product() {



  const [cartCount, setCartCount] = React.useState();

console.log(cartCount);

  const isLoggedIn = localStorage.getItem('roles') == 'ADMIN';


  //select option 
  const [age, setcategory] = React.useState('');

  const handleChange = (event) => {
    setcategory(event.target.value);
  };

  const [selectedOption, setSelectedOption] = useState("");



  const navigate = useNavigate();
  const [open, setOpen] = React.useState(false);
  const [editopen, seteditOpen] = React.useState(false);

  const [cartOpen, setcartOpen] = React.useState(false);

  const [totalCartOpen, settotalCartOpen] = React.useState(false);



  const handleCartOpen = (id) => {

    setItemproductId(id)


    setcartOpen(true);
  }


  const handleClickOpen = () => {

    setOpen(!open);
    if (open === true) {
      axios.get(api, { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
        .then(res => {

          setproduct(res.data);


        })
        .catch(e => {


        })
    }
  };



  const handleClose = () => {
    handleClickOpen();

    setproductName('');
    setprice('');
    setstock('');
    setunit('');
    setSelectedOption('');
  };



  const handleCartClose = () => {
    setcartOpen(false);

  };

  const handleClickOpenCart = () => {

    settotalCartOpen(true);
  };

  const handleTotalCartClose = () => {

    settotalCartOpen(false);
  };



  const handleClose2 = () => {
    seteditOpen(false);
    setproductName('');
    setprice('');
    setstock('');
    setunit('');
    setSelectedOption('');
  };




  const handleCategory = () => {

    navigate('/category');

  };


  //  style for edit delte button






  const CustomButton = styled(Button)({


    backgroundColor: '#063970',
    border: 0,
    borderRadius: 40,
    boxShadow: '0 3px 5px 2px rgba(255, 105, 135, .3)',
    color: 'white',
    height: 48,
    padding: '0 30px',
    height: 30,
    width: 80,



  });


  const [searchValue, setsearchValue] = useState('');

  const handleSearchValue = (e) => {
    setsearchValue(e.target.value)
  }



  const [products, setproduct] = useState([]);

  const api = `http://localhost:8080/product/search?productName=${searchValue}`

  useEffect(() => {


 

    getAllProducts();

    getCartCount();

    if(isLoggedIn){
    const apic = `http://localhost:8080/category/ordercategory/`+ localStorage.getItem('userId')
    axios.get(apic, { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
      .then(res => {


        setcategory1(res.data);


      })
      .catch(e => {
        console(e.message);
      })
    }

  }, [searchValue]);


  const [category1, setcategory1] = useState([]);



  function getAllProducts() {

    axios.get(api, { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
      .then(res => {

        setproduct(res.data);





      })
      .catch(e => {


      })

  }

  function getCartCount(){
    const data = {
      "userId": localStorage.getItem("userId"),
      "orderStatus": "ACTIVE"
    };
    axios.post('http://localhost:8080/cart/data', data
        , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
        .then(res => {
   
          {
            console.log("------", res.data.item.length);
           setCartCount( res.data.item.length);
          }
  
  
        })
        .catch(e => {
  
          console.log(e.message);
  
        })
  }





  /*  Add New Product      */


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












  const [file, setFile] = useState(null);
  const [productName, setproductName] = useState('');
  const [price, setprice] = useState('');
  const [stock, setstock] = useState('');
  const [unit, setunit] = useState('');

 const handlePriceChange = (event) => {
    const inputValue = event.target.value;

    // Allow only numbers and a dot (for decimal places)
    const numericValue = inputValue.replace(/[^0-9.]/g, '');

    // Update the state with the numeric value
    setprice(numericValue);

 }
 const handleStockChange = (event) => {
  const stockValue = event.target.value;

  // Allow only numbers and a dot (for decimal places)
  const numericStockValue = stockValue.replace(/[^0-9.]/g, '');

  // Update the state with the numeric value
  setstock(numericStockValue);

}



  const handleSubmit = () => {

    setsnackOpen(true);
    const formData = new FormData();

    if (!file) {
      setmessage('Please upload product image');
      return;
    }
    if (!productName) {
      setmessage('Product is name is required');
      return;
    }

    if (!stock) {
      setmessage('Please Enter the Stock');
      return;
    }

    if (!unit) {
      setmessage('which type of product you add. Please add unit of product');
      return;
    }

    if (!selectedOption) {
      setmessage('Choose category in your product');
      return;
    }
    if (!price) {
      setmessage('Please Enter the procut price');
      return;
    }


    formData.append('imagefile', file);

    formData.append('data', JSON.stringify({
      "productName": productName,
      "stock": stock,
      "unit": unit,
      "categoryId": selectedOption,
      "price": price,
      "userId": localStorage.getItem('userId')


    }))

    axios.post('http://localhost:8080/product', formData, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('Token')}`,
        'Content-Type': 'multipart/form-data'
      }
    })
      .then((response) => {
        console.log(response);
        setmessage('product added');


        setproductName('');
        setprice('');
        setstock('');
        setunit('');
        setSelectedOption('');

        handleClickOpen();

      })
      .catch((error) => {

        console.log(error);
        setmessage(error.response.data.message);




        setproductName('');
        setprice('');
        setstock('');
        setunit('');
        setSelectedOption('');
      });

  };


  /************  get by id for update in product   **&&&&&&&&&&&********/

  const [updateproductName, setupdateproductName] = useState('');
  const [updateprice, setupdatetprice] = useState('');
  const [updatestock, setupdatestock] = useState('');
  const [updateunit, setupdateunit] = useState('');
  const [updateCategory, setupdateCategory] = useState();

  const [imageId, setimageId] = useState();
  const [updatefile, setupdateFile] = useState(null);
  const [productId, setproductId] = useState('');


  const [updateCategoryId, setupdateCategoryId] = useState();


  const handleDelete = (id) => {

    axios.delete('http://localhost:8080/product/' + id +'/'+localStorage.getItem('userId'), {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('Token')}`

      }
    })
      .then((res, status) => {

        console.log(res.data, status);

        getAllProducts();





      })
      .catch((error) => {
        setsnackOpen(true);
        setmessage('Cant delete this product. customers added they are cart');

        setOpen(false);
      });

  }


  const handleEdit = (id) => {


    seteditOpen(true);
    axios.get('http://localhost:8080/product/' + id, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('Token')}`

      }
    })
      .then((res) => {


        setproductId(res.data.productId);
        setupdateproductName(res.data.productName);
        setupdatetprice(res.data.price);
        setupdatestock(res.data.stocks);
        setupdateunit(res.data.unit);
        setupdateCategoryId(res.data.category.categoryId);
        setupdateCategory(res.data.category.categoryName)
        // setupdateCategory(<MenuItem value={res.data.category.categoryId}   >
        // {res.data.category.categoryName}</MenuItem>);

        setimageId(res.data.productImage.imageId);
      })
      .catch((error) => {
        setmessage('Cant empty field')

        setOpen(false);
      });



  }





  /********   get Id end    ****************/


  /*  For edit Api
    */

  const handleEdit2 = (id) => {
    setsnackOpen(true);
    if (updateproductName == '') {
      setmessage('Please Give product name cant empty')
      return;
    }
    if (updatestock == '') {
      setmessage('Please Give Stock name cant empty')
      return;
    }
    if (updateunit == '') {
      setmessage('Please Give unit value')
      return;
    }
    if (updateprice == '') {
      setmessage('Please Give price value')
      return;
    }
    if (imageId == '') {
      setmessage('Please upload product image')
      return;
    }


    const formData = new FormData();
    formData.append('imagefile', updatefile);



    formData.append('data', JSON.stringify({
      "productName": updateproductName,
      "stock": updatestock,
      "unit": updateunit,
      "categoryId": updateCategoryId,
      "price": updateprice,
      "imageId": imageId,
      "userId": localStorage.getItem('userId')

    }))

    axios.put('http://localhost:8080/product/' + id, formData, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('Token')}`,
        'Content-Type': 'multipart/form-data'
      }
    })
      .then((response) => {
        setmessage('product updated')
        console.log(response)
        seteditOpen(false);
        setOpen(false);
        getAllProducts();



      })
      .catch((error) => {

        console.log(error);

        setOpen(false);
      });

  }














  const ResponsiveImage = ({ src, alt }) => {
    return (

      <div className="image-wrapper">
        <img className="responsive-image" src={src} alt={alt} />
      </div>
    );
  };

  const [itemproductId, setItemproductId] = useState();


  /* To get productId and show the user can choose item and its quantity  */
  // const [cartproduct, setcartproduct] = useState([]);

  // const handleAddToCart = (id) => {

  //  setItemproductId(id)



  //   setcartOpen(true);
  //   axios.get('http://localhost:8080/product/' + id, {
  //     headers: {
  //       Authorization: `Bearer ${localStorage.getItem('Token')}`

  //     }
  //   })
  //     .then((res) => {

  //       setcartproduct(res.data);



  //     })
  //     .catch((error) => {

  //       alert('error')

  //       setOpen(false);
  //     });
  // };


  const AddtocartButton = ({ children, onClick }) => {
    return (
      <button className="add-to-cart-button" onClick={onClick}>
        {children}
      </button>
    );
  };

  const EditButton = ({ children, onClick }) => {
    return (
      <button className="edit-button" onClick={onClick}>
        {children}
      </button>
    );
  };

  const handleUpdatecategory = (event) => {

    setupdateCategoryId(event.target.value)

  }



  return (

    <div>
      <>
        <Navbar itemCount2 = {cartCount}/>

     
        
      </>
      <br />
      <br />
      <br />


      {isLoggedIn ? (
        <Box    className="addBtn" sx={{ "& > :not(style)": { m: 1 }}}>
          <button
            onClick={handleClickOpen}
            style={{
              float: "left",
              marginLeft: "20px",
              // marginTop: "40.5px",
              border: "2px solid #2E8BC0",
              color: "white",
              padding: "5px 5px",
              backgroundColor: "#2E8BC0",
              cursor: "pointer",
              fontSize: "16px",
              borderRadius: "25px",
            }}
          >

            Add Product
          </button>
          <br />
          <br />
          <br />
          <br />
          <TextField
            style={{
              marginTop: "-86px",
              marginLeft: "60%",
              width: "300px",

              borderRadius: "25px",
            }}
            InputProps={{
              endAdornment: (
                <InputAdornment>
                  <IconButton>
                    <SearchIcon />
                  </IconButton>
                </InputAdornment>
              ),
            }}
            placeholder="Search Product or category..."
            className="searchBar"
            type="text"
            // value={searchText}
            onChange={handleSearchValue}
          />
        </Box>
      ) : (
        <Box  className="addBtn" sx={{ "& > :not(style)": { m: 1 } }}>
          <br />
          <TextField
            style={{
              marginTop: "-5px",
              marginLeft: '70%',
              width: "300px",
              height: "100px",
              borderRadius: "25px",
            }}
            InputProps={{
              endAdornment: (
                <InputAdornment>
                  <IconButton>
                    <SearchIcon />
                  </IconButton>
                </InputAdornment>
              ),
            }}
            // style={{ marginTop: "80px" }}
            placeholder="Search Product or category..."
            className="userSearchBar"
            type="text"
            // value={searchText}
            onChange={handleSearchValue}
          />
        </Box>
      )}

      {/* {isLoggedIn ? (
        <>

          <ProductAddButton label="Add Product" onClick={handleClickOpen} />
          {/* <Button sx={{ marginTop: 2 }} variant="outlined" onClick={}>
           
          </Button> */}
      {/* </> */}
      {/* ) : ( */} 
      <Button sx={{}}></Button>
      {/* )} */}
      {/* <div className="search-container">
         <input type="text" placeholder="Search Product or category..." className="search-box" onChange={handleSearchValue} />
       </div>  */}

 
 
      <Box sx={{ flexGrow: 1, display: 'flex', justifyContent: 'center' }} >
        <Grid container spacing={1} style={{ direction: 'row', padding: '15px' }}>

          {products.map((items) => (

  
   

            <Grid disabled={items.stock === 0} item xs={6} md={6} lg={3} style={{ marginBottom: 15 }}>

              <Card sx={{ maxWidth: 200, maxHeight: 400 }}>
                <CardMedia>
                  <ResponsiveImage src={'data:image/png;base64,' + items.data} alt="Alt text for image" />
                </CardMedia>


                <CardContent>
                  <StockCount value={items.stock} />
                  <Typography style={{ fontStyle: 'bold', color: "#000000", }} component="div">
                    {items.productName}&nbsp; <span><b>&#x20B9;</b></span><strong>&nbsp;{items.price}</strong>

                  </Typography>

                  {isLoggedIn ? (
                    <>

                      {/* <p>  {'Stocks: '+items.stock}&nbsp;{items.unit}</p> */}
                      {/* <Typography style={{fontWeight:500}}   gutterBottom   component="div">
                   {'Stocks: '+items.stock}&nbsp;{items.unit}
                  </Typography> */}
                    </>
                  ) : (
                    ""
                  )}
                </CardContent>
                <CardActions>


                  {isLoggedIn ? (
                    <>

                      {/* <CustomButton    style={{   marginLeft:7, bottom:10,top:-3}} onClick={() => handleEdit(items.productId)}  size="small">EDIT</CustomButton>
         <CustomButton onClick={() => handleDelete(items.productId)}  style={{  bottom:10,top:-3 }} size="small">DELETE</CustomButton> */}


                      <EditButton onClick={() => handleEdit(items.productId)} >
                        <b><img height={30} width={30} src={EditIcon} /></b> </EditButton>

                      <AddtocartButton onClick={() => handleDelete(items.productId)} >
                        <b><img height={30} width={30} src={DeleteIcon} /></b> </AddtocartButton>

                    </>
                  ) : (

                    <AddtocartButton onClick={() => handleCartOpen(items.productId)} >
                      Add To cart </AddtocartButton>


                  )}

                </CardActions>
              </Card>
            </Grid>


          ))}




        </Grid>
      </Box>



















      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Adding New Product</DialogTitle>
        <DialogContent>

          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Product Name"
            type="email"
            fullWidth
            variant="standard"
            value={productName}
            onChange={(e) => setproductName(e.target.value)}
          />
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Price "
            type="text"
            fullWidth
            variant="standard"
            value={price}
            onChange={handlePriceChange}
          />
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Stock"
            type="text"
            fullWidth
            variant="standard"
            value={stock}
            onChange={handleStockChange}
          />
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Unit"
            type="text"
            fullWidth
            variant="standard"
            value={unit}
            onChange={(e) => setunit(e.target.value)}
          />

          <FormControl variant="standard" sx={{ m: 1, minWidth: 150 }}>
            <InputLabel id="demo-simple-select-standard-label">Category</InputLabel>


            {/* <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Product Name"
            type="email"
            fullWidth
            variant="standard"
            value={getCategoryId}
            onChange={(e) => setCategory(e.target.value)}
          /> */}




            <Select
              labelId="demo-select-small"
              id="demo-select-small"
              value={selectedOption}
              label="Category"
              onChange={(event) => setSelectedOption(event.target.value)}
            >
              {category1.map((item) => (

                <MenuItem value={item.categoryId}   >
                  {item.categoryName}</MenuItem>

              ))}

            </Select>

          </FormControl>






          <Stack sx={{ marginTop: 2 }} direction="row" alignItems="center" spacing={2}>
            <Button variant="contained" component="label">
              Uploading Product image
              <input hidden value={''} onChange={(e) => setFile(e.target.files[0])} type="file" />
            </Button>

          </Stack>

        </DialogContent>
        <DialogActions>



          <ProductAddButton label="Add" onClick={handleSubmit} />
          <ProductAddButton label="Cancel" onClick={handleClose} />
          {/* <button  onClick={handleSubmit}>Add</button>
          <button onClick={handleClose}>Cancel</button> */}
        </DialogActions>
      </Dialog>







      {/* for uploading a product */}

      <Dialog open={editopen} onClose={handleClose2}>
        <DialogTitle>Updating a Product</DialogTitle>
        <DialogContent>

          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Product Name"
            type="text"
            fullWidth
            variant="standard"
            value={updateproductName}
            onChange={(e) => setupdateproductName(e.target.value)}
          />
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Price "
            type="text"
            fullWidth
            variant="standard"
            value={updateprice}
            onChange={(e) => setupdatetprice(e.target.value)}
          />
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Stock"
            type="text"
            fullWidth
            variant="standard"
            value={updatestock}
            onChange={(e) => setupdatestock(e.target.value)}
          />
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="units"
            type="text"
            fullWidth
            variant="standard"
            value={updateunit}
            onChange={(e) => setupdateunit(e.target.value)}
          />

          <FormControl variant="standard" sx={{ m: 1, minWidth: 150 }} size="small">
            <InputLabel id="demo-simple-select-standard-label">Category</InputLabel>
            <Select
              labelId="demo-select-small"
              id="demo-select-small"
              value={updateCategoryId}

              label="Category"
              onChange={handleUpdatecategory}
            >
              {/* <MenuItem value={updateCategoryId} selectedOption={updateCategory} >{updateCategory}</MenuItem> */}
              {category1.map((item) => (
                <MenuItem value={item.categoryId} >
                  {item.categoryName}
                </MenuItem>
              ))}
              {/* **************************   */}
            </Select>
          </FormControl>



          <Stack sx={{ marginTop: 2 }} direction="row" alignItems="center" spacing={2}>
            <Button variant="contained" component="label">
              Uploading Product image
              <input hidden value={''}
                onChange={(e) => setupdateFile(e.target.files[0])}
                type="file" />
            </Button>

          </Stack>

        </DialogContent>
        <DialogActions>
          {/* <Button onClick={handleClose2}>Cancel</Button>
          <Button onClick={() => handleEdit2(productId)}>Update</Button> */}

          <ProductAddButton label="Update" onClick={() => handleEdit2(productId)} />
          <ProductAddButton label="Cancel" onClick={handleClose2} />

        </DialogActions>
      </Dialog>


      {/* end update dialog box */}



      {/* add to cart dialog box */}






      {/* Total cart dialog box */}

      <Dialog open={totalCartOpen} onClose={handleCartClose}>
        <DialogTitle>Total Item in your cart</DialogTitle>
        <DialogContent>


        </DialogContent>
        <DialogActions>
          <Button onClick={handleTotalCartClose}>Cancel</Button>
          <Button onClick={handleSubmit}>Add</Button>
        </DialogActions>
      </Dialog>


      {/* total cart dialog box end */}


      <Dialog
        fullWidth={'md'}
        open={cartOpen}
        onClose={handleCartClose}
      >

        <DialogTitle>Adding Product in your cart</DialogTitle>

        {<ProductAdd handleCartClose={handleCartClose} productId={itemproductId} setCartCount={setCartCount}
        //  handleClose={}
        />}

      </Dialog>



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


     {/* <Navbar itemCount2={<CartCount/>}/> */}



      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>

      {/* <Divider>CENTER</Divider> */}

      {

        <Footer />
 

      }



    </div>

  )
}

