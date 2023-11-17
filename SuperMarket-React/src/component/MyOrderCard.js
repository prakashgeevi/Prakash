import React, { useState } from 'react'


import '../CSS/order.css'


import Accordion from '@mui/material/Accordion';
import AccordionDetails from '@mui/material/AccordionDetails';
import AccordionSummary from '@mui/material/AccordionSummary';
import Typography from '@mui/material/Typography';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';


import { styled } from '@mui/material/styles';
import Paper from '@mui/material/Paper';

import { Container, Row, Col, Button } from 'react-bootstrap';
import { IconButton } from '@mui/material';







 
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import Avatar from '@mui/material/Avatar';  
import { red } from '@mui/material/colors';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ShareIcon from '@mui/icons-material/Share'; 
import MoreVertIcon from '@mui/icons-material/MoreVert';










// const Item = styled(Paper)(({ theme }) => ({
//   backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
//   ...theme.typography.body2,
//   padding: theme.spacing(1),
//   textAlign: 'center',
//   color: theme.palette.text.secondary,
// }));


// const ExpandMore = styled((props) => {
//   const { expand, ...other } = props;
//   return <IconButton {...other} />;
// })(({ theme, expand }) => ({
//   transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
//   marginLeft: 'auto',
//   transition: theme.transitions.create('transform', {
//     duration: theme.transitions.duration.shortest,
//   }),
// }));

const MyOrderCard = ({ onVariableChange,  item, dateTime, totalAmount }) => {

  const Img = styled('img')({
    margin: 'auto',
    display: 'block',
    maxWidth: '100%',
    maxHeight: '100%',
  });



  // const handleExpandClick = () => {
  //   setExpanded(!expanded);
  // };
     

  console.log(item);
  console.log(dateTime);
  console.log(totalAmount);



  const dateString = dateTime;
  const date = new Date(dateString);

  const options = {
    timeZone: 'Asia/Kolkata',
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: 'numeric',
    minute: 'numeric',
    second: 'numeric',
    hour12: true,

  };
// format the date using the options and Indian English locale
  const formattedDate = date.toLocaleString('en-US', options); 

          onVariableChange(formattedDate);
  
  // CARD EXPAND COLLAPSE

  const [expanded, setExpanded] = React.useState(false);

  const handleChange = (panel) => (event, isExpanded) => {
    setExpanded(isExpanded ? panel : false);
  };


  // mui card exapnd collapse for main card

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

            const [expanded1, setExpanded1] = React.useState(false);

            const handleExpandClick = () => {
              setExpanded(!expanded);
            };

  // *****************End ***************






  return (
    <div>

{/* 
  <Card sx={{ maxWidth: 500 }}>
      <CardHeader
        avatar={
          <Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">
            R
          </Avatar>
        }
        action={
          <IconButton aria-label="settings">
            <MoreVertIcon />
          </IconButton>
        }
        title="Shrimp and Chorizo Paella"
        subheader="September 14, 2016"
      />
      <CardMedia
        component="img"
        height="194"
        image="/static/images/cards/paella.jpg"
        alt="Paella dish"
      />
      <CardContent>
        <Typography variant="body2" color="text.secondary">
          This impressive paella is a perfect party dish and a fun meal to cook
          together with your guests. Add 1 cup of frozen peas along with the mussels,
          if you like.
        </Typography>
      </CardContent>
      <CardActions disableSpacing>
        <IconButton aria-label="add to favorites">
          <FavoriteIcon />
        </IconButton>
        <IconButton aria-label="share">
          <ShareIcon />
        </IconButton>
        <ExpandMore
          expand={expanded}
          onClick={handleExpandClick}
          aria-expanded={expanded}
          aria-label="show more"
        >
          <ExpandMoreIcon />
        </ExpandMore>
      </CardActions>
      <Collapse in={expanded} timeout="auto" unmountOnExit>
        <CardContent>
          

          


        </CardContent>
      </Collapse>
    </Card>
 */}















      <Accordion expanded={expanded === 'panel1'} onChange={handleChange('panel1')} style={{ marginBottom: '20px', maxWidth: '80%', marginLeft: '8%' }}>
        <AccordionSummary
          expandIcon={<ExpandMoreIcon />}
          aria-controls="panel1bh-content"
          id="panel1bh-header"
          style={{ position: 'relative', backgroundColor: '#f3fa98', color:'#0a0a0a' }}
        >
          <Col md>
            <Typography sx={{ width: '33%', flexShrink: 0 }} >
              <h6><b>Date</b></h6>
            </Typography>
            <p className="text-pebble mb-0 w-100 mb-2 mb-md-0"><b>{formattedDate}</b></p>
          </Col>
          <Col md={2}>
            <Typography style={{paddingLeft:10}} sx={{ width: '33%', flexShrink: 0 }}>
              <h6><b>Total</b></h6>
            </Typography>
            <p style={{paddingLeft:10}} className="text-pebble mb-0 w-100 mb-2 mb-md-0">&#x20B9;&nbsp;<b>{totalAmount}</b></p>
          </Col>
          <Col md={4}>
            <Typography style={{paddingLeft:10}} sx={{ width: '33%', flexShrink: 0 }}>
              <h6><b>Total Items</b></h6>
            </Typography>
            <p style={{paddingLeft:10}} className="text-pebble mb-0 w-100 mb-2 mb-md-0">    <b>{item.length}</b></p>
          </Col>
          <Col md>
            <Typography sx={{ width: '33%', flexShrink: 0 }}>
              <h6><b>Shipped To</b></h6>
            </Typography>
            <p className="text-pebble mb-0 w-100 mb-2 mb-md-0"><b>{localStorage.getItem('username')}</b></p>
          </Col>
        </AccordionSummary>
        <AccordionDetails>
          <Typography>
            {item.map(item => (
              <div className="product">
             <Row  className="no-gutters mt-3">
                <Col xs={3} md={3} >
                  <img style={{ maxHeight: 100, maxWidth: 100 }} alt="complex" src={'data:image/png;base64,' + item.product.imageData} />

                </Col>
                <Col xs={9} md={9} className="pr-0 pr-md-3">
                  <h6 className="text-charcoal mb-2 mb-md-1">
                    <p style={{ color: '#507694' }} className="text-charcoal"><b>{item.product.productName}</b></p>

                  </h6>
                  <ul className="list-unstyled text-pebble mb-2 small">
                    <li>
                      <b>Quantity: </b> {item.quantity}
                    </li>
                    
                  </ul>
                  <h6 className="text-charcoal text-left mb-0 mb-md-2"><b>Price:</b>&nbsp;&#x20B9;<b style={{color: 'text.secondary'}}> {item.totalPrice}</b></h6>
                </Col>
              </Row>
               </div>
            ))}
          </Typography>
        </AccordionDetails>
      </Accordion>
    </div>
  )
}
export default MyOrderCard
