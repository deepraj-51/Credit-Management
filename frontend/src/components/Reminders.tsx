import React, {useState} from 'react'

export default function Reminders(){
  const [auto, setAuto] = useState(false)

  return (
    <div>
      <h2>Reminders</h2>
      <div className="card">
        <div style={{marginBottom:12}}>
          <label><input type="checkbox" checked={auto} onChange={e=>setAuto(e.target.checked)} /> Auto-send weekly reminder</label>
        </div>
        <div>
          <button className="button" onClick={()=>alert('WhatsApp reminder (demo) sent')}>Send WhatsApp reminder</button>
        </div>
      </div>
    </div>
  )
}
