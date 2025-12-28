import React, {useEffect, useState} from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import client from '../api/client'

export default function AddCredit(){
  const { id } = useParams()
  const navigate = useNavigate()
  const [amount, setAmount] = useState('')
  const [desc, setDesc] = useState('')
  const [customer, setCustomer] = useState<any>(null)
  const [ledger, setLedger] = useState<any>(null)
  const [error, setError] = useState<string | null>(null)

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
    setError(null)
    const amt = Number(amount)
    if(!amt || amt<=0){ setError('Enter valid amount'); return }
    // check credit limit
    const creditLimit = Number(customer?.creditLimit || 0)
    const currentBalance = Number(ledger?.currentBalance || 0)
    const projected = currentBalance + amt
    if(creditLimit && projected > creditLimit){
      setError(`Credit limit exceeded\nAllowed: ₹${creditLimit} Current: ₹${currentBalance}`)
      return
    }
    try{
      await client.post(`/customers/${id}/credits`, { amount: amt, billReference: '', description: desc })
      alert('Udhar recorded')
      navigate(-1)
    }catch(e){ console.error(e); setError('Failed to save') }
  }

  return (
    <div>
      <h2>Add Udhar</h2>
      {customer && <div className="card"><strong>{customer.name}</strong><div style={{fontSize:13}}>{customer.phoneNumber}</div></div>}
      <div style={{marginTop:12}} className="card">
        <div style={{fontSize:12, color:'#475569'}}>Amount</div>
        <input className="input" value={amount} onChange={e=>setAmount(e.target.value)} inputMode="numeric" />
        <div style={{fontSize:12, color:'#475569', marginTop:8}}>Description (optional)</div>
        <input className="input" value={desc} onChange={e=>setDesc(e.target.value)} />
        {error && <div style={{color:'#b91c1c', marginTop:8, whiteSpace:'pre-wrap'}}>{error}</div>}
        <div style={{marginTop:12}}>
          <button className="button" onClick={handleSave}>Save</button>
        </div>
      </div>
    </div>
  )
}
