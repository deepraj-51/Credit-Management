import React, {useEffect, useState} from 'react'
import client from '../api/client'
import { Link } from 'react-router-dom'

export default function OverdueView(){
  const [aging, setAging] = useState<any[]>([])

  useEffect(()=>{
    client.get('/aging')
      .then(r=> setAging(r.data))
      .catch(err=> console.error(err))
  },[])

  const buckets = [
    { key: 'days0to7', label: '0–7 days' },
    { key: 'days8to15', label: '8–15 days' },
    { key: 'days16to30', label: '16–30 days' },
    { key: 'days31Plus', label: '30+ days' }
  ]

  return (
    <div>
      <h2>Overdue / Risk View</h2>
      <div className="card">
        {buckets.map(b=> (
          <div key={b.key} style={{display:'flex', justifyContent:'space-between', padding:'8px 0'}}>
            <div style={{fontWeight:600}}>{b.label}</div>
            <div>
              ₹{aging.reduce((acc:any,a:any)=> acc + Number(a[b.key] || 0), 0)}
            </div>
          </div>
        ))}
      </div>
      <div style={{marginTop:12}}>
        <Link to="/" className="button">View Customers</Link>
      </div>
    </div>
  )
}
