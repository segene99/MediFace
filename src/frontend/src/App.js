import { BrowserRouter, Routes, Route } from 'react-router-dom';
// import Layout from './layout/Layout';
// import MainPage from './page/main/MainPage';
import TakePiceturePage from './page/main/TakePicturePage';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* <Route path="/" element={<Layout />}> */}
        <Route path="/" element={<TakePiceturePage />}>
          {/* <Route index element={<MainPage />} /> */}
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
export default App;
