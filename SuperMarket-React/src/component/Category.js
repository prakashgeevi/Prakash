import ReactDOM from "react-dom";
import React, { useState, useEffect, useMemo } from "react";
import axios from "axios";
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import CreateIcon from '@mui/icons-material/Create';
import DeleteIcon from '@mui/icons-material/Delete';
 
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Navbar from "./Navbar";

import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
 
  

import '../CSS/Product.css';
  

import ProductAddButton from './ProductAddButton';
import Box from '@mui/material/Box';

export default function Category() {
  
   


  const [snackopen, setsnackOpen] = React.useState(false);

  const[message, setmessage]= useState('');

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

 
  
  const [open, setOpen] = React.useState(false);

  const [editOpen, setEditOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(!open);
    getCategory();
  };

  const handleClose = () => {
    
    setaddCategory('');
    handleClickOpen();


  };

  const handleEditClose = () => {
    setEditOpen(false);
    setUpdateCategory('');
 
  };
 
  

     const [categories, setcategories] = useState([]);
   
    console.log(categories.length);

     useEffect(() => {

      getCategory();
    
 

  }, []);
 

  function getCategory(){
    const api = `http://localhost:8080/category/ordercategory/`+ localStorage.getItem('userId')

    axios.get(api, { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
      .then(res => {
           
        console.log(res.data);
        setcategories(res.data);


      })
      .catch(e => {

        console.log(e)
      })
    }
    
    const [updateCategory, setUpdateCategory] = useState();

  const [addCategory, setaddCategory] = useState();
  const categoryData = {"categoryName": updateCategory,
                         "userId":localStorage.getItem('userId')                        
                      }
   
  const handleSubmit = () => {

    setsnackOpen(true);
    if(!addCategory){
      
      setmessage('Please enter the category name')
      return ;
    }

    axios.post('http://localhost:8080/category', {"categoryName": addCategory,
    "userId":localStorage.getItem('userId')                        
 },
      
    {
     headers: {
       Authorization: `Bearer ${localStorage.getItem('Token')}`,
       'Content-Type': 'application/json'
     }
   })
   .then((response) => {
   
      console.log(response);
       setmessage('category added');
       setaddCategory('');
       getCategory();
       
       handleClickOpen();
    
     
      
   })
   .catch((error) => {
    setmessage(error.response.data.message);
    setaddCategory(''); 
    console.log(error.response.data.message);    
    handleClickOpen();
   });
}; 
 
 
 

const handleDelete = (id) => {
 
  const api = `http://localhost:8080/category/`+ id +'/'+localStorage.getItem('userId')

  axios.delete(api, { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
    .then(res => {
      getCategory();
      console.log(res.data);
      
    })
    .catch(e => {
       console.log(e)
       setsnackOpen(true);
      setmessage("can't delete this category because this category related to some product");
     
    })

}
 


const [updateId, setUpdateId] = useState();
const handleEdit = (id) => {

  setEditOpen(true);

  const api = `http://localhost:8080/category/`+ id

  axios.get(api, { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
    .then(res => {
         
      console.log(res.data);
      setUpdateCategory(res.data.categoryName);
      setUpdateId(id)
       


    })
    .catch(e => {

      console.log(e)
    })

}

const handleUpdate = () => {

  

  const api = `http://localhost:8080/category/`+ updateId

  axios.put(api, categoryData,
     { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
    .then(res => {
      
      setsnackOpen(true);
      setmessage('category updated');
       
     
      setEditOpen(false);
      getCategory();
    })
    .catch(e => {
      setEditOpen(false);
      console.log(e)
    })

}




// const columns = useMemo(
//   () => [
//     {
//       accessorKey: "categoryName", //simple recommended way to define a column
//       id:'categoryName',
//       header: "Name",
//       muiTableHeadCellProps: { sx: { color: "green" } }, //custom props
//       Cell: ({ renderedCellValue }) => <strong>{renderedCellValue}</strong> //optional custom cell render
//     },
    
//   ],
//   []
// );

 

const rows = categories.map((item) => (
  <tr key={item.categoryId}>
    <td>{item.categoryName}</td>
    <td><Button onClick={() => handleEdit(item.categoryId)}><CreateIcon /></Button></td>
      <td><Button style={{color: "#C8626D"}} onClick={() => handleDelete(item.categoryId)}><DeleteIcon /></Button></td>
  </tr>
));





 

  return (
  
      <div className="App">

      <Navbar />

      <Box style={{marginTop:70}}>
      <ProductAddButton label='Add Category'  onClick={handleClickOpen} />
      </Box>
      {/* <Button sx={{marginTop: 10 }} variant="outlined" onClick={handleClickOpen}>
         Add Category
      </Button> */}


      <div className="table-container">
      <table>
        <thead>
          <tr>
            <th>Category Name</th>
            <th style={{textAlign:'center'}} colSpan={2}>Action</th>
             
          </tr>
        </thead>
        <tbody>{rows}</tbody>
      </table>

      </div> 







      {/* <MaterialReactTable columns={categories} data={categories} /> */}

        



        
      {/* <table align="center" style={{marginTop: 10}}>
      <thead>
        <tr>
          <th>S.No</th>
          <th >Category Name</th>
          <th colSpan="2">Action</th>
        </tr>
      </thead>
      <tbody>
        {categories.map((item, index) => (
          <tr key={index}>
            <td>{index + 1}</td>
            <td>{item.categoryName}</td>
            <td colSpan="2"><CreateIcon /></td>
            <td colSpan="2"><DeleteIcon /></td>
          </tr>
        ))}
      </tbody>
    </table> */}
        
      
        
        
        
         {/* <TableContainer  component={Paper}>

      <Table sx={{ minWidth: 450, marginTop: 5 }} >
         
        <TableHead sx={{backgroundColor: 'blueviolet'}}>
          <TableRow>
            <TableCell sx={{textAlign: 'center', fontSize:18, }}> Category Name</TableCell>
             <TableCell sx={{textAlign: 'center',fontSize:18, }} colSpan={2}>Action</TableCell>
          </TableRow>
        </TableHead>

        <TableBody>
          {categories.map((row) => (
                
            <TableRow sx={{borderBlockStartColor: 'red'}} key={row.categoryId}>
              <TableCell sx={{textAlign: 'left', fontSize:18, fontWeight:500 }} component="th" scope="row">
                
                {row.categoryName}
              </TableCell>
              <TableCell sx={{textAlign: 'center', fontSize:18,   }} component="th" scope="row"><DeleteIcon /> </TableCell>
              <TableCell sx={{textAlign: 'center', fontSize:18,  }} component="th"  scope="row"> <CreateIcon />  </TableCell>
              
            </TableRow>
          ))}

        </TableBody>
      </Table>
    </TableContainer> */}
       
    {/* <Stack spacing={7}>
       
      <Pagination
      
      count={12} color="secondary" />
     
    </Stack> */}

    
        
     
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Adding a Category</DialogTitle>
        <DialogContent>
          <DialogContentText>
             You add some category depends on the product type
          </DialogContentText>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Add Category"
            type="text"
            fullWidth
            value={addCategory}
            onChange={(e) => setaddCategory(e.target.value)}
        
            variant="standard"
          />
        </DialogContent>
        <DialogActions>

        <ProductAddButton  label="Add" onClick={handleSubmit}/>
            <ProductAddButton  label="Cancel" onClick={handleClose}/>

          {/* <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleSubmit}>ADD</Button> */}
        </DialogActions>
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
      
      <Dialog open={editOpen} onClose={handleEditClose}>
        <DialogTitle>Updating a Category</DialogTitle>
        <DialogContent>
         
          <TextField
            autoFocus
            margin="dense"
            id="name"
             
            type="text"
            fullWidth
            value={updateCategory}
            onChange={(e) => setUpdateCategory(e.target.value)}
        
            variant="standard"
          />
        </DialogContent>
        <DialogActions>
        <ProductAddButton  label="Update" onClick={handleUpdate}/>
            <ProductAddButton  label="Cancel" onClick={handleEditClose}/>
        
          {/* <Button onClick={handleEditClose}>Cancel</Button>
          <Button onClick={handleUpdate}>Update</Button> */}
        </DialogActions>
      </Dialog>





    </div>
  )
}

