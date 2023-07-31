import React, { useContext, useState } from "react";
import { ShopProductDTO } from "../../services/shop-product-service";
import { AiFillStar } from "react-icons/ai";
import {
  Badge,
  Box,
  Card,
  CardBody,
  HStack,
  Heading,
  Image,
} from "@chakra-ui/react";
import { BsFillCartPlusFill } from "react-icons/bs";
import { Link, useNavigate } from "react-router-dom";
import utilService from "../../services/util-service";
import { GLOBAL_CONTEXT } from "../../App";
import cartActionSerivce from "../../services/cart-action-service";
import Swal from "sweetalert2";
interface Props {
  product: ShopProductDTO;
}
const ShopProductListItem = ({ product }: Props) => {
  const productContext = useContext(GLOBAL_CONTEXT).productContext;
  const [reload, setReload] = useState(false);
  const navigate = useNavigate();
  return (
    <Link
      to="/shop/detail"
      onClick={(e) => {
        productContext.setProductId(product.id);
        if (window.location.pathname == "/shop/detail")
          window.scrollTo({ top: 0, behavior: "smooth" });
      }}
    >
      <Card className="cursor-pointer product-card">
        <Image height="160px" src={product.avatar} objectFit="cover" />
        <CardBody>
          <HStack justifyContent="space-between" alignItems="top" spacing="2">
            <Heading fontSize="md" height="40px">
              {product.name}
            </Heading>
            <Box
              id={"cart-" + product.id}
              width="22px"
              className="product-cart-icon"
              onClick={(e) => {
                e.preventDefault();
                let x = window.screenX;
                let y = window.screenY;

                if (!utilService.getCurrentUser()) navigate("/login");
                else {
                  cartActionSerivce.addToCart(product.id);
                  Swal.fire({
                    position: "center",
                    icon: "success",
                    title: "Đã thêm vào giỏ hàng",
                    showConfirmButton: false,
                    timer: 1000,
                  });
                }
                // window.screenX = x;
                // window.screenY = y;
              }}
            >
              <BsFillCartPlusFill size="22px" />
            </Box>
          </HStack>
          <HStack justifyContent="space-between" marginBottom={3}>
            <Badge colorScheme="blue" fontSize="sm">
              {" "}
              {product.price / 1000}
              {".000đ"}
            </Badge>

            <HStack spacing="1px">
              <AiFillStar color="gold" />
              <AiFillStar color="gold" />
              <AiFillStar color="gold" />
              <AiFillStar color="gold" />
              <AiFillStar color="gold" />
            </HStack>
          </HStack>
        </CardBody>
      </Card>
    </Link>
  );
};

export default ShopProductListItem;
