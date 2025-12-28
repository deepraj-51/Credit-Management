import React, {useEffect, useState} from 'react'
import { useParams } from 'react-router-dom'
import client from '../api/client'

export default function TransactionHistory(){
  const { id } = useParams()
  const [items, setItems] = useState<any[]>([])

  useEffect(()=>{
    if(!id) return
    Promise.all([
      client.get(`/customers/${id}/credits`).catch(()=>({data:[]})),
      client.get(`/customers/${id}/payments`).catch(()=>({data:[]})),
    ]).then(([cRes, pRes])=>{
      const credits = cRes.data.map((c:any)=> ({ date: c.transactionDate || c.creditDate || '', type:'Udhar', amount: c.amount, note: c.description }))
      const payments = pRes.data.map((p:any)=> ({ date: p.paymentDate || '', type:'Payment', amount: p.amount, note: p.paymentMode }))
      const combined = [...credits, ...payments].sort((a,b)=> new Date(a.date).getTime() - new Date(b.date).getTime())
      setItems(combined)
    }).catch(console.error)
  },[id])

  return (
    <div>
      <h2>Transaction History</h2>
      <div className="card">
        <div style={{display:'flex', flexDirection:'column', gap:8}}>
          {items.map((it, idx)=>(
            <div key={idx} style={{display:'flex', justifyContent:'space-between'}}>
              <div style={{fontSize:13}}>{new Date(it.date).toLocaleDateString()} - {it.type}</div>
              <div style={{fontWeight:700}}>{it.type==='Udhar'? '₹'+it.amount : '₹'+it.amount}</div>
            </div>
          ))}
        </div>
      </div>
    </div>
  )
}
