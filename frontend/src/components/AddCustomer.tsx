import React, {useState} from 'react'
import { useNavigate } from 'react-router-dom'
import client from '../api/client'

export default function AddCustomer(){
  const navigate = useNavigate()
  const [name, setName] = useState('')
  const [phone, setPhone] = useState('')
  const [limit, setLimit] = useState('0')

  const handleSave = async ()=>{
    if(!phone){ alert('Phone number required'); return }
    try{
      const res = await client.post('/customers', { name, phoneNumber: phone, creditLimit: Number(limit) })
      alert('Customer added')
      navigate(`/customers/${res.data.customerId}`)
    }catch(e){ console.error(e); alert('Failed') }
  }

  return (
    <div>
      <h2>Add Customer</h2>
      <div className="card">
        <div className="form-row">
          <input className="input" placeholder="Name" value={name} onChange={e=>setName(e.target.value)} />
        </div>
        <div className="form-row">
          <input className="input" placeholder="Phone number" value={phone} onChange={e=>setPhone(e.target.value)} />
        </div>
        <div className="form-row">
          <input className="input" placeholder="Credit limit" value={limit} onChange={e=>setLimit(e.target.value)} inputMode="numeric" />
        </div>
        <div style={{marginTop:8}}>
          <button className="button" onClick={handleSave}>Save</button>
        </div>
      </div>
    </div>
  )
}
