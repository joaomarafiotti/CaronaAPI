import { Avatar, Button, Card, DataList, Spinner } from "@chakra-ui/react";
import { useState } from "react";
import { useCreateSolicitation } from "../services/rideSolicitationService";

export const Ride = ({
  stats,
  isAvailable,
  isDone,
  solicitationHandler,
  abandonHandler,
  isLoading,
}) => {
  let actionButton = null;
  if (!isDone) {
    actionButton = isAvailable ? (
      <Button
        variant="outline"
        colorPalette="green"
        onClick={solicitationHandler}
        isLoading={isLoading}
      >
        Solicitar
      </Button>
    ) : (
      <Button variant="outline" colorPalette="red" onClick={abandonHandler}>
        Abandonar
      </Button>
    );
  }

  return (
    <Card.Root width="320px" className={isDone ? "ride-done" : ""}>
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
                <DataList.ItemValue>{`${stats.startTime}`}</DataList.ItemValue>
              </DataList.Item>
              <DataList.Item>
                <DataList.ItemLabel>Vagas Disponíveis</DataList.ItemLabel>
                <DataList.ItemValue>{stats.availableSeats}</DataList.ItemValue>
              </DataList.Item>
            </div>
            <div className="ride-row">
              <DataList.Item>
                <DataList.ItemLabel>Local de Partida</DataList.ItemLabel>
                <DataList.ItemValue>{`${stats.pickupLocation.street}, ${stats.pickupLocation.number}`}</DataList.ItemValue>
              </DataList.Item>
              <DataList.Item>
                <DataList.ItemLabel>Local de Chegada</DataList.ItemLabel>
                <DataList.ItemValue>{`${stats.dropOffLocation.street}, ${stats.dropOffLocation.number}`}</DataList.ItemValue>
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
        {isLoading ? <Spinner /> : actionButton}
      </Card.Footer>
    </Card.Root>
  );
};

export default Ride;
