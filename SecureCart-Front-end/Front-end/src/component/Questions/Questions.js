import React from 'react'
import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { url } from "../../ServiceApi/ServiceApi";
import axios from 'axios';
import moment from 'moment/moment';
import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import { Box, Divider, TextField, FormControl, Button, Select, MenuItem, InputLabel, Card } from '@mui/material';
import { styled } from '@mui/material/styles';
import ArrowForwardIosSharpIcon from '@mui/icons-material/ArrowForwardIosSharp';
import MuiAccordion from '@mui/material/Accordion';
import MuiAccordionSummary from '@mui/material/AccordionSummary';
import MuiAccordionDetails from '@mui/material/AccordionDetails';
import Typography from '@mui/material/Typography';
import Navbar from '../Navbar/Navbar';

const Accordion = styled((props) => (
  <MuiAccordion disableGutters elevation={0} square {...props} />
))(({ theme }) => ({
  border: `1px solid ${theme.palette.divider}`,
  '&:not(:last-child)': {
    borderBottom: 0,
  },
  '&:before': {
    display: 'none',
  },
}));

const AccordionSummary = styled((props) => (
  <MuiAccordionSummary
    expandIcon={<ArrowForwardIosSharpIcon sx={{ fontSize: '0.9rem' }} />}
    {...props}
  />
))(({ theme }) => ({
  backgroundColor:
    theme.palette.mode === 'dark'
      ? 'rgba(255, 255, 255, .05)'
      : 'rgba(0, 0, 0, .03)',
  flexDirection: 'row-reverse',
  '& .MuiAccordionSummary-expandIconWrapper.Mui-expanded': {
    transform: 'rotate(90deg)',
  },
  '& .MuiAccordionSummary-content': {
    marginLeft: theme.spacing(1),
  },
}));

const AccordionDetails = styled(MuiAccordionDetails)(({ theme }) => ({
  padding: theme.spacing(2),
  borderTop: '1px solid rgba(0, 0, 0, .125)',
}));


export default function Questions() {

  const navigate = useNavigate();

  const [open, setOpen] = React.useState(false);
  const [message, setmessage] = useState('');
  const [expanded, setExpanded] = React.useState('');
  const [questions, setQuestions] = React.useState([]);
  const [sellers, setSellers] = React.useState([]);
  const [questionValue, setQuestionValue] = React.useState([]);
  const [selectedOption, setSelectedOption] = useState("");
  const [replyMessage, setReplyMessage] = useState("");
  const handleChange = (panel) => (event, newExpanded) => {
    setExpanded(newExpanded ? panel : false);
  };
  const api = url + 'questions/' + localStorage.getItem('userId');
  const saveQuestion = url + 'questions';
  const saveReply = url + 'replies';
  const sellersUrl = url + 'user/sellers';
  useEffect(() => {
    getAllQuestions();
    getAllSellers();
  }, []);

  function getAllQuestions() {
    axios.get(api, { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
      .then(res => {
        setQuestions(res.data);
      })
      .catch(e => {
      })
  }

  function getAllSellers() {
    axios.get(sellersUrl, { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
      .then(res => {
        setSellers(res.data);
      })
      .catch(e => {
      })
  }

  const submitQuestion = () => {

    const questionDetails = {
      "buyerId": localStorage.getItem("userId"),
      "sellerId": selectedOption,
      "question": questionValue
    };

    axios.post(saveQuestion, questionDetails
      , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
      .then(res => {

        console.log(res.data);
        getAllQuestions();
      })
      .catch(e => {

        console.log(e.response.data.message);
        setmessage(e.response.data.message);
      })
  }

  const submitReply = (questId) => {

    const questionDetails = {
      "buyerId": localStorage.getItem("userId"),
      "questionsId": questId,
      "replyMessage": replyMessage
    };

    axios.post(saveReply, questionDetails
      , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
      .then(res => {
        setReplyMessage("");
        console.log(res.data);
        getAllQuestions();
      })
      .catch(e => {

        console.log(e.response.data.message);
        setmessage(e.response.data.message);
      })
  }


  const handleQuestionValue = (e) => {
    setQuestionValue(e.target.value);
  }

  const handleClose = (event, reason) => {

    if (reason === 'clickaway') {

      return;
    }

    setOpen(false);
  };


  function search(arr, value) {
    for (let i = 0; i < arr.length; i++) {
      if (arr[i].userId.role === value) {
        return true;
      }
    }
    return false;
  }





  return (
    <div>
      <Navbar />
      <br></br><br></br><br></br><br></br>
      {
        (localStorage.getItem("roles") === "buyer") ? (
          <>
            <Card style={{ padding: "30px", width: '90%' }}>
              <FormControl variant="outlined" fullWidth sx={{ m: 1 }}>
                <TextField
                  id="outlined-multiline-flexible"
                  label="Ask your questions here:"
                  multiline
                  fullWidth
                  value={questionValue}
                  onChange={handleQuestionValue}
                  rows={4}
                />
              </FormControl>

              <FormControl variant="outlined" fullWidth sx={{ m: 1 }}>
                <InputLabel id="demo-simple-select-standard-label">Select seller</InputLabel>
                <Select
                  labelId="demo-select-small"
                  id="demo-select-small"
                  value={selectedOption}
                  label="Select seller"
                  onChange={(event) => setSelectedOption(event.target.value)}
                >
                  {sellers.map((item) => (
                    <MenuItem value={item.userId}   >
                      {item.firstName} {item.lastName}</MenuItem>
                  ))}
                </Select>
              </FormControl>
              <Button variant='outlined' onClick={() => submitQuestion()}>Submit</Button>
            </Card>
            <br></br>
            <Divider spacing={1} style={{width:'90%'}}>Your old questions</Divider>
          </>
        ) : ""
      }

      <br></br>

      {
        questions.length > 0 ? (questions.map((each, index) => (
          <Card style={{ width: '90%', background: '#FFEEE3' }}>
            <Accordion expanded={expanded === index} onChange={handleChange(index)}>
              <AccordionSummary aria-controls="panel1d-content" id="panel1d-header">
                <div style={{display: 'flex'}} className='d-flex justify-content-between'>
              <Typography style={{ float: 'left' }} fontWeight={700}>{each.buyerId.firstName} {each.buyerId.lastName}: </Typography>
                <Typography>  &nbsp;&nbsp;&nbsp; {each.question} </Typography>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <Typography variant="subtitle1" ><i>at&nbsp;{moment(each.datetime).format("DD-MM-YYYY HH:mm:ss")}</i> </Typography>
                </div>
              </AccordionSummary>
              <AccordionDetails>
                {
                  localStorage.getItem("roles") === "buyer" ? (
                    each.replies.length > 0 ? (each.replies.map((reply, ind) => (
                      <>
                        <Typography> {reply.userId.firstName} {reply.userId.lastName} ({reply.userId.role}) reply:</Typography>
                        <Typography variant="subtitle1"><i> at&nbsp;{moment(reply.datetime).format("DD-MM-YYYY HH:mm:ss")}</i></Typography>
                        <Typography>
                          <FormControl fullWidth>
                            <TextField
                              id="outlined-multiline-flexible"
                              multiline
                              disabled
                              value={reply.replyMessage}
                              rows={4}
                            />
                          </FormControl>
                        </Typography>
                      </>
                    ))) : (
                      <Typography color={"red"}>No replies yet from admin & seller</Typography>
                    )

                  ) : ""

                }

                {
                  localStorage.getItem("roles") === "seller" ? (

                    each.replies.length > 0 ? (each.replies.map((reply, ind) => (

                      <>
                        <Typography> {reply.userId.firstName} {reply.userId.lastName} ({reply.userId.role}) reply:</Typography>
                        <Typography variant="subtitle1"> <i> at&nbsp;{moment(reply.datetime).format("DD-MM-YYYY HH:mm:ss")}</i></Typography>
                        <Typography>
                          <FormControl fullWidth>
                            <TextField
                              id="outlined-multiline-flexible"
                              multiline
                              disabled
                              value={reply.replyMessage}
                              rows={4}
                            />
                          </FormControl>
                        </Typography>
                      </>

                    ))) : ""

                  ) : ""
                }

                {
                  localStorage.getItem("roles") === "seller" ? (

                    (!search(each.replies, "seller")) ? (
                      <>
                       <Typography>
                          <FormControl fullWidth>
                            <TextField
                              id="outlined-multiline-flexible"
                              multiline
                              value={replyMessage}
                              onChange={(e) => setReplyMessage(e.target.value)}
                              rows={4}
                            />
                          </FormControl>
                        </Typography>
                        <Button variant='outlined' onClick={() => submitReply(each.id)}>Submit</Button>
                      </>
                    ) : ""

                  ) : ""
                }


                {
                  localStorage.getItem("roles") === "admin" ? (

                    each.replies.length > 0 ? (each.replies.map((reply, ind) => (

                      <>
                        <Typography> {reply.userId.firstName} {reply.userId.lastName} ({reply.userId.role}) reply:</Typography>
                        <Typography variant="subtitle1"><i> at&nbsp;{moment(reply.datetime).format("DD-MM-YYYY HH:mm:ss")}</i></Typography>
                        <Typography>
                          <FormControl fullWidth>
                            <TextField
                              id="outlined-multiline-flexible"
                              multiline
                              disabled
                              value={reply.replyMessage}
                              rows={4}
                            />
                          </FormControl>
                        </Typography>
                      </>

                    ))) : ""

                  ) : ""
                }

                {
                  localStorage.getItem("roles") === "admin" ? (

                    (!search(each.replies, "admin")) ? (
                      <>
                        <Typography>
                          <FormControl fullWidth>
                            <TextField
                              id="outlined-multiline-flexible"
                              multiline
                              value={replyMessage}
                              onChange={(e) => setReplyMessage(e.target.value)}
                              rows={4}
                            />
                          </FormControl>
                        </Typography>
                        <Button variant='outlined' onClick={() => submitReply(each.id)}>Submit</Button>
                      </>
                    ) : ""

                  ) : ""
                }




              </AccordionDetails>
            </Accordion>
          </Card>
        ))) : (
          <Typography color="red">No questions found</Typography>
        )
      }
<br></br>

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

