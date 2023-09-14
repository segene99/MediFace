import Header from '../../component/common/Header';
import React, { useEffect, useState } from 'react'
import axios from  'axios';
import '../../../node_modules/bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.min';
import '/node_modules/bootstrap/dist/js/bootstrap.min.js'
const RegisteredPage = () => {

  const [patient, setPatient] = useState([]);

  useEffect(()=>{
    loadPatient();
  }, []);

  const loadPatient = async()=>{
    const result = await axios.get(
      'http://localhost:8081/registered',{
        validateStatus: () =>{
          return true;
        }
      });
      if(result.status === 302){
        setPatient(result.data)
      }
  } 

  return (
    <div>
      <Header />
      <div className='container text-center'>
      <p className='mb-5' style={{color:'#00567B',fontSize:30+'px'}}>등록완료</p>
      {patient.map((patient)=>(
        <div>
        {/* <img src='' className='captureImg' alt/> */}
        <div className='row'>
        <div className='col-3'>이름 : </div>
        <div className='col form-control'>{patient.name}</div>
        </div>
        <div className='row'>
        <div className='col-3'>전화번호 : </div>
        <div className='col form-control'>{patient.phoneNum}</div>
        </div>
        <div className='row'>
        <div className='col-3'>진료순번 : </div>
        <div className='col form-control'>{patient.id}</div>
        </div>
        </div>
            ))}
      </div>
    </div>
  );
}
export default RegisteredPage
