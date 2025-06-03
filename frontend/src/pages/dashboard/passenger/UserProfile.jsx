import { Box, Flex, Stack, Avatar, Heading, Text } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { getUserByToken } from "../../../services/userService";
import { useAuth } from "../../../context/AuthContext";

const UserProfile = () => {
  const [passenger, setPassenger] = useState({});
  const { userToken } = useAuth();

  useEffect(() => {
    const updatePassenger = async () => {
      try {
        const response = await getUserByToken(userToken);
        setPassenger(response.data);
      } catch (error) {
        console.error("Error attempting to update passenger:", error);
      }
    };
    updatePassenger();
  }, []);

  return (
    <Box
      className="passenger-profile-box"
      borderWidth="1px"
      borderRadius="lg"
      p={6}
      boxShadow="md"
    >
      <Avatar.Root colorPalette="blue" size="2xl" cursor="pointer">
        <Avatar.Fallback name={passenger.name} />
      </Avatar.Root>
      <Box>
        <Heading size="md">{passenger.name}</Heading>
        <Text color="gray.500">{passenger.email}</Text>
      </Box>
      <Stack spacing={2} className="passenger-details">
        <Text>
          <strong>CPF:</strong> {passenger.cpf}
        </Text>
        <Text>
          <strong>Data de Nascimento:</strong> {passenger.birthDate}
        </Text>
      </Stack>
    </Box>
  );
};

export default UserProfile;
