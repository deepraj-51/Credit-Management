import React from 'react'
import { Routes, Route, Link } from 'react-router-dom'
import CustomerList from './components/CustomerList'
import CustomerDetails from './components/CustomerDetails'
import AddCredit from './components/AddCredit'
import ReceivePayment from './components/ReceivePayment'
import TransactionHistory from './components/TransactionHistory'
import OverdueView from './components/OverdueView'
import AddCustomer from './components/AddCustomer'
import Reminders from './components/Reminders'
import Login from './components/Login'

export default function App(){
  return (
    <div className="container">
      <div className="header">
        <h1>Credit Management</h1>
        <div style={{display:'flex', gap:8}}>
          <Link className="button" to="/">Customers</Link>
          <Link className="button" to="/overdue">Overdue</Link>
          <Link className="button" to="/reminders">Reminders</Link>
        </div>
      </div>
      <Routes>
        <Route path="/login" element={<Login/>} />
        <Route path="/" element={<CustomerList/>} />
        <Route path="/customers/:id" element={<CustomerDetails/>} />
        <Route path="/customers/:id/add" element={<AddCredit/>} />
        <Route path="/customers/:id/payment" element={<ReceivePayment/>} />
        <Route path="/customers/:id/history" element={<TransactionHistory/>} />
        <Route path="/overdue" element={<OverdueView/>} />
        <Route path="/add-customer" element={<AddCustomer/>} />
        <Route path="/reminders" element={<Reminders/>} />
      </Routes>
    </div>
  )
}
