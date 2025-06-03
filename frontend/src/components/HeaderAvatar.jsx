import { Avatar, Menu, Portal, Button } from "@chakra-ui/react";

export const HeaderAvatar = ({ itens }) => (
  <Menu.Root positioning={{ placement: "bottom-end" }}>
    <Menu.Trigger asChild>
      <Button variant="unstyled" backgroundColor={"transparent"} p={0} mr={2}>
        <Avatar.Root colorPalette="blue" size="md" cursor="pointer">
          <Avatar.Fallback name="Usuário" />
          <Avatar.Image src="https://bit.ly/broken-link" />
        </Avatar.Root>
      </Button>
    </Menu.Trigger>

    <Portal>
      <Menu.Positioner>
        <Menu.Content>
          {itens?.map((item) => (
            <Menu.Item
              key={item.value}
              onClick={() => {
                item.handler && item.handler();
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

export default HeaderAvatar;