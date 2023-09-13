import React from 'react';
import Layout from '../../layout/Layout';
import CameraCapture from '../../component/common/CameraCapture'; // CameraCapture 컴포넌트를 가져옵니다.

function TakePicturePage() {
  return (
    <div>
      <Layout>
        <CameraCapture>
          <p>얼굴이 카메라에 다 들어올 수 있도록 다가와주세요</p>
        </CameraCapture>
      </Layout>
    </div>
  );
}

export default TakePicturePage;
