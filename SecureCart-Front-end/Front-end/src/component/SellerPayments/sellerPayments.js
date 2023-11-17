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
import { Avatar } from "@mui/material";
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
function SellerPayment(cartCount1) {
  const [expanded, setExpanded] = React.useState(true);
  const handleExpandClick = () => {
    setExpanded(!expanded);
  };
  const [isTrue, setIsTrue] = useState();
  const [isTrue1, setIsTrue1] = useState("");
  const [order, setorder] = useState();
  const [totalAmount, setTotalAmount] = useState();
  const [cartCount, setCartCount] = React.useState();
  const [item, setitem] = useState([]);
  const [dateTime, setDateTime] = useState();
  const [totalAmount1, setTotalAmount1] = useState(0);
  function orderlist() {
    axios.get(url + 'order/seller/' + localStorage.getItem('userId')
      , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
      .then(res => {
        // if (res.data == 0) {
        //   setIsTrue(orderEmpty);
        //   setIsTrue1("Your order list is currently empty.");
        // }
        console.log(res.data);
        setorder(res.data);
      })
      .catch(e => {
        console.log(e);
      })
  }

  function getPendingPayment() {
    axios.get(url + 'order/seller/payment/' + localStorage.getItem('userId')
      , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
      .then(res => {
        console.log(res.data);
        setTotalAmount(res.data);
      })
      .catch(e => {
        console.log(e);
      })
  }
  useEffect(() => {
    orderlist();
    getPendingPayment();
  }, []);
  const handleVariableChange = (receivedVariable) => {
    setDateTime(receivedVariable);
    console.log(receivedVariable);
    // Do something with the receivedVariable in the parent component
  };
  return (
    <div>
      <Navbar
        itemCount2={cartCount}
      />
      <br />
      <br />
      <br />
      <br />
      <Grid container>
        <Grid md={6}>
        <h5 className="text-charcoal d-none d-sm-block float-left">Your Products Sale:</h5>
        </Grid>
        <Grid md={6} style={{textAlign : 'right', padding:'20px'}}>
          {
            totalAmount ? 
<h5 className="float-right">Pending payment: RM {totalAmount.pendingAmount}</h5>
: <h5 className="float-right">Pending payment: RM 0</h5>
          }
        
        </Grid>
        </Grid>
      
     
      
      {order ? (
        Object.values(order).map(each => (
          <>
          <Card style={{ padding: '10px', backgroundColor: 'aliceblue', width: '90%' }}>
            {
              each.length > 0 ? (
                <div>
                  <Typography style={{ flexDirection: 'row', display: 'flex', alignItems: "center" }}>
                    {
                      each[0].orders.user.profilePicture ? (
                        <>
                          <Avatar src={each[0].orders.user.profilePicture} style={{ width: "60px", height: '60px' }}>
                            <img height={60} width={60} src={'data:image/png;base64,' + each[0].orders.user.profilePicture} />
                          </Avatar>
                          &nbsp;<Typography style={{ float: 'left' }}>{each[0].orders.user.firstName} {each[0].orders.user.lastName}</Typography>

                        </>
                      ) : (
                        <>
                          <Avatar src={each[0].orders.user.profilePicture} style={{ width: "60px", height: '60px' }}>
                          </Avatar>
                          &nbsp;<Typography style={{ float: 'left' }}>{each[0].orders.user.firstName} {each[0].orders.user.lastName}</Typography>
                        </>
                      )


                    }

                    <Typography variant="subtitle1" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i>at&nbsp;{moment(each[0].orders.dateTime).format("DD-MM-YYYY HH:mm:ss")}</i> </Typography>

                  </Typography>

                  <Grid container>
                    <Grid md={6}>
                      <Card style={{padding:'20px'}}>
                    <Typography fontFamily={"Saira"} fontWeight={700}>ADDRESS:</Typography> 
                    <Typography fontFamily={"Saira"} fontWeight={500}>{each[0].orders.user.street}</Typography> 
                    <Typography fontFamily={"Saira"} fontWeight={500}>{each[0].orders.user.city}</Typography> 
                    <Typography fontFamily={"Saira"} fontWeight={500}>{each[0].orders.user.state}</Typography> 
                    <Typography fontFamily={"Saira"} fontWeight={500}>{each[0].orders.user.country}</Typography> 
                    </Card>
                    </Grid>

                    <Grid md={6}>
                      {
                        each.map((eitem, ind) => (
                          <>

                            <Card key={ind} style={{ padding: '20px', margin: '10px' }}>
                              <Grid container>
                                <Grid md={4}>
                                  <div className="product-image">
                                    <img src={"data:image/png;base64," + eitem.item.product.imageData} style={{ width: '120px', height: '120px', verticalAlign: 'middle', borderRadius: 4 }} />
                                    <Typography fontFamily={"Saira"} fontWeight={700}>{eitem.item.product.productName}</Typography>
                                  </div>
                                </Grid>

                                <Grid md={8}>
                                  <table style={{ borderStyle: 'groove', marginTop: '0px', width: '100%' }}>
                                    <tbody>
                                      <tr>
                                        <td>
                                          <Typography>QUANTITY</Typography>
                                        </td>
                                        <td>
                                          <Typography>{eitem.item.quantity}</Typography>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
                                          <Typography>PRICE:</Typography>
                                        </td>
                                        <td>
                                          <Typography>RM {eitem.item.totalPrice}</Typography>
                                        </td>
                                      </tr>

                                      {/* <tr>
                                <td>
                                <Typography>PAYMENT STATUS:</Typography>
                                </td>
                                <td>
                                <Typography> {eitem.isPaymentDone}</Typography>
                                </td>
                              </tr> */}
                                    </tbody>
                                  </table>
                                </Grid>
                              </Grid>
                            </Card>  <br></br>
                          </>
                        ))
                      }

                    </Grid>


                  </Grid>





                </div>
              ) : ""
            }
          </Card><br></br>
          </>
        )
        )
      ) : <CircularProgress />
      }
      <br></br>
    </div>
  )
}
export default SellerPayment
