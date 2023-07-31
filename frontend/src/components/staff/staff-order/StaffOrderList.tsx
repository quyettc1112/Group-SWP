import {
  Badge,
  Box,
  Button,
  Table,
  TableContainer,
  Tbody,
  Td,
  Th,
  Thead,
  Tr,
  useDisclosure,
} from "@chakra-ui/react";
import React, { useContext, useState } from "react";
import StaffOrderDetailModal from "./StaffOrderDetailModal";
import {
  StaffOrderQuery,
} from "../../../hooks/useFetchStaffOrder";
import ORDER_STATUS_MAP from "../../../data/OrderStatusData";
import OrderDTO from "../../../type/OrderDTO";
import { GLOBAL_CONTEXT } from "../../../App";
import moneyService from "../../../services/money-service";

interface Props {
  orders: OrderDTO[];
  setOrders: (orders: OrderDTO[]) => void;
  staffOrderQuery: StaffOrderQuery;
  setStaffOrderQuery: (staffOrderQuery: StaffOrderQuery) => void;
}
const StaffOrderList = ({
  orders,
  setOrders,
  staffOrderQuery,
  setStaffOrderQuery,
}: Props) => {
  const globalContext = useContext(GLOBAL_CONTEXT);
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [order, setOrder] = useState({} as OrderDTO);
  const onSetOrder = (order: OrderDTO) => {
    setOrder(order);
    setOrders(orders.map((o) => (o.id != order.id ? o : order)));
  };

  return (
    <Box>
      <TableContainer borderRadius="12" border="solid 1px gray">
        <Table variant="striped" colorScheme="gray">
          <Thead>
            <Tr>
              <Th>ID</Th>
              <Th>Ngày tạo đơn</Th>
              <Th>Trạng thái</Th>
              <Th>Người nhận</Th>
              <Th>Số điện thoại</Th>
              <Th>Địa chỉ nhận hàng</Th>
              <Th>Thanh toán</Th>

              <Th>Chi tiết</Th>
            </Tr>
          </Thead>
          <Tbody>
            {orders.map((order) => (
              <Tr key={order.id}>
                <Td>
                  <strong>{order.id}</strong>
                </Td>
                <Td>{order.createTime}</Td>
                <Td>
                  <Badge
                    colorScheme={ORDER_STATUS_MAP[order.status].colorScheme}
                    border={
                      ORDER_STATUS_MAP[order.status].colorScheme == "gray"
                        ? "1px solid gray"
                        : "none"
                    }
                    w="unset"
                  >
                    {ORDER_STATUS_MAP[order.status].label}
                  </Badge>
                </Td>
                <Td>
                  <strong>{order.name}</strong>
                </Td>
                <Td>
                  <strong>{order.phone}</strong>
                </Td>
                <Td maxW="320px">
                  <div className="text-truncate">{order.address}</div>
                </Td>
                <Td textAlign={"right"} color={"yellow.500"}>
                  <strong>
                    {moneyService.getVND(
                      order.orderDetails.reduce(
                        (acc, item) => acc + item.total,
                        0
                      ) + order.shippingFee
                    )}
                  </strong>
                </Td>

                <Td>
                  <Button
                    colorScheme="blue"
                    paddingX="4"
                    size="sm"
                    onClick={() => {
                      onOpen();
                      setOrder(order);
                    }}
                  >
                    Chi tiết
                  </Button>
                </Td>
              </Tr>
            ))}
          </Tbody>
        </Table>
      </TableContainer>
      <StaffOrderDetailModal
        order={order}
        transition={(order) => {
          setOrders(orders.filter((o) => o.id != order.id));
        }}
        setOrder={onSetOrder}
        isOpen={isOpen}
        onOpen={onOpen}
        onClose={onClose}
      />
    </Box>
  );
};

export default StaffOrderList;
