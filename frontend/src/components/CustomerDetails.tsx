import React, {useEffect, useState} from 'react'
import { useParams } from 'react-router-dom'
import client from '../api/client'

type Customer = {
  customerId: any
  id: number
  name: string
  phoneNumber?: string
  address?: string
  creditLimit?: number
}

export default function CustomerDetails(){
  const { id } = useParams()
  const [customer, setCustomer] = useState<Customer | null>(null)
  const [ledger, setLedger] = useState<any>(null)
  const [amount, setAmount] = useState('')
  const [loading, setLoading] = useState(false)

  useEffect(()=>{
    if(!id) return
    setLoading(true)
    client.get(`/customers/${id}`)
      .then(res => setCustomer(res.data))
      .catch(err => console.error(err))
      .finally(()=> setLoading(false))

    client.get(`/customers/${id}/ledger`)
      .then(res => setLedger(res.data))
      .catch(err => console.error(err))
  },[id])

  const handlePayment = async ()=>{
    if(!id || !amount) return
    try{
      await client.post(`/customers/${id}/payments`, { amount: Number(amount), mode: 'UPI', receivedBy: 'frontend' })
      alert('Payment submitted')
      setAmount('')
    }catch(e){
      console.error(e)
      alert('Failed')
    }
  }

  return (
    <div>
      <h2>Customer</h2>
      {loading && <div>Loading...</div>}
      {customer && (
        <div className="card">
          <div style={{display:'flex', justifyContent:'space-between', alignItems:'center'}}>
            <div>
              <div style={{fontSize:18, fontWeight:700}}>{customer.name}</div>
              <div style={{fontSize:13, color:'#475569'}}>{customer.phoneNumber}</div>
            </div>
            <div style={{textAlign:'right'}}>
              <div style={{fontSize:16}}>Total Due: <span style={{color:'#dc2626', fontWeight:700}}>₹{ledger?.currentBalance || 0}</span></div>
              <div style={{fontSize:13, color:'#475569'}}>Credit Limit: ₹{customer.creditLimit || 0}</div>
            </div>
          </div>

          <div style={{display:'flex', gap:8, marginTop:12}}>
            <a className="button" href={`/customers/${customer.customerId}/add`}>➕ Add Udhar</a>
            <a className="button" href={`/customers/${customer.customerId}/payment`}>💰 Receive Payment</a>
            <a className="button" href={`/customers/${customer.customerId}/history`}>📜 History</a>
            <button className="button" onClick={async ()=>{
              if(!confirm('Block this customer?')) return
              try{ await client.post(`/customers/${customer.customerId}/block`); alert('Customer blocked'); }catch(e){ console.error(e); alert('Failed') }
            }}>🚫 Block Customer</button>
          </div>

          {customer.address && <div style={{marginTop:8}}>{customer.address}</div>}
        </div>
      )}

      <div style={{marginTop:16}}>
        <h3>Ledger</h3>
        {ledger ? (
          <div className="card">
            <div>Total Credit: ₹{ledger.totalCredit}</div>
            <div>Total Payment: ₹{ledger.totalPayment}</div>
            <div>Current Balance: ₹{ledger.currentBalance}</div>
          </div>
        ) : (
          <div>No ledger available</div>
        )}
      </div>
    </div>
  )
}
