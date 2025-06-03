import { Avatar, Button, Card, DataList, Spinner } from "@chakra-ui/react";
export const RideSolicitation = ({
  stats,
  cancelSolicitationHandler,
  isLoading,
}) => {
  return (
    <Card.Root width="320px">
      <Card.Body gap="2">
        <Avatar.Root>
          <Avatar.Fallback />
        </Avatar.Root>
        <Card.Title mt="2">Solicitação</Card.Title>
        <div className="ride-grid">
          <DataList.Root>
            <div className="ride-row">
              <DataList.Item>
                <DataList.ItemLabel>Nome do Motorista</DataList.ItemLabel>
                <DataList.ItemValue>
                  {stats.ride.driver.name}
                </DataList.ItemValue>
              </DataList.Item>
              {/* <DataList.Item>
                <DataList.ItemLabel>Solitação feita em</DataList.ItemLabel>
                <DataList.ItemValue>{stats.requestDate}</DataList.ItemValue>
              </DataList.Item> */}
            </div>
            <div className="ride-row">
              <DataList.Item>
                <DataList.ItemLabel>Modelo</DataList.ItemLabel>
                <DataList.ItemValue>
                  {stats.ride.car.brand} {stats.ride.car.model}
                </DataList.ItemValue>
              </DataList.Item>
              <DataList.Item>
                <DataList.ItemLabel>Placa</DataList.ItemLabel>
                <DataList.ItemValue>
                  {stats.ride.car.licensePlate}
                </DataList.ItemValue>
              </DataList.Item>
            </div>
            <div className="ride-row">
              <DataList.Item>
                <DataList.ItemLabel>Início</DataList.ItemLabel>
                <DataList.ItemValue>{`${stats.ride.startTime}`}</DataList.ItemValue>
              </DataList.Item>
              <DataList.Item>
                <DataList.ItemLabel>Vagas Disponíveis</DataList.ItemLabel>
                <DataList.ItemValue>
                  {stats.ride.availableSeats}
                </DataList.ItemValue>
              </DataList.Item>
            </div>
            <div className="ride-row">
              <DataList.Item>
                <DataList.ItemLabel>Local de Partida</DataList.ItemLabel>
                <DataList.ItemValue>{`${stats.ride.pickupLocation.street} ${stats.ride.pickupLocation.number}`}</DataList.ItemValue>
              </DataList.Item>
              <DataList.Item>
                <DataList.ItemLabel>Local de Chegada</DataList.ItemLabel>
                <DataList.ItemValue>{`${stats.ride.dropOffLocation.street} ${stats.ride.dropOffLocation.number}`}</DataList.ItemValue>
              </DataList.Item>
            </div>
          </DataList.Root>
        </div>
        <div
          style={{
            display: "flex",
            flexDirection: "row",
            gap: "8px",
            marginTop: "8px",
          }}
        >
          {stats.passengers &&
            stats.passengers.map((passenger) => (
              <Avatar.Root
                key={passenger}
              >
                <Avatar.Fallback name={passenger} />
              </Avatar.Root>
            ))}
        </div>
      </Card.Body>
      <Card.Footer justifyContent="flex-end">
        {isLoading ? (
          <Spinner />
        ) : (
          <Button variant="outline" onClick={cancelSolicitationHandler}>
            Cancelar
          </Button>
        )}
      </Card.Footer>
    </Card.Root>
  );
};

export default RideSolicitation;
