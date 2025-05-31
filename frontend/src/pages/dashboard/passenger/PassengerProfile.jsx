import {
  Box,
  Flex,
  Stack,
  Avatar,
  Heading,
  Text,
} from "@chakra-ui/react";

const PassengerProfile = () => {
  const passenger = {
    nome: "João da Silva",
    email: "Ox7k7@example.com",
    cpf: "123.456.789-00",
    dataNascimento: "1990-01-01",
  };

  return (
    <Box borderWidth="1px" borderRadius="lg" p={6} boxShadow="md">
      <Flex align="center" mb={4}>
        <Avatar name={passenger.nome} size="xl" mr={4} />
        <Box>
          <Heading size="md">{passenger.nome}</Heading>
          <Text color="gray.500">{passenger.email}</Text>
        </Box>
      </Flex>
      <Stack spacing={2}>
        <Text>
          <strong>CPF:</strong> {passenger.cpf}
        </Text>
        <Text>
          <strong>Data de Nascimento:</strong> {passenger.dataNascimento}
        </Text>
      </Stack>
    </Box>
  );
};

export default PassengerProfile;
