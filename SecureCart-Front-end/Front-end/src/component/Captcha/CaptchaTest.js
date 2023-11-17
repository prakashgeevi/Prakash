import { Padding } from "@mui/icons-material";
import { TextField } from "@mui/material";
import React, { Component } from "react";
import './CaptchaTest.css';
import VerifiedIcon from '@mui/icons-material/Verified';
import {
    loadCaptchaEnginge,
    LoadCanvasTemplate,
    LoadCanvasTemplateNoReload,
    validateCaptcha
} from "react-simple-captcha";
class CaptchaTest extends Component {
    componentDidMount() {
        loadCaptchaEnginge(6);
    }
    doSubmit = () => {
        let user_captcha = document.getElementById("user_captcha_input").value;
        if (validateCaptcha(user_captcha) == true) {
            //loadCaptchaEnginge(6);
            this.props.onCaptchaValidation(true); 
            //document.getElementById("user_captcha_input").value = "";
        } else {
            //alert("Captcha Does Not Match");
            this.setState({ isCaptchaValid: false });
            //document.getElementById("user_captcha_input").value = "";
            this.props.onCaptchaValidation(false); 
        }
    };
    render() {
        return (
            <div>
                <div className="container">
                    <div className="form-group">
                        <div className="col mt-3" style={{border:'1px solid black', padding:'10px', borderRadius: '10px'}}>
                            <LoadCanvasTemplate />
                        <div  >
                            <div style={{display:'flex'}}>
                                <TextField id="user_captcha_input" label="Enter Captcha" variant="outlined" 
                                type="text" className="input-box" onBlur={() => this.doSubmit()} />
                                {/* <button
                                className="buttons"
                                onClick={}
                            >
                               <VerifiedIcon/>
                            </button> */}
                            </div>
                        </div>
                        </div>
                        <div className="col mt-3">
                            <div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
export default CaptchaTest ;
