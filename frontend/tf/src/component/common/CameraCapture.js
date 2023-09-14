import React, { useEffect, useRef, useState } from 'react';

function CameraCapture({ onCapture }) {
  const videoRef = useRef(null);
  const [capturedImage, setCapturedImage] = useState(null);
  const [showCaptureButton, setShowCaptureButton] = useState(true);

  useEffect(() => {
    const startCamera = async () => {
      try {
        const stream = await navigator.mediaDevices.getUserMedia({ video: true });
        videoRef.current.srcObject = stream;
        videoRef.current.play();
      } catch (error) {
        console.error('Error accessing the camera', error);
      }
    };

    startCamera();
  }, []);

  const handleCapture = async () => {
    const video = videoRef.current;

    if (video) {
      const canvas = document.createElement('canvas');
      canvas.width = video.videoWidth;
      canvas.height = video.videoHeight;
      const context = canvas.getContext('2d');
      context.drawImage(video, 0, 0, canvas.width, canvas.height);

      const dataURL = canvas.toDataURL('image/png');
      console.log('Captured Data URL (first 100 chars):', dataURL.substring(0, 100));

      // 캡처된 이미지를 부모 컴포넌트로 전달
      onCapture(dataURL);

      // 캡처된 이미지를 상태에 저장
      setCapturedImage(dataURL);
      setShowCaptureButton(false); // 촬영 버튼 숨김

      // 5초 후에 다시 촬영 버튼을 표시하고 찍은 사진을 초기화
      setTimeout(() => {
        setShowCaptureButton(true);
        setCapturedImage(null);
      }, 5000);
    }
  };

  return (
    <div className="video-container">
      <video id="video" ref={videoRef}></video>
      {capturedImage ? (
        <>
          <img src={capturedImage} alt="Captured" />
          <p>사진 확인 중...</p>
        </>
      ) : (
        showCaptureButton && (
          <button className="Capturebtn" onClick={handleCapture}>
            사진 촬영
          </button>
        )
      )}
    </div>
  );
}

export default CameraCapture;
