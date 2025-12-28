import React, {useState} from 'react'
import { useNavigate } from 'react-router-dom'
import client from '../api/client'

export default function Login(){
  const [phone, setPhone] = useState('')
  const [otp, setOtp] = useState('')
  const navigate = useNavigate()

  const handleSend = ()=>{
    alert('OTP sent (demo)')
  }
  const handleLogin = async ()=>{
    if(!phone || !otp){ alert('Enter phone and OTP'); return }
    try{
      await client.post('/login', { phone, otp })
      navigate('/')
    }catch(e){
      console.error(e)
      alert('Login failed')
    }
  }

  return (
    <div>
      <h2>Login</h2>
      <div className="card">
        <div className="form-row">
          <input className="input" placeholder="Phone number" value={phone} onChange={e=>setPhone(e.target.value)} />
          <button className="button" onClick={handleSend}>Send OTP</button>
        </div>
        <div className="form-row">
          <input className="input" placeholder="OTP" value={otp} onChange={e=>setOtp(e.target.value)} />
        </div>
        <div>
          <button className="button" onClick={handleLogin}>Login</button>
        </div>
      </div>
    </div>
  )
}
