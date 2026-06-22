import {StrictMode, useState} from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'


// TODO
// Clean up the CSS code for the dashboard page, giving the different brief descriptive names instead of left and right
//
// Look into how to make HTTP request in react and begin fetching reports from the server

createRoot(document.getElementById('root')).render(
    <App/>
)
