
import '../CSS/Footer.css';
import React from 'react';
//  import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
// import { faFacebook, faTwitter, faInstagram, faGithub, faLinkedin, faYoutube } from '@fortawesome/free-brands-svg-icons';
export default function Footer (){
    return(
 
      <footer>

      <section className="ft-main"  style={{backgroundColor:"#414242"}}>
        
        <div className="ft-main-item">
          <h2 className="ft-title">About</h2>
          <ul>
            <li><a href="#">Product</a></li>
            <li><a href="#">Portfolio</a></li>
            <li><a href="#">Services</a></li>
            <li><a href="#">Customers</a></li>
            
          </ul>
        </div>
        <div className="ft-main-item">
          <h2 className="ft-title">Resources</h2>
          <ul>
            <li><a href="#">Ecommerce</a></li>
         
            <li><a href="#">Product</a></li>
            <li><a href="#">Webinars</a></li>
          </ul>
        </div>
        <div className="ft-main-item">
          <h2 className="ft-title">Contact</h2>
          <ul>
            <li><a href="#">Help</a></li>
            <li><a href="#">Sales</a></li>
            <li><a href="#">Advertise</a></li>
          </ul>
        </div>
        <div className="ft-main-item">
          <h2 className="ft-title">Stay Updated</h2>
          <p>Subscribe to our newsletter to get our latest news.</p>
          <form>
            <input type="email" name="email" placeholder="Enter email address" />
            <input type="submit" value="Sent" />
          </form>
        </div>
      </section>

      <section className="ft-social" style={{backgroundColor:"#414242"}}>
        <ul className="ft-social-list">
          {/* <li><a href="#"><FontAwesomeIcon icon={faFacebook} /></a></li>
          <li><a href="#"><FontAwesomeIcon icon={faTwitter} /></a></li>
          <li><a href="#"><FontAwesomeIcon icon={faInstagram} /></a></li>
          <li><a href="#"><FontAwesomeIcon icon={faGithub} /></a></li>
          <li><a href="#"><FontAwesomeIcon icon={faLinkedin} /></a></li>
          <li><a href="#"><FontAwesomeIcon icon={faYoutube} /></a></li> */}
        </ul>
      </section>

      <section className="ft-legal" style={{backgroundColor:"#414242"}}>
        <ul className="ft-legal-list">
          <li><a href="#">Terms &amp; Conditions</a></li>
          <li><a href="#">Privacy Policy</a></li>
          <li>&copy; 2023 Copyright Nowrap Inc.
          </li>
        </ul>
      </section>
 
    </footer>
    
    )

}