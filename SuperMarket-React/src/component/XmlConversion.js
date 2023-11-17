// import React from 'react'

// import exportFromJSON from "export-from-json";
// import PropTypes      from "prop-types";
// import { Button } from '@mui/material';
// import axios from "axios";
 
// import { useState } from "react";
// function XmlConversion() {
    
    
    
//     const fields = {
//         Name: "Name column header",
//         Author: "Author column header",
//         Subject: "Subject column header",
//         "Publication Date": "Publication Date column header",
//         "additional data": "additional data column header"
//       };
    
    
    
    
    
    
//     const[data, setResponseData] = useState();
//     axios.get('http://localhost:8080/api/property/file-xml/1', {
//       headers: {
//         Authorization: `Bearer ${localStorage.getItem('Token')}`,
//         'Content-Type': 'application/json; charset=utf-8'
//       }
//     })
//       .then((res) => {
//         console.log(res.data);
//         setResponseData(res.data);
             
//       })
//       .catch((error) => {
//           console.log(error.message);

         
//       });




//     function onClickxml() {
//         const data = props.data;
//         const fileName = props.fileName ? props.fileName : "exported";
//         let fields = props.fields ? props.fields : [];  //empty list means "use all"
//         const exportType = 'xml';
//         exportFromJSON({data, fileName, fields, exportType})
//       }

//       function onClickjson() {
//         const data = props.data;
//         const fileName = props.fileName ? props.fileName : "exported";
//         let fields = props.fields ? props.fields : [];  
//         const exportType = 'json';
//         exportFromJSON({data, fileName, fields, exportType})
//       }
    
//       return (
//         <div>
//              <Button onClick={onClickxml}>
//           download
//         </Button>
//         <Button onClick={onClickjson}>
//             download-json
//             </Button>
//         </div>
       


//       )
    
//     }
    
//     XMLExport.propTypes = {
//       data: PropTypes.arrayOf(PropTypes.object).isRequired,
//       tooltip: PropTypes.string,
//       fileName: PropTypes.string,
//       fields: PropTypes.oneOfType([
//         PropTypes.arrayOf(PropTypes.object),
//         PropTypes.object
//       ]),
//     }


// export default XmlConversion
