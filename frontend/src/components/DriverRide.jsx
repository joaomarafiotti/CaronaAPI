import { Avatar, Button, Card, DataList } from "@chakra-ui/react";

const DriverRide = ({ ride, onCancel }) => {
  const isWaiting = ride.status === "WAITING";
  const isDone = ride.status === "FINISHED" || ride.status === "CANCELLED";

  const actionButton = !isDone && isWaiting ? (
    <Button variant="outline" colorScheme="red" onClick={() => onCancel(ride.uuid)}>
      Cancelar Carona
    </Button>
  ) : null;

  return (
    <Card.Root width="320px" className={isDone ? "ride-done" : ""}>
      <Card.Body gap="2">
        <div className="ride-grid">
          <DataList.Root>
            <div className="ride-row">
              <DataList.Item>
                <DataList.ItemLabel>Modelo</DataList.ItemLabel>
                <DataList.ItemValue>
                  {ride.car.brand} {ride.car.model}
                </DataList.ItemValue>
              </DataList.Item>
              <DataList.Item>
                <DataList.ItemLabel>Placa</DataList.ItemLabel>
                <DataList.ItemValue>{ride.car.licensePlate}</DataList.ItemValue>
              </DataList.Item>
            </div>

            <div className="ride-row">
              <DataList.Item>
                <DataList.ItemLabel>Início</DataList.ItemLabel>
                <DataList.ItemValue>{ride.startTime}</DataList.ItemValue>
              </DataList.Item>
              <DataList.Item>
                <DataList.ItemLabel>Vagas Disponíveis</DataList.ItemLabel>
                <DataList.ItemValue>{ride.availableSeats}</DataList.ItemValue>
              </DataList.Item>
            </div>

            <div className="ride-row">
              <DataList.Item>
                <DataList.ItemLabel>Local de Partida</DataList.ItemLabel>
                <DataList.ItemValue>
                  {ride.pickupLocation.street}, {ride.pickupLocation.number}
                </DataList.ItemValue>
              </DataList.Item>
              <DataList.Item>
                <DataList.ItemLabel>Local de Chegada</DataList.ItemLabel>
                <DataList.ItemValue>
                  {ride.dropOffLocation.street}, {ride.dropOffLocation.number}
                </DataList.ItemValue>
              </DataList.Item>
            </div>
          </DataList.Root>
        </div>
      </Card.Body>

      <Card.Footer justifyContent="flex-end">{actionButton}</Card.Footer>
    </Card.Root>
  );
};

export default DriverRide;
