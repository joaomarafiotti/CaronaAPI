import { Box, Flex, Stack, Avatar, Heading, Text } from "@chakra-ui/react";
import { color } from "framer-motion";

const DriverProfile = () => {
  const driver = {
    nome: "João da Silva",
    email: "Ox7k7@example.com",
    cpf: "123.456.789-00",
    dataNascimento: "1990-01-01",
    cars: [
      {
        model: "Toyota Corolla",
        plate: "ABC-1234",
        year: "2020",
        color: "Blue",
      },
      {
        model: "Toyota Corolla",
        plate: "ABC-1234",
        year: "2020",
        color: "Blue",
      },
      {
        model: "Toyota Corolla",
        plate: "ABC-1234",
        year: "2020",
        color: "Blue",
      },
    ],
  };

  return (
    <Box
      className="driver-profile-box"
      borderWidth="1px"
      borderRadius="lg"
      p={6}
      boxShadow="md"
    >
      <Avatar.Root colorPalette="blue" size="2xl">
        <Avatar.Fallback name={driver.nome} />
      </Avatar.Root>
      <Box>
        <Heading size="md">{driver.nome}</Heading>
        <Text color="gray.500">{driver.email}</Text>
      </Box>
      <Stack spacing={2} className="driver-details">
        <Text>
          <strong>CPF:</strong> {driver.cpf}
        </Text>
        <Text>
          <strong>Data de Nascimento:</strong> {driver.dataNascimento}
        </Text>
      </Stack>
      <Text className="driver-cars-title">
        <strong>Carros: </strong>
      </Text>
      <div className="driver-cars">
        {driver.cars.map((car, index) => (
          <Box key={index} borderWidth="1px" borderRadius="md" p={3} mb={2}>
            <Text>
              <strong>Modelo:</strong> {car.model}
            </Text>
            <Text>
              <strong>Placa:</strong> {car.plate}
            </Text>
            <Text>
              <strong>Ano:</strong> {car.year}
            </Text>
            <Text>
              <strong>Cor:</strong> {car.color}
            </Text>
          </Box>
        ))}
      </div>
    </Box>
  );
};

export default DriverProfile;
