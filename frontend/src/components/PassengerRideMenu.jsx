import { Button, Menu, Portal } from "@chakra-ui/react";

const PassengerRideMenu = ({ itens }) => {
  return (
    <Menu.Root>
      <Menu.Trigger asChild>
        <Button variant="outline" size="sm">
          Caronas
        </Button>
      </Menu.Trigger>
      <Portal>
        <Menu.Positioner>
          <Menu.Content>
            {itens &&
              itens.map((item) => (
                <Menu.Item
                  key={item.value}
                  value={item.value}
                  onClick={() => {
                    console.log(`Selected: ${item.label}`);
                    // item.handler && item.handler();
                  }}
                >
                  {item.label}
                </Menu.Item>
              ))}
          </Menu.Content>
        </Menu.Positioner>
      </Portal>
    </Menu.Root>
  );
};

export default PassengerRideMenu;
