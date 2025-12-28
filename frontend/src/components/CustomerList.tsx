import React, {useEffect, useState} from 'react'
import client from '../api/client'
import { Link } from 'react-router-dom'

type LedgerItem = {
  customer: {
    customerId: number
    name: string
    phoneNumber?: string
  }
  currentBalance?: number
}

export default function CustomerList(){
  const [customers, setCustomers] = useState<LedgerItem[]>([])
  const [loading, setLoading] = useState(false)

  useEffect(()=>{
    setLoading(true)
    client.get('/customers')
      .then(res => setCustomers(res.data))
      .catch(err => console.error(err))
      .finally(()=> setLoading(false))
  },[])

  return (
    <div>
      <h2>Customers</h2>
      {loading && <div>Loading...</div>}
      <div className="list">
        {customers
          .sort((a,b)=> (Number(b.currentBalance||0) - Number(a.currentBalance||0)))
          .map(item => (
          <div key={item.customer.customerId} className="card">
            <div style={{display:'flex', justifyContent:'space-between', alignItems:'center'}}>
              <div>
                <strong>{item.customer.name}</strong>
                <div style={{fontSize:12, color:'#475569'}}>{item.customer.phoneNumber}</div>
              </div>
              <div style={{textAlign:'right'}}>
                <div style={{fontWeight:700, color: (Number(item.currentBalance||0) > 0 ? '#dc2626' : '#16a34a')}}>
                  ₹{Number(item.currentBalance||0)}
                </div>
                <div style={{marginTop:6}}>
                  <Link className="button" to={`/customers/${item.customer.customerId}`}>Open</Link>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>

      <div style={{position:'fixed', right:24, bottom:24}}>
        <Link to="/add-customer" className="button">➕ Add Customer</Link>
      </div>
    </div>
  )
}
