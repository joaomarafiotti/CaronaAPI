import {
  Avatar,
  Button,
  Card,
  HStack,
  Spinner,
  Stack,
  Strong,
  Text,
} from "@chakra-ui/react";
import { LuCheck, LuX } from "react-icons/lu";

const RideSolicitationNotification = ({
  solicitation,
  handleDecline,
  handleApprove,
  isLoading,
}) => {
  return (
    <Card.Root width="320px">
      <Card.Body>
        <HStack mb="6" gap="3">
          <Avatar.Root>
            <Avatar.Fallback name={solicitation.passenger.name} />
          </Avatar.Root>
          <Stack gap="0">
            <Text fontWeight="semibold" textStyle="sm">
              {solicitation.passenger.name}
            </Text>
            <Text color="fg.muted" textStyle="sm">
              {solicitation.passenger.email}
            </Text>
          </Stack>
        </HStack>
        <Card.Description>
          <Strong color="fg">{solicitation.passenger.name} </Strong>
          solicitou para se juntar à sua carona.
        </Card.Description>
      </Card.Body>
      {isLoading ? (
        <Spinner />
      ) : (
        <Card.Footer>
          <Button
            variant="subtle"
            colorPalette="red"
            flex="1"
            onClick={handleDecline}
          >
            <LuX />
            Recusar
          </Button>
          <Button
            variant="subtle"
            colorPalette="blue"
            flex="1"
            onClick={handleApprove}
          >
            <LuCheck />
            Aprovar
          </Button>
        </Card.Footer>
      )}
    </Card.Root>
  );
};

export default RideSolicitationNotification;
