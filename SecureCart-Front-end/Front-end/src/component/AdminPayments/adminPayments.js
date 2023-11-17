import React, { useState, useEffect } from "react";
import axios from "axios";
import { styled } from '@mui/material/styles';
import Typography from '@mui/material/Typography';
// import MyOrderCard from "./MyOrderCard";
import orderEmpty from '../../images/order_empty.jpg';
import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import CircularProgress from '@mui/material/CircularProgress';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import Navbar from "../Navbar/Navbar";
import { url } from "../../ServiceApi/ServiceApi";
import { Avatar, Button, FormControl, InputLabel, MenuItem, Select, Snackbar, TextField } from "@mui/material";
import moment from "moment/moment";
const Img = styled('img')({
    margin: 'auto',
    display: 'block',
    maxWidth: '100%',
    maxHeight: '100%',
});
const ExpandMore = styled((props) => {
    const { expand, ...other } = props;
    return <IconButton {...other} />;
})(({ theme, expand }) => ({
    transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
    marginLeft: 'auto',
    transition: theme.transitions.create('transform', {
        duration: theme.transitions.duration.shortest,
    }),
}));
function AdminPayment(cartCount1) {
    const [open, setOpen] = React.useState(false);
    const [message, setmessage] = useState('');
    const sellersUrl = url + 'user/sellers';
    const adminSellerPaymentUrl = url + 'order/admin/payment';
    const [pendingAmount, setPendingAmount] = useState(0);
    const [bankAccount, setBankAccount] = useState();
    const [bankName, setBankName] = useState('');
    const [amount, setAmount] = useState(0);
    const [sellers, setSellers] = useState([]);
    const [selectedSeller, setSelectedSeller] = useState();
    function getAllSellers() {
        axios.get(sellersUrl, { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
            .then(res => {
                setSellers(res.data);
            })
            .catch(e => {
            })
    }

    function getPendingPayment(userId) {
        axios.get(url + 'order/seller/payment/' + userId
            , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
            .then(res => {
                console.log(res.data);
                setPendingAmount(res.data);
            })
            .catch(e => {
                console.log(e);
            })
    }
    useEffect(() => {
        getAllSellers();
    }, []);

    const handleAmountChange = (e) => {
            setAmount(e.target.value);
    }
    const handleSelectedSeller = (event) => {
        setSelectedSeller(event.target.value);
        getPendingPayment(event.target.value);
        sellers.filter(e => e.userId === event.target.value).map(data => {
            setBankAccount(data.bankAccount);
            setBankName(data.bankName);
        })

    }

    const handleClose = (event, reason) => {

        if (reason === 'clickaway') {
    
          return;
        }
    
        setOpen(false);
      };

    function submitPaymentToSeller() {
        if (isNaN(parseInt(amount)) || amount == 0) {
            setOpen(true);
            setmessage("Amount is not valid");
            return false;
        }

        if(parseInt(amount) > parseInt(pendingAmount.pendingAmount)){
            setOpen(true);
            setmessage("Amount is not more than pending amount");
            return false;
        }

        
        const questionDetails = {
            "userId": selectedSeller,
            "amount": amount
          };

        axios.post(adminSellerPaymentUrl, questionDetails
            , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
            .then(res => {
                setOpen(true);
                setmessage("Successfully payment done");
                getPendingPayment(selectedSeller);
                setAmount(0);
            })
            .catch(e => {
      
              console.log(e.response.data.message);
              setmessage(e.response.data.message);
            })

    }

    return (
        <div>
            <Navbar />
            <br />
            <br />
            <br />
            <br />
            <FormControl variant="outlined" style={{ width: '50%' }} sx={{ m: 1 }}>
                <InputLabel id="demo-simple-select-standard-label">Select seller</InputLabel>
                <Select
                    labelId="demo-select-small"
                    id="demo-select-small"
                    value={selectedSeller}
                    label="Select seller"
                    onChange={handleSelectedSeller}
                >
                    {sellers.map((item) => (
                        <MenuItem value={item.userId}   >
                            {item.firstName} {item.lastName}</MenuItem>
                    ))}
                </Select>
            </FormControl>

            {
                bankName && pendingAmount ? (
                    <Card style={{ padding: '30px', width: '30%', margin:'40px' }}>
                        <Typography>
                            Seller Pending Amount: RM :{pendingAmount.pendingAmount}
                        </Typography>
                        <Typography>
                            Seller Bank Name: {bankName}
                        </Typography>
                        <Typography>
                            Seller Bank Account: {bankAccount}
                        </Typography>

                            <TextField
                                id="outlined-multiline-flexible"
                                label="Pay: (RM)"
                                type="number"
                                fullWidth
                                value={amount}
                                onChange={handleAmountChange}
                            />

                        <Button variant='outlined' onClick={() => submitPaymentToSeller()}>Submit</Button>

                    </Card>
                ) : ""
            }

        <Snackbar
        open={open}
        autoHideDuration={6000}
        onClose={handleClose}
        message={message}
        anchorOrigin={{
          vertical: "top",
          horizontal: "right"
        }}
      />

        </div>
    )
}
export default AdminPayment
