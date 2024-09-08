import Dashboard from "./components/Dashboard";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./components/Login";
import SignUp from "./components/SignUp";
import React from "react";

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route exact path="/" element={<Login />} />
        <Route exact path="/welcome" element={<Dashboard page ="welcome" />} />
        <Route exact path="/signup" element={<SignUp />} />
        <Route exact path="/asset/add" element={<Dashboard page="asset/add" />} />
        <Route exact path="/type/add" element={<Dashboard page="type/add" />} />
        <Route exact path="/log/view" element={<Dashboard page="log/view" />} />
        <Route exact path="/asset/delete" element={<Dashboard page="asset/delete" />} />
        <Route exact path="/type/delete" element={<Dashboard page="type/delete" />} />
        <Route exact path="/asset/open/:openAssetId" element={<Dashboard page="asset/open" />} />
        <Route exact path="/asset/edit/:editAssetId" element={<Dashboard page="asset/edit" />} />
        <Route exact path="/type/open/:openTypeId" element={<Dashboard page="type/open" />} />
        <Route exact path="/type/edit/:editTypeId" element={<Dashboard page="type/edit" />} />
        <Route exact path="/asset/find" element={<Dashboard page="asset/find" />} />
        <Route exact path="/type/find" element={<Dashboard page="type/find" />} />
        <Route exact path="/user/find" element={<Dashboard page="user/find" />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
