import React from 'react'

function StockCount(props) {

    const value = props.value;

  if (value == 0) {
    return <p style={{color:'red'}}><b>Currently Unavaliable</b></p>;
  } else if (value < 10) {
    return <p style={{color:'#c49c16'}}><b>Just {value} Quantity left</b></p>;
  } else {
    return <p style={{color:'green'}}><b>In Stock</b></p>;
  }

  return (
    <div>
      
    </div>
  )
}

export default StockCount
