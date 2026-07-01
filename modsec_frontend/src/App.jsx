import Navigation from './Navigation';

import { BrowserRouter, Routes, Route} from 'react-router-dom';
import ReportsPage from "./reports/ReportsPage.jsx";
import DevicesPage from "./devices/DevicesPage.jsx";
import ArmingButton from "./ArmingButton.jsx";
import DashboardPage from "./dashboard/DashboardPage.jsx";

function App() {
  return (
      <BrowserRouter>
          <Navigation />
          <ArmingButton />
          <Routes>
              <Route path="/" element={<DashboardPage />} />
              <Route path= {"/reports"} element={<ReportsPage />} />
              <Route path={"/devices"} element={<DevicesPage />} />
          </Routes>
      </BrowserRouter>
  );
}

export default App;