import React, {useEffect, useState} from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import client from '../api/client'

export default function ReceivePayment(){
  const { id } = useParams()
  const navigate = useNavigate()
  const [amount, setAmount] = useState('')
  const [mode, setMode] = useState('Cash')
  const [customer, setCustomer] = useState<any>(null)
  const [ledger, setLedger] = useState<any>(null)

  useEffect(()=>{
    if(!id) return
    client.get(`/customers/${id}`)
      .then(r=> setCustomer(r.data))
      .catch(console.error)
    client.get(`/customers/${id}/ledger`)
      .then(r=> setLedger(r.data))
      .catch(console.error)
  },[id])

  const handleSave = async ()=>{
    if(!id) return
    const amt = Number(amount)
    if(!amt || amt<=0) { alert('Enter amount'); return }
    try{
      await client.post(`/customers/${id}/payments`, { amount: amt, mode: mode, receivedBy: 'frontend' })
      alert('Payment recorded')
      navigate(-1)
    }catch(e){ console.error(e); alert('Failed') }
  }

  return (
    <div>
      <h2>Receive Payment</h2>
      {customer && <div className="card"><strong>{customer.name}</strong><div style={{fontSize:13}}>{customer.phoneNumber}</div></div>}
      <div style={{marginTop:12}} className="card">
        <div style={{fontSize:12, color:'#475569'}}>Amount</div>
        <input className="input" value={amount} onChange={e=>setAmount(e.target.value)} inputMode="numeric" />
        <div style={{marginTop:8}}>
          <label style={{marginRight:8}}><input type="radio" checked={mode==='Cash'} onChange={()=>setMode('Cash')} /> Cash</label>
          <label><input type="radio" checked={mode==='UPI'} onChange={()=>setMode('UPI')} /> UPI</label>
        </div>
        <div style={{marginTop:12}}>
          <button className="button" onClick={handleSave}>Save</button>
        </div>
      </div>
      {ledger && <div style={{marginTop:12}}>Remaining balance: ₹{ledger.currentBalance}</div>}
    </div>
  )
}
