import { Avatar, Button, Card, DataList } from "@chakra-ui/react";
export const Ride = ({ stats, cardButton }) => {
  return (
    <Card.Root width="320px">
      <Card.Body gap="2">
        <Avatar.Root>
          <Avatar.Fallback />
        </Avatar.Root>
        <Card.Title mt="2">{stats.driverName}</Card.Title>
        <div className="ride-grid">
          <DataList.Root>
            <div className="ride-row">
              <DataList.Item>
                <DataList.ItemLabel>Modelo</DataList.ItemLabel>
                <DataList.ItemValue>
                  {stats.car.brand} {stats.car.model}
                </DataList.ItemValue>
              </DataList.Item>
              <DataList.Item>
                <DataList.ItemLabel>Placa</DataList.ItemLabel>
                <DataList.ItemValue>
                  {stats.car.licensePlate}
                </DataList.ItemValue>
              </DataList.Item>
            </div>
            <div className="ride-row">
              <DataList.Item>
                <DataList.ItemLabel>Início</DataList.ItemLabel>
                <DataList.ItemValue>{`${stats.rideDate} ${stats.rideTime}`}</DataList.ItemValue>
              </DataList.Item>
              <DataList.Item>
                <DataList.ItemLabel>Vagas Disponíveis</DataList.ItemLabel>
                <DataList.ItemValue>{stats.availableSeats}</DataList.ItemValue>
              </DataList.Item>
            </div>
            <div className="ride-row">
              <DataList.Item>
                <DataList.ItemLabel>Local de Partida</DataList.ItemLabel>
                <DataList.ItemValue>{stats.pickupLocation}</DataList.ItemValue>
              </DataList.Item>
              <DataList.Item>
                <DataList.ItemLabel>Local de Chegada</DataList.ItemLabel>
                <DataList.ItemValue>{stats.dropoffLocation}</DataList.ItemValue>
              </DataList.Item>
            </div>
          </DataList.Root>
        </div>
      </Card.Body>
      <Card.Footer justifyContent="flex-end">{cardButton}</Card.Footer>
    </Card.Root>
  );
};

export default Ride;
