import { Card, DataList } from "@chakra-ui/react";

export const Car = ({ stats, cardButton }) => {
  return (
    <Card.Root width="320px" margin={"2"} padding="4" boxShadow="md">
      <Card.Body gap="2">
        <Card.Title mt="3">{"Carro"} {stats.index + 1}</Card.Title>
        <div className="ride-grid">
          <DataList.Root>
            <div className="ride-row">
              <DataList.Item>
                <DataList.ItemLabel>Modelo</DataList.ItemLabel>
                <DataList.ItemValue>
                  {stats.brand} {stats.model }
                </DataList.ItemValue>
              </DataList.Item>
              <DataList.Item>
                <DataList.ItemLabel>Placa</DataList.ItemLabel>
                <DataList.ItemValue>
                  {stats.licensePlate}
                </DataList.ItemValue>
              </DataList.Item>
            </div>
            <div className="ride-row">
              <DataList.Item>
                <DataList.ItemLabel>Assentos</DataList.ItemLabel>
                <DataList.ItemValue>
                  {stats.seats}
                </DataList.ItemValue>
              </DataList.Item>
              <DataList.Item>
                <DataList.ItemLabel>Cor</DataList.ItemLabel>
                <DataList.ItemValue>
                  {stats.color}
                </DataList.ItemValue>
              </DataList.Item>
            </div>
          </DataList.Root>
        </div>
      </Card.Body>
      <Card.Footer justifyContent="flex-end">{cardButton}</Card.Footer>
    </Card.Root>
  );
};

export default Car;