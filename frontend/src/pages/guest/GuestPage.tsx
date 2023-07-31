import React from "react";
import { Grid, GridItem } from "@chakra-ui/react";
import Header from "../../components/header/Header";
import { Route, Routes } from "react-router-dom";
import Login from "./Login";
import GuestShopPage from "./GuestShopPage";
import Register from "./Register";
import GuestShopDetailPage from "./GuestShopDetailPage";
import GuestForgotPasswordPage from "./GuestForgotPasswordPage";

const GuestPage = () => {
  return (
    <Grid
      templateAreas={{
        base: `"header" "main"`,
      }}
      templateColumns={{
        base: `1fr`,
      }}
      h="100%"
    >
      <GridItem
        area="header"
        position="fixed"
        w="100%"
        backgroundColor="white"
        zIndex={999}
      >
        <Header />
      </GridItem>

      <GridItem area="main">
        <Routes>
          <Route index element={<GuestShopPage />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/shop" element={<GuestShopPage />} />
          <Route path="/about" element={<GuestShopPage />} />
          <Route path="/shop/detail" element={<GuestShopDetailPage />} />
          <Route
            path="/forgot-password"
            element={<GuestForgotPasswordPage />}
          />
        </Routes>
      </GridItem>
    </Grid>
  );
};

export default GuestPage;
