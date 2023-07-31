import { Grid, GridItem } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { HttpUser } from "../../services/user-service";
import {
  StaffProductQuery,
} from "../../hooks/useFetchStaffProduct";
import StaffProductMain from "../../components/staff/staff-product-list-page/StaffProductListMain";
import StaffProductListFilter from "../../components/staff/staff-product-list-page/StaffProductListAction";

interface Props {
  setCurrentProductId: (productId: number) => void;
}
function StaffProductListPage({ setCurrentProductId }: Props) {
  const [user, setUser] = useState<HttpUser | null>(null);
  const [staffProductQuery, setStaffProductQuery] = useState<StaffProductQuery>(
    {} as StaffProductQuery
  );

  useEffect(() => {
    const userJSON = sessionStorage.getItem("user");
    if (userJSON) setUser(JSON.parse(userJSON));
  }, []);

  return (
    <Grid
      templateAreas={{
        base: `"aside-left main"`,
        // sm: `"header header" "aside-left main"`,
        // lg: `"header header" "aside-left main"`,
      }}
      templateColumns={{
        base: `280px 1fr`,
        // sm: `"1fr" "200px 1fr"`,
        // lg: `"1fr" "200px 1fr"`,
      }}
      h="100%"
    >
      {/* <Show above="sm"> */}
      <GridItem area="aside-left" className="aside-left">
        <StaffProductListFilter
          staffProductQuery={staffProductQuery}
          setStaffProductQuery={setStaffProductQuery}
        />
      </GridItem>
      {/* </Show> */}
      <GridItem area="main" className="main">
        <StaffProductMain
          staffProductQuery={staffProductQuery}
          setStaffProductQuery={setStaffProductQuery}
          setCurrentProductId={setCurrentProductId}
        />
      </GridItem>
    </Grid>
  );
}

export default StaffProductListPage;
