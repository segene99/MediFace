import {useNavigate} from 'react-router-dom'
import Header from '../../component/common/Header';
import '../../../node_modules/bootstrap/dist/css/bootstrap.min.css';
import '../../../node_modules/bootstrap/dist/js/bootstrap.min.js';
import '/node_modules/bootstrap/dist/js/bootstrap.min.js'
function MainPage() {

  const navigate = useNavigate();
 
  const navigateToCapture = () => {
    navigate("/capture");
  };

  return (
    <div>
      <Header />
      <div className='container text-center'>
      <p className='guide text-center my-5 py-5'>아래버튼으로<br/>진료 등록을 진행하세요</p>
      <button className='btn my-5' style={{backgroundColor :'#a3e0fa', fontWeight: 700}} onClick={navigateToCapture}>진료등록</button>
      </div>
    </div>
  );
}
export default MainPage;
