
import {useNavigate} from 'react-router-dom'
import Header from '../../component/common/Header';
import '../../../node_modules/bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.min';
import '/node_modules/bootstrap/dist/js/bootstrap.min.js'
function MainPage() {
  const navigate = useNavigate();
 
  const navigateToAdminList = () => {
    navigate("/list");
  };

  return (
    <div style={{backgroundColor: '#37A8D9', height:1000+'px'}}>
      <Header />
      <div className='text-center mb-5'>
        <h2 className='my-5 py-5' style={{color: '#fff'}}>관리자 페이지</h2>
      <button className='addcare'  onClick={navigateToAdminList}>진료목록</button>
      </div>
    </div>
  );
}
export default MainPage;
