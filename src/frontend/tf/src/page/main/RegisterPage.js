import {useNavigate} from 'react-router-dom'
import { useForm } from "react-hook-form"
import Header from '../../component/common/Header';
import '../../../node_modules/bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.min';
import '/node_modules/bootstrap/dist/js/bootstrap.min.js'
function RegisterPage() {

  const navigate = useNavigate();
 
  const navigateToRegister = () => {
    navigate("/register");
  };

  const {handleSubmit } = useForm();

  return (
    <div>
      <Header />
      <div className='container text-center'>
      <form onSubmit={handleSubmit(onsubmit)} action='/registered'>
      <img src='' className='captureImg'/>
      <div className='row'>
        <label for='name' className='col-3'>이름 : </label>
        <input id='name' type='text' className='form-control col'
        placeholder='홍길동' required/>
      </div>    
      <div className='row my-3'>
        <label for='phoneNum' className='col-3'>전화번호 : </label>
        <input id='phoneNum' type='text' oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
        maxLength={11}  placeholder='01012341234' className='form-control col' required/>
      </div>    
      <div className='buttonList d-flex justify-content-around'>
      <button type='submit' className='btn my-5' style={{backgroundColor :'#a3e0fa', fontWeight: 700}} >진료등록</button>
      <button className='btn btn-secondary my-5' style={{fontWeight: 700}} onClick={navigateToRegister}>취소</button>
      </div>
      </form>
      </div>
    </div>
  );
}
export default RegisterPage;
