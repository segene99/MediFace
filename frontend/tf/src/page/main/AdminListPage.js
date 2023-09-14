import React, { useEffect, useState } from 'react'
import Header from '../../component/common/Header';
import axios from  'axios';

const AdminView = () => {

  const [patients, setPatients] = useState([]);

  useEffect(()=>{
    loadPatients();
  }, []);

  const loadPatients = async()=>{
    const result = await axios.get(
      'http://localhost:8081/list',{
        validateStatus: () =>{
          return true;
        }
      });
      if(result.status === 302){
        setPatients(result.data)
      }
  } 

  return (
    <div>
      <Header />
      <section>
        <table className='table table-striped mt-5'>
          <thead>
            <tr className='text-center'>
              <th>진료순서</th>
              <th >이름</th>
              <th>번호</th>
              <th>진료취소</th>
            </tr>
          </thead>

          <tbody className='text-center' style={{fontSize : 10+'px'}}>
            {patients.map((patient, index)=>(
              <tr key={patient.id}>
                <th scope='row' key={index}>
                  {index +1}
                </th>
                <td>{patient.name}</td>
                <td>{patient.phoneNum}</td>
                <td>
                  <button className='btn btn-danger'>
                    취소
                  </button>
                </td>
              </tr>    
            ))}
            
          </tbody>
        </table>
      </section>
    </div>
  )
}

export default AdminView 