import React from 'react';

function CaptureButton({ onClick }) {
  return (
    <button className="Capturebtn" onClick={onClick}>
      사진 촬영
    </button>
  );
}

export default CaptureButton;
