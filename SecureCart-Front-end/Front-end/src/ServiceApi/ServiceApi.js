import axios from "axios";

  export const url =`https://localhost:8080/`;

  export const loginUser = (email, password) => {
    return axios.post(url+'user/signin', {
        userName: email,
        password: password
      })
  }

  
  export const sendOtp = (email) => {
    return axios.post(url+'forget-password?email='+email,{} )
  }

  export const verifyOtp = (email, otp) => {
    return axios.post(url+'forget-password/otp?otp=' + otp + '&email=' + email, {
    })
  }

  export const twoStepVerify = (email, password) => {
    return axios.post(url+'user/twoStepVerification', {
        userName: email,
        password: password
      })
  }

    const config = {
        headers: {
          "Authorization": `Bearer ${localStorage.getItem('Token')}`
        }
      };

 
export const pendingAccount = () => {
    return axios.get(url+'user/allusers', config);
       
  };

  export const StatusApprove = (data) => {
    return axios.put(url+'user/status/'+localStorage.getItem('userId'),data ,config);
  };

 
  
  export const getAllProductApi = (searchValue) => {
    const api = url+'product/search?productName='+searchValue+'&userId='+localStorage.getItem('userId')
    
    return axios.get(api, { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } });

    
  }