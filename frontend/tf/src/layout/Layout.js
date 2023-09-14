import Header from '../component/common/Header';
import CameraCapture from '../component/common/CameraCapture';
import '../css/common.css';

function Layout() {
  const handleCapture = (dataURL) => {
    console.log('Captured image data URL:', dataURL);
  };
  return (
    <>
      <div className="content main-width"></div>
      <Header />
      <CameraCapture onCapture={handleCapture} />
    </>
  );
}

export default Layout;
