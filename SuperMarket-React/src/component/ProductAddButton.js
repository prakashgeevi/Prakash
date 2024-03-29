import React from 'react'


import '../CSS/CustomButton.css';

 const ProductAddButton = ({ label, onClick }) => {
  return (
    <div>
        <button className="custom-button" onClick={onClick}>
             {label}
         </button>
    </div>
  )
}

export default ProductAddButton
