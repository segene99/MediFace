import { BrowserRouter, Routes, Route } from 'react-router-dom';
// import Layout from './layout/Layout';
// import MainPage from './page/main/MainPage';
import TakePiceturePage from './page/main/TakePicturePage';
import AdminListPage from './page/main/AdminListPage';
import MainPage from './page/main/MainPage';
import RegisterPage from './page/main/RegisterPage';
import RegisteredPage from './page/main/RegisteredPage';
import AdminMainPage from './page/main/AdminMainPage';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* <Route path="/" element={<Layout />}> */}
        <Route index element={<MainPage />} />
        <Route path="/capture" element={<TakePiceturePage />}>
        </Route>
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/registered" element={<RegisteredPage />} />
        <Route path="/list" element={<AdminListPage />} />
        <Route path="/admin" element={<AdminMainPage />} />
      </Routes>
    </BrowserRouter>
  );
}
export default App;
